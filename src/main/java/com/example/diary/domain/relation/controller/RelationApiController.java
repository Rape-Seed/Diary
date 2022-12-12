package com.example.diary.domain.relation.controller;

import com.example.diary.domain.member.entity.CurrentMember;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.relation.service.RelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/relations")
@RequiredArgsConstructor
public class RelationApiController {
    private final RelationService relationService;

    /**
     * 친구 목록 조회 - ACCEPT
     */
    @GetMapping("/accept")
    public ResponseEntity getRelationsByAccept(@CurrentMember Member member) {
        return null;
    }

    /**
     * 친구 목록 조회 - TEMP
     */
}
