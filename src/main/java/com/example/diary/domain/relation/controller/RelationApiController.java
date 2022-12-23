package com.example.diary.domain.relation.controller;

import com.example.diary.domain.member.entity.CurrentMember;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.relation.dto.RelationDecideRequestDto;
import com.example.diary.domain.relation.dto.RelationPagingDto;
import com.example.diary.domain.relation.dto.RelationRequestDto;
import com.example.diary.domain.relation.dto.RelationResponseDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RelationApiController {
    private final RelationService relationService;

    /**
     * 친구 목록 조회 - ACCEPT
     */
    @GetMapping("/v1/relations")
    public ResponseDto<RelationPagingDto> getRelationsByStatus(@CurrentMember Member member,
                                                               @ModelAttribute RelationSearchCondition condition,
                                                               @PageableDefault(sort = {"name",
                                                                       "email"}) Pageable pageable) {
        return new ResponseDto<>(
                relationService.getRelationsByStatus(member, condition, pageable),
                HttpStatus.OK);
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
     * 친구 삭제
     */
    @PostMapping("/v1/accept")
    public ResponseDto<RelationResponseDto> acceptRelation(@CurrentMember Member member,
                                                           @RequestBody RelationDecideRequestDto relationAcceptRequestDto) {
        return new ResponseDto<>(
                relationService.acceptRelation(member, relationAcceptRequestDto),
                "친구가 추가 되었습니다.",
                HttpStatus.OK);
    }

    /**
     * 친구삭제, 거절
     */
    @DeleteMapping()
    public ResponseDto<?> deleteRelation(@CurrentMember Member member, @RequestHeader("Friend-Id") String friendId) {
        relationService.rejectRelation(member, Long.valueOf(friendId));
        return new ResponseDto<>("친구 거절 되었습니다.", HttpStatus.OK);
    }
}
