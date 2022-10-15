package com.example.boardservice.module.member.domain;


import com.example.boardservice.module.base.TimeEntity;
import com.example.boardservice.module.member.web.dto.request.RequestMemberSaveDto;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(of = "id")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MEMBERS",
        indexes = @Index(columnList = "nickname, email", unique = true))
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

    public static Member createMember(RequestMemberSaveDto requestDto) {
        return Member.builder()
                .name(requestDto.getName())
                .nickname(requestDto.getNickname())
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .build();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
