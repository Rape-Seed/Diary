package com.example.diary.domain;

import com.example.diary.global.utils.QRUtils;
import com.google.zxing.WriterException;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {

    @GetMapping("/login")
    public String index() {
        return "index";
    }

    @GetMapping("/hello")
    public ResponseEntity createQrCode() throws IOException, WriterException {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(QRUtils.generateQRCodeImage("1q2w3e4r", 200, 200));
    }
}
