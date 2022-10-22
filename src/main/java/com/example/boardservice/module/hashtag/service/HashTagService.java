package com.example.boardservice.module.hashtag.service;

import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.module.hashtag.domain.HashTag;
import com.example.boardservice.module.hashtag.domain.repository.HashTagRepository;
import com.example.boardservice.module.hashtag.web.dto.ResponseHashTagSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class HashTagService {

    private final HashTagRepository repository;

    @Transactional
    public ResponseHashTagSaveDto saveHashTag(String hashTagName) {
        isExists(hashTagName);
        HashTag hashTag = HashTag.builder().hashTagName(hashTagName).build();
        HashTag savedHashTag = repository.save(hashTag);
        return ResponseHashTagSaveDto.of(savedHashTag);
    }

    private void isExists(String hashTagName) {
        if (isExistsByHashTagName(hashTagName)) {
            throw new EntityNotFoundException(ErrorCode.REQUEST_DATA_DUPLICATED.getMessage());
        }
    }

    private boolean isExistsByHashTagName(String hashTagName) {
        return repository.existsByHashTagName(hashTagName);
    }
}
