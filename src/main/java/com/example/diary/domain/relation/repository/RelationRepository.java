package com.example.diary.domain.relation.repository;

import com.example.diary.domain.relation.entity.Relation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationRepository extends JpaRepository<Relation, Long> {
}
