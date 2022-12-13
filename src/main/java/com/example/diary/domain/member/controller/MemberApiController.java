package com.example.diary.domain.member.controller;

import com.example.diary.domain.member.dto.InfoResponseDto;
import com.example.diary.domain.member.dto.MyInfoRequestDto;
import com.example.diary.domain.member.entity.CurrentMember;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.service.MemberService;
import com.example.diary.global.common.dto.ResponseDto;
import com.example.diary.global.utils.QRUtils;
import com.google.zxing.WriterException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/v1/member/myInfo")
    public ResponseDto<InfoResponseDto> getMyInfo(@CurrentMember Member member) {
        return new ResponseDto<>(memberService.getMyInfo(member), HttpStatus.OK);
    }

    @GetMapping("/v1/member/info/{member_id}")
    public ResponseDto<InfoResponseDto> getMemberInfo(@CurrentMember Member member, @PathVariable String member_id) {
        return new ResponseDto<>(memberService.getMemberInfo(member, Long.valueOf(member_id)), HttpStatus.OK);
    }

    @PutMapping("/v1/member/myInfo")
    public ResponseDto<InfoResponseDto> updateMyInfo(@CurrentMember Member member,
                                                     @RequestBody MyInfoRequestDto dto) {
        return new ResponseDto<>(memberService.updateMyInfo(member, dto), HttpStatus.OK);
    }

    @GetMapping("/v1/member/code")
    public ResponseEntity<String> createCode(@CurrentMember Member member) {
        return new ResponseEntity<>(memberService.getMemberCode(member), HttpStatus.OK);
    }

    //TODO qr을 사용자 코드를 넣어서 생성해 주고 사진을 찍으면 친구추가 정보입력칸에 자동으로 입력되게
    @GetMapping("/v1/member/code/Qr")
    public ResponseEntity createQrCode(@CurrentMember Member member) throws IOException, WriterException {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(QRUtils.generateQRCodeImage(member.getCode(), 200, 200));
    }

}
