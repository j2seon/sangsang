package iium.jjs.sansang_back.common.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class PagingResponseDTO {

  private Object data;

  private PageDto pageInfo;

}