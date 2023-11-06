package iium.jjs.sansang_back.member.repository;

import iium.jjs.sansang_back.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom{

    Optional<Member> findByMemberId(String memberId);

    Page<Member> findByMemberIdContains(String memberId, Pageable pageable);

}
