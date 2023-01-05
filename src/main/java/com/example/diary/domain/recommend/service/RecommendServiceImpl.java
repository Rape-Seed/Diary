package com.example.diary.domain.recommend.service;

import static com.example.diary.global.utils.RandomUtils.makeRandomNumber;

import com.example.diary.domain.recommend.dto.MovieInfoDto;
import com.example.diary.domain.recommend.dto.MovieInfoDto.Results;
import com.example.diary.domain.recommend.dto.MovieResponseDto;
import com.example.diary.domain.recommend.entity.EmotionGenres;
import com.example.diary.global.advice.exception.MovieNotFoundException;
import com.example.diary.global.properties.TMDBProperties;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

    public static final int MAX_PAGE_SIZE = 500;
    private final RestTemplate restTemplate;
    private final TMDBProperties tmdbProperties;

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
    public void recommendPhrase() {

    }

    @Override
    public void uploadPhrase(MultipartFile file) {

    }

    /*
    1. 명언 등록기능 1개1개 따로따로
    2. excel 한번에 등록하는 기능
    3. 명언 추출 알고리즘
     */
}
