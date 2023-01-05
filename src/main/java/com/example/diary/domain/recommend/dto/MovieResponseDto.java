package com.example.diary.domain.recommend.dto;

import com.example.diary.domain.recommend.dto.MovieInfoDto.Results;
import com.example.diary.domain.recommend.entity.Genres;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class MovieResponseDto {

    private String title;
    private List<String> genres;
    private String overview;
    private String releaseDate;
    private String posterPath;

    public MovieResponseDto() {
    }

    public MovieResponseDto(Results results) {
        this.title = results.getOriginal_title();
        this.genres = getGenres(results.getGenre_ids());
        this.overview = results.getOverview();
        this.releaseDate = results.getRelease_date();
        this.posterPath = results.getPoster_path();
    }

    private List<String> getGenres(List<Integer> ids) {
        List<String> genres = new ArrayList<>();
        for (Integer id : ids) {
            genres.add(Genres.findGenre(id).toString());
        }
        return genres;
    }

}
