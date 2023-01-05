package com.example.diary.domain.recommend;

import com.example.diary.domain.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

//    @PostMapping
//    public ResponseDto<Boolean> readPhraseExcel(@RequestParam("file") MultipartFile file) throws IOException {
//        if (file.isEmpty()) {
//            return new ResponseDto<>(false, "실패", HttpStatus.OK);
//        }
//        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
//        if (extension != null && !extension.equals("xlxs") && !extension.equals("xls")) {
//            return new ResponseDto<>(false, "엑셀파일만 업로드 해주세요", HttpStatus.OK);
//        }
//
//
//    }
}
