package com.example.boardservice.module.member.domain.repository;

import com.example.boardservice.module.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    boolean existsByNicknameAndEmailAndPassword(String nickname, String email, String password);
    Optional<Member> findByNickname(String email);
}