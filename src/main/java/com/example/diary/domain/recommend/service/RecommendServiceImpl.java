package com.example.diary.domain.recommend.service;

import static com.example.diary.global.utils.RandomUtils.makeRandomNumber;

import com.example.diary.domain.recommend.dto.ExcelData;
import com.example.diary.domain.recommend.dto.MovieInfoDto;
import com.example.diary.domain.recommend.dto.MovieInfoDto.Results;
import com.example.diary.domain.recommend.dto.MovieResponseDto;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

    public static final int MAX_PAGE_SIZE = 500;
    private final RestTemplate restTemplate;
    private final TMDBProperties tmdbProperties;
    private final PhraseRepository phraseRepository;

    @Override
    public MovieResponseDto recommendMovie(EmotionGenres emotionGenres) {
        Integer genreNumber = EmotionGenres.getGenres(emotionGenres);
//        Genres genre = Genres.findGenre(genreNumber);

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
    public void uploadPhrase(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.rowIterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
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

            phraseRepository.save(new Phrase(excelData));
        }
        inputStream.close();
    }
}
