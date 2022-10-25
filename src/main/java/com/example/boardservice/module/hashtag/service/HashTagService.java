package com.example.boardservice.module.hashtag.service;

import com.example.boardservice.module.hashtag.domain.HashTag;
import com.example.boardservice.module.hashtag.domain.repository.HashTagRepository;
import com.example.boardservice.module.hashtag.web.dto.ResponseHashTagListDto;
import com.example.boardservice.module.hashtag.web.dto.ResponseHashTagSaveDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HashTagService {

    private final HashTagRepository repository;

    @Transactional
    public ResponseHashTagSaveDto saveHashTag(List<String> hashTagName) {
        List<HashTag> fullHashTags = toFullHashTagMapper(hashTagName);
        List<HashTag> savedFullHashTags = repository.saveAll(fullHashTags);
        return ResponseHashTagSaveDto.of(savedFullHashTags);
    }

    private List<HashTag> toFullHashTagMapper(List<String> hashTagNames) {
        return hashTagNames.stream()
                .map(hashTagNameData -> Strings.concat("#", hashTagNameData))
                .map(fullHashTagNameData -> HashTag.builder().hashTagName(fullHashTagNameData).build())
                .collect(Collectors.toList());
    }

    public ResponseHashTagListDto findHashTagNameList() {
        List<String> list = repository.findAllByOrderByHashTagNameDesc().stream()
                .map(HashTag::getHashTagName).collect(Collectors.toList());

        return ResponseHashTagListDto.builder().hashTagName(list).build();

    }
}
