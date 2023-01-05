package com.example.diary.domain.recommend.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class GenresTest {

    @Test
    void genreTest() {
        Assertions.assertThat(Genres.findGenre(37)).isEqualTo(Genres.valueOf("Western"));
    }

}