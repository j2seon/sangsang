package iium.jjs.sansang_back.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

  private final JPAQueryFactory queryFactory;
}
