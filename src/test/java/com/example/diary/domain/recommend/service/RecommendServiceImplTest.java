package com.example.diary.domain.recommend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.diary.domain.recommend.dto.PhraseRegRequestDto;
import com.example.diary.domain.recommend.dto.PhraseRegRequestDto.RegPhrase;
import com.example.diary.domain.recommend.dto.PhraseRegResponseDto;
import com.example.diary.domain.recommend.repository.PhraseRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest
class RecommendServiceImplTest {

    @Autowired
    RecommendService recommendService;

    @Autowired
    PhraseRepository phraseRepository;

    @AfterEach
    public void after() {
        phraseRepository.deleteAll();
    }

    @Test
    void recommendRegSuccessTest() {
        List<RegPhrase> regPhrases = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            regPhrases.add(new RegPhrase(String.valueOf(i), "test" + i));
        }

        PhraseRegResponseDto result = recommendService.registerPhrase(new PhraseRegRequestDto(regPhrases));

        assertThat(result.getSavedSuccess().size()).isEqualTo(10);
        assertThat(result.getSavedFailure().size()).isEqualTo(0);
        assertThat(phraseRepository.findAll().size()).isEqualTo(10);
    }

    @Test
    void recommendRegSuccessFailureTest() {
        List<RegPhrase> regPhrases = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            regPhrases.add(new RegPhrase(String.valueOf(i), "test" + i));
        }
        regPhrases.add(new RegPhrase(String.valueOf(11), ""));
        regPhrases.add(new RegPhrase(String.valueOf(12), "test1"));

        PhraseRegResponseDto result = recommendService.registerPhrase(new PhraseRegRequestDto(regPhrases));

        assertThat(result.getSavedSuccess().size()).isEqualTo(10);
        assertThat(result.getSavedFailure().size()).isEqualTo(2);
        assertThat(phraseRepository.findAll().size()).isEqualTo(10);
    }

    @Test
    void excelTestSuccess() throws IOException {
        FileInputStream file = new FileInputStream(new File("src/test/resources/static/test.xlsx"));
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("test", "test.xlsx", "xlsx", file);
        PhraseRegResponseDto result = recommendService.registerPhraseByExcel(mockMultipartFile);

        assertThat(result.getSavedSuccess().size()).isEqualTo(20);
        assertThat(result.getSavedFailure().size()).isEqualTo(0);
    }

    @Test
    void excelTestSuccessFailure() throws IOException {
        FileInputStream file = new FileInputStream(new File("src/test/resources/static/test2.xlsx"));
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("test2", "test2.xlsx", "xlsx", file);
        PhraseRegResponseDto result = recommendService.registerPhraseByExcel(mockMultipartFile);

        assertThat(result.getSavedSuccess().size()).isEqualTo(20);
        assertThat(result.getSavedFailure().size()).isEqualTo(2);
        assertThat(phraseRepository.findAll().size()).isEqualTo(20);
    }

    @Test
    void throwByNotExcelFile() throws IOException {
        FileInputStream file = new FileInputStream(new File("src/test/resources/static/test3.txt"));
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("test3", "test3.txt", "txt", file);
        assertThatThrownBy(() -> recommendService.registerPhraseByExcel(mockMultipartFile))
                .isInstanceOf(IllegalArgumentException.class);
    }

}