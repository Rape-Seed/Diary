package com.example.diary.domain.member.controller;

import com.example.diary.domain.member.dto.MyInfoRequestDto;
import com.example.diary.domain.member.dto.MyInfoResponseDto;
import com.example.diary.domain.member.entity.CurrentMember;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<MyInfoResponseDto> getMyInfo(@CurrentMember Member member) {
        return new ResponseEntity<>(memberService.getMyInfo(member), HttpStatus.OK);
    }

    @PutMapping("/v1/member/myInfo")
    public ResponseEntity<?> updateMyInfo(@CurrentMember Member member, @RequestBody MyInfoRequestDto dto) {
        return new ResponseEntity<>(memberService.updateMyInfo(member, dto), HttpStatus.OK);
    }
//
//    @GetMapping("v1/member/code")
//    public
}
