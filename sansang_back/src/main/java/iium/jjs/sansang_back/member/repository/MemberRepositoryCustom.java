package iium.jjs.sansang_back.member.repository;

import iium.jjs.sansang_back.member.dto.request.SearchMemberDto;
import iium.jjs.sansang_back.member.dto.response.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {

  Page<MemberDto> conditionList(SearchMemberDto search, Pageable pageable);
}
