package com.example.diary.domain.recommend.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieInfoDto {
    private String page;
    private List<Results> results;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Results {
        private Boolean adult;
        private String backdrop_path;
        private List<Integer> genre_ids;
        private Long id;
        private String original_language;
        private String original_title;
        private String overview;
        private Double popularity;
        private String poster_path;
        private String release_date;
        private String title;
        private Boolean video;
        private Long vote_average;
        private Long vote_count;
    }
}
