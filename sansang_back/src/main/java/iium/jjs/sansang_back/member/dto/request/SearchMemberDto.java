package iium.jjs.sansang_back.member.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SearchMemberDto {

  private String kind;

  private String content;

}
