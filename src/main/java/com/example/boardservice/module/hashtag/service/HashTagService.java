package com.example.boardservice.module.hashtag.service;

import com.example.boardservice.error.ErrorCode;
import com.example.boardservice.module.hashtag.domain.HashTag;
import com.example.boardservice.module.hashtag.domain.repository.HashTagRepository;
import com.example.boardservice.module.hashtag.web.dto.ResponseHashTagListDto;
import com.example.boardservice.module.hashtag.web.dto.ResponseHashTagSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HashTagService {

    private final HashTagRepository repository;
    private StringBuilder hashTagPrefix = new StringBuilder();

    @Transactional
    public ResponseHashTagSaveDto saveHashTag(String hashTagName) {
        String fullHashTagName = hashTagPrefix.append("#").append(hashTagName).toString();

        HashTag hashTag = HashTag.builder().hashTagName(fullHashTagName).build();
        if (repository.existsByHashTagName(fullHashTagName)) {
            // 존재 한다면
            HashTag savedHashTag = repository.findByHashTagName(fullHashTagName)
                    .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
            return ResponseHashTagSaveDto.of(savedHashTag);

        } else {
            // 존재 하지 않는다면
            HashTag savedHashTag = repository.save(hashTag);
            return ResponseHashTagSaveDto.of(savedHashTag);
        }
    }

    public ResponseHashTagListDto findHashTagNameList() {
        List<String> list = repository.findAllByOrderByHashTagNameDesc().stream()
                .map(HashTag::getHashTagName).collect(Collectors.toList());

        return ResponseHashTagListDto.builder().hashTagName(list).build();

    }
}
