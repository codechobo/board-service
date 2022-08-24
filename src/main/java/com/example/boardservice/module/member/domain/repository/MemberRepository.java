package com.example.boardservice.module.member.domain.repository;

import com.example.boardservice.module.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    boolean existsByPassword(String password);

    Optional<Member> findByNickname(String memberNickname);
}