package com.example.diary.domain.recommend.service;

import static com.example.diary.global.utils.RandomUtils.makeRandomNumber;

import com.example.diary.domain.recommend.dto.ExcelData;
import com.example.diary.domain.recommend.dto.MovieInfoDto;
import com.example.diary.domain.recommend.dto.MovieInfoDto.Results;
import com.example.diary.domain.recommend.dto.MovieResponseDto;
import com.example.diary.domain.recommend.dto.PhraseRegRequestDto;
import com.example.diary.domain.recommend.dto.PhraseRegRequestDto.RegPhrase;
import com.example.diary.domain.recommend.dto.PhraseRegResponseDto;
import com.example.diary.domain.recommend.dto.PhraseResponseDto;
import com.example.diary.domain.recommend.entity.EmotionGenres;
import com.example.diary.domain.recommend.entity.Phrase;
import com.example.diary.domain.recommend.repository.PhraseRepository;
import com.example.diary.global.advice.exception.MovieNotFoundException;
import com.example.diary.global.properties.TMDBProperties;
import com.example.diary.global.utils.RandomUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

    public static final int MAX_PAGE_SIZE = 500;
    public static final String REG_NULL = "잘못된 입력을 하셨습니다.";
    private final RestTemplate restTemplate;
    private final TMDBProperties tmdbProperties;
    private final PhraseRepository phraseRepository;

    @Override
    public MovieResponseDto recommendMovie(EmotionGenres emotionGenres) {
        Integer genreNumber = EmotionGenres.getGenres(emotionGenres);

        MovieInfoDto movieInfoDto = restTemplate.getForObject(makeMovieDiscoverUrl(genreNumber), MovieInfoDto.class);
        if (movieInfoDto == null || movieInfoDto.getResults() == null) {
            throw new MovieNotFoundException();
        }
        return makeResponse(movieInfoDto.getResults());
    }

    private MovieResponseDto makeResponse(List<Results> results) {
        Collections.shuffle(results);
        return new MovieResponseDto(results.get(makeRandomNumber(results.size())));
    }

    private String makeMovieDiscoverUrl(Integer genreNumber) {
        return UriComponentsBuilder
                .fromHttpUrl(tmdbProperties.getDiscoverUrl())
                .queryParam("with_genres", genreNumber)
                .queryParam("page", makeRandomNumber(MAX_PAGE_SIZE))
                .queryParam("api_key", tmdbProperties.getTmdbKey())
                .toUriString();
    }

    @Override
    public PhraseResponseDto recommendPhrase() {
        Phrase phrase = phraseRepository.findById(RandomUtils.makeRandomNumber(
                        1, Integer.parseInt(String.valueOf(phraseRepository.findLastIndexNumber()))))
                .orElseThrow(IllegalArgumentException::new);

        return new PhraseResponseDto(phrase.getContent());
    }

    @Override
    @Transactional
    public PhraseRegResponseDto registerPhrase(PhraseRegRequestDto phraseRegRequestDto) {
        PhraseRegResponseDto result = new PhraseRegResponseDto();
        for (RegPhrase phrase : phraseRegRequestDto.getPhrases()) {
            if (phrase.getContent().isEmpty() || phrase.getContent().isBlank()) {
                result.addFailure(phrase.getId(), REG_NULL);
                continue;
            }
            if (phraseRepository.findByContent(phrase.getContent()) != null) {
                result.addFailure(phrase.getContent(), "이미 등록된 명언입니다.");
                continue;
            }
            Phrase savedPhrase = phraseRepository.save(new Phrase(phrase.getContent()));
            result.addSuccess(savedPhrase.getContent());
        }
        return result;
    }

    @Override
    @Transactional
    public PhraseRegResponseDto registerPhraseByExcel(MultipartFile file) throws IOException {
        if (!checkExcelFileExist(file) || !checkExcelFile(file)) {
            throw new IllegalArgumentException("파일이 잘못되었습니다.");
        }
        InputStream inputStream = file.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();

        PhraseRegResponseDto result = new PhraseRegResponseDto();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() == 0) {
                continue;
            }

            ExcelData excelData = new ExcelData();
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getColumnIndex() == 0) {
                    excelData.setColumn1(cell.getNumericCellValue());
                } else if (cell.getColumnIndex() == 1) {
                    excelData.setColumn2(cell.getStringCellValue());
                }
            }

            Phrase phrase = new Phrase(excelData);
            if (phrase.getContent().isEmpty() || phrase.getContent().isBlank()) {
                result.addFailure("", REG_NULL);
                continue;
            }
            if (phraseRepository.findByContent(phrase.getContent()) != null) {
                result.addFailure(phrase.getContent(), "이미 등록된 명언입니다.");
                continue;
            }
            result.addSuccess(phraseRepository.save(phrase).getContent());
        }
        inputStream.close();

        return result;
    }


    private Boolean checkExcelFileExist(MultipartFile file) {
        return !file.isEmpty();
    }

    private Boolean checkExcelFile(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return extension == null || extension.equals("xlsx") || extension.equals("xls");
    }

}
