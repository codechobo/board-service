package com.example.boardservice.module.member.domain.repository;

import com.example.boardservice.module.member.web.model.ResponseMemberListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberCustomRepository {
    Page<ResponseMemberListDto> getMembersIncludingLastJoin(String searchName, Pageable pageable);
}
