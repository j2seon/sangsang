package iium.jjs.sansang_back.common.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {

  private int pageStart;

  private int pageEnd;

  private int page; // 현재 페이지

  private long total; // 전체 수

  private int size; // 사이즈

  private boolean next, prev; // 이전

  @Builder
  public PageDto(long total, int page, int realEnd, int size){
    this.total = total;
    this.page = page + 1;
    this.size = size;

    this.pageEnd = (int) (Math.ceil(this.page / 10.0)) * 10;

    this.pageStart = this.pageEnd - 9;

    if (realEnd < pageEnd) {
      this.pageEnd = realEnd;
    }

    this.prev = this.pageStart > 1;

    this.next = this.pageEnd < realEnd;

  }

}
