package com.example.diary.domain.relation.controller;

import com.example.diary.domain.member.entity.CurrentMember;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.relation.dto.RelationRequestDto;
import com.example.diary.domain.relation.dto.RelationSearchCondition;
import com.example.diary.domain.relation.service.RelationService;
import com.example.diary.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    @GetMapping("/v1/{status}")
    public void getRelationsByStatus(@CurrentMember Member member, @PathVariable String status,
                                     @ModelAttribute RelationSearchCondition condition,
                                     @PageableDefault(sort = {"name", "email"}) Pageable pageable) {

    }

    /**
     * 친구 추가
     */
    @PostMapping("/v1/friend")
    public ResponseDto<?> enterIntoRelation(@CurrentMember Member member,
                                            @RequestBody RelationRequestDto relationRequestDto) {
        return new ResponseDto<>(
                relationService.enterIntoRelation(member, relationRequestDto),
                "친구 신청이 완료되었습니다.",
                HttpStatus.OK);
    }

    /**
     * 친구수락
     */
    @PostMapping("/v1/accept")
    public void acceptRelation(@CurrentMember Member member, @RequestBody RelationRequestDto relationRequestDto) {

    }

    /**
     * 친구삭제
     */
    @DeleteMapping()
    public void deleteRelation(@CurrentMember Member member, @RequestHeader("Code") String relationCode) {

    }
}
