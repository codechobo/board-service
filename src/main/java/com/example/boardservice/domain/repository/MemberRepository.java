package com.example.boardservice.domain.repository;

import com.example.boardservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
    boolean existsByPassword(String password);

}
