package com.example.diary.domain.relation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RelationServiceImpl implements RelationService {
}
