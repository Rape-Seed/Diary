package com.example.diary.domain.recommend.service;

import com.example.diary.domain.recommend.entity.EmotionGenres;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RecommendServiceImplTest {

    @Autowired
    RecommendService recommendService;

    @Test
    void test() {
        recommendService.recommendMovie(EmotionGenres.NEGATIVE);
    }


}