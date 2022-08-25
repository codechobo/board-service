package com.example.boardservice.module.post.domain.repository;

import com.example.boardservice.module.post.web.dto.ResponsePostListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostCustomRepository {
    Page<ResponsePostListDto> getMembersIncludingLastCreate(String author, String title, String content, Pageable pageable);
}
