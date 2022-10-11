package com.example.boardservice.module.post.domain.repository;

import com.example.boardservice.module.post.web.dto.response.ResponsePostListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostCustomRepository {
    Page<ResponsePostListDto> getMembersIncludingLastCreate(String author, String title, String content, Pageable pageable);
    Page<ResponsePostListDto> getPostViewCountSort(String author, String title, String content, Pageable pageable);
}
