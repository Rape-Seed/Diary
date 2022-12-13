package com.example.diary.domain.relation.dto;


import com.example.diary.domain.relation.entity.Relation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    private List<RelationBriefInfo> relationBriefInfos = new ArrayList<>();

    public RelationPagingDto(Page<Relation> relationResult) {
        this.totalPageCount = relationResult.getTotalPages();
        this.currentPageCount = relationResult.getNumber();
        this.totalElementCount = relationResult.getTotalElements();
        this.currentPageElementCount = relationResult.getNumberOfElements();
        this.relationBriefInfos = relationResult.getContent()
                .stream().map(RelationBriefInfo::new).collect(Collectors.toList());
    }
}
