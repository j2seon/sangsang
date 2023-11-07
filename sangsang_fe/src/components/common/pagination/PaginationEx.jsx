import React from 'react';
import { Pagination } from '@mui/material';

function PaginationEx(props) {
  // 페이지 번호와 페이지 변경 핸들러를 부모 컴포넌트로부터 받아옵니다.
  const { page, count, onChangePage } = props;

  return (
      <Pagination
          count={count} // 전체 페이지 수
          page={page} // 현재 페이지
          onChange={onChangePage} // 페이지 변경 핸들러
          showFirstButton // 처음 페이지로 이동하는 버튼 표시
          showLastButton // 마지막 페이지로 이동하는 버튼 표시
          variant="outlined"
          color="primary"
      />
  );
}

export default PaginationEx;