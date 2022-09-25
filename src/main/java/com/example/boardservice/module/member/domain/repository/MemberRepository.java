package com.example.boardservice.module.member.domain.repository;

import com.example.boardservice.module.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    Optional<Member> findByNickname(String nickname);

    boolean existsByNicknameOrPasswordOrEmail(String nickname, String password, String email);

    boolean existsByPassword(String password);

    boolean existsByNickname(String nickname);
}