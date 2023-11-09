import React, {useEffect, useState} from 'react';
import {createSearchParams, Link, useLocation, useNavigate, useSearchParams} from "react-router-dom";
import AdminHeader from "../../components/admin/AdminHeader";
import styles from "./UserListPage.module.css";
import PaginationEx from "../../components/common/pagination/PaginationEx";
import BasicTable from "../../components/common/table/BasicTable";

import {memberList} from "../../api/admin/adminApi";
import {useQuery} from "@tanstack/react-query";
import {LoadingSpinner} from "../../components/common/other/LoadingSpinner";

function UserListPage() {
  const [search] = useSearchParams();
  const navigate = useNavigate();
  const paramData = {
    page: search.get("page") || 0,
    size: search.get("size") || 10,
    kind: search.get("kind") ?? '',
    content: search.get("content") ?? ''
  }

  const [pageData, setPageData] = useState(null);

  const {isLoading, isError, data} = useQuery({
    queryKey: ["memberList", paramData],
    queryFn: async () => memberList(paramData),
    staleTime : 60 * 1000
  });

  useEffect(() => {
    //console.log(data)
    if (data) {
      setPageData({
        list: data?.data, paging: data.pageInfo
      })
    }
    console.log(data)
    return ()=>{
    }
  }, [data, search]);

  const handlePageChange = (event, newPage) => {
    navigate({
      search: `${createSearchParams({
        size: parseInt(paramData.size),
        page: newPage - 1,
        kind: paramData.kind,
        content: paramData.content
      })}`
    })
    console.log(newPage)
  };

  if (isLoading) {
    return <LoadingSpinner/>;
  }

  if (isError) {
    return <div> 오류발생! 오류페이지로 대체하자</div>
  }
  return (
    <>
      <AdminHeader
        value={"회원조회"}
      />
      <div className={styles.container}>
        <div className={styles.add}>
          <Link to="add">회원 추가</Link>
        </div>
        <div>
          <BasicTable
            list={pageData?.list}
            tableHeadData={tableHeadData}
            color={{ backgroundColor: 'lightBlue' }}
          />
        </div>
        <div>
          <PaginationEx
            count={pageData?.paging?.pageEnd}
            page={pageData?.paging?.page}
            onChangePage={handlePageChange}
          />
        </div>
      </div>
    </>
  );
}


const tableHeadData = ['아이디', '이름', '권한', '우편번호', '주소', '가입일자', '탈퇴여부']

export default UserListPage;