package com.example.boardservice.module.member.domain;


import com.example.boardservice.module.base.TimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MEMBERS", uniqueConstraints = {
        @UniqueConstraint(columnNames = "NICKNAME"),
        @UniqueConstraint(columnNames = "PASSWORD"),
        @UniqueConstraint(columnNames = "EMAIL")})
@Entity
public class Member extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBERS_ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name; // 이름

    @Column(name = "NICKNAME", length = 30, nullable = false)
    private String nickname; // 닉네임

    @Column(name = "EMAIL", length = 100, nullable = false)
    private String email; // 이메일

    @Column(name = "PASSWORD", length = 100, nullable = false)
    private String password; // 비밀번호

    @Builder
    public Member(String name, String nickname, String email, String password) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public void updateMember(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

}
