import React from 'react';
import {Pagination} from '@mui/material';
import {createSearchParams, useNavigate, useSearchParams} from "react-router-dom";

function PaginationEx(props) {
  const {page, count, onChangePage} = props;


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