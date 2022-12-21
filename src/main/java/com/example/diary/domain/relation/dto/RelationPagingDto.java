package com.example.diary.domain.relation.dto;


import com.example.diary.domain.relation.repository.RelationMemberDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class RelationPagingDto {

    private int totalPageCount;
    private int currentPageCount;
    private long totalElementCount;
    private int currentPageElementCount;
    private List<RelationMemberDto> relationMemberDtos = new ArrayList<>();

    public RelationPagingDto(Page<RelationMemberDto> relationResult) {
        this.totalPageCount = relationResult.getTotalPages();
        this.currentPageCount = relationResult.getNumber();
        this.totalElementCount = relationResult.getTotalElements();
        this.currentPageElementCount = relationResult.getNumberOfElements();
        this.relationMemberDtos = relationResult.getContent();
    }
}
