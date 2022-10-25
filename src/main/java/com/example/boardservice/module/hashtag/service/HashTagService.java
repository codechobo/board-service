package com.example.boardservice.module.hashtag.service;

import com.example.boardservice.module.hashtag.domain.HashTag;
import com.example.boardservice.module.hashtag.domain.repository.HashTagRepository;
import com.example.boardservice.module.hashtag.web.dto.ResponseHashTagSaveDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HashTagService {

    private final HashTagRepository repository;

    @Transactional
    public ResponseHashTagSaveDto saveHashTag(Set<String> hashTagNames) {
        List<HashTag> fullHashTags = toFullHashTagMapper(hashTagNames);
        List<HashTag> savedFullHashTags = repository.saveAll(fullHashTags);
        return ResponseHashTagSaveDto.of(savedFullHashTags);
    }

    private List<HashTag> toFullHashTagMapper(Set<String> hashTagNames) {
        return hashTagNames.stream()
                .map(hashTagNameData -> Strings.concat("#", hashTagNameData))
                .map(fullHashTagNameData -> HashTag.builder().hashTagName(fullHashTagNameData).build())
                .collect(Collectors.toList());
    }
}
