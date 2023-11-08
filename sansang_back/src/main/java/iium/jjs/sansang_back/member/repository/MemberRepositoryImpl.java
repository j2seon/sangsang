package iium.jjs.sansang_back.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import iium.jjs.sansang_back.member.dto.request.SearchMemberDto;
import iium.jjs.sansang_back.member.dto.response.MemberDto;
import iium.jjs.sansang_back.member.dto.response.QMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static iium.jjs.sansang_back.member.entity.QMember.member;


@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<MemberDto> conditionList(SearchMemberDto search, Pageable pageable) {
    List<MemberDto> content = queryFactory
      .select(new QMemberDto(
              member.memberId,
              member.memberPwd,
              member.memberName,
              member.address.zipCode,
              member.address.address,
              member.address.addressDetail,
              member.profile,
              member.authority.stringValue(),
              member.createdAt,
              member.deletedAt
      )).from(member)
      .where(search(search))
      .offset(pageable.getOffset())
      .limit(pageable.getPageSize())
      .fetch();

    JPAQuery<Long> total = queryFactory
      .select(member.count())
      .from(member)
      .where(search(search));

    return PageableExecutionUtils.getPage(content, pageable, total::fetchOne);

  }


  private BooleanExpression memberIdEq(String memberId) {
    return (memberId == null || memberId.isEmpty()) ? null : member.memberId.contains(memberId);
  }

  private BooleanExpression memberNameEq(String memberName) {
    return (memberName == null || memberName.isEmpty()) ? null : member.memberName.contains(memberName);
  }

  private BooleanExpression search(SearchMemberDto search) {
    if (search == null || search.getKind() == null) {
      return null;
    }

    switch (search.getKind()){
      case "title":
        return memberIdEq(search.getContent());
      case "name":
        return memberNameEq(search.getContent());
    }
    return null;
  }
}
