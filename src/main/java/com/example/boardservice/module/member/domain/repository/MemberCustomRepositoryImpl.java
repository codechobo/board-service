package com.example.boardservice.module.member.domain.repository;

import com.example.boardservice.module.member.web.dto.response.QResponseMemberListDto;
import com.example.boardservice.module.member.web.dto.response.ResponseMemberListDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.boardservice.module.member.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ResponseMemberListDto> getMembersIncludingLastJoin(String searchName, Pageable pageable) {
        List<ResponseMemberListDto> result = jpaQueryFactory
                .select(new QResponseMemberListDto(
                        member.name,
                        member.nickname,
                        member.email,
                        member.createdAt))
                .from(member)
                .where(eqMemberName(searchName))
                .orderBy(member.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

    private BooleanExpression eqMemberName(String searchName) {
        if (Strings.isEmpty(searchName)) {
            return null;
        }
        return member.name.eq(searchName);
    }
}
