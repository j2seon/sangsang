import React from 'react';
import {createSearchParams, Link, useLocation, useNavigate, useSearchParams} from "react-router-dom";
import AdminHeader from "../../components/admin/AdminHeader";
import styles from "./UserListPage.module.css";
import PaginationEx from "../../components/common/pagination/PaginationEx";
import BasicTable from "../../components/common/table/BasicTable";

import {memberList, memberWithdrawal} from "../../api/admin/adminApi";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {LoadingSpinner} from "../../components/common/other/LoadingSpinner";
import {useEdit} from "../../context/EditContext";

function UserListPage() {
  const queryClient = useQueryClient();
  const [search] = useSearchParams();
  const navigate = useNavigate();
  const { setImgEdit, setFormEdit } = useEdit();
  const paramData = {
    page: search.get("page") || 0,
    size: search.get("size") || 10,
    kind: search.get("kind") ?? '',
    content: search.get("content") ?? ''
  }

  const {isPending, isError, data} = useQuery({
    queryKey: ["memberList", paramData],
    queryFn: async () => memberList(paramData),
    //staleTime : 60 * 1000
  });

  const withdrawal = useMutation({
    mutationFn : async (memberId) => memberWithdrawal(memberId),
    onSuccess: () => {
      return queryClient.invalidateQueries({ queryKey:["memberList", paramData]})
    },
  });

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

  const handleTableRow = (id) => {
    setFormEdit(false);
    navigate(`${id}`);
  }


  if (isPending) {
    console.log("pending")
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
        <div className={styles.table}>
          <BasicTable
            list={data?.data}
            tableHeadData={tableHeadData}
            color={{ backgroundColor: 'lightBlue' }}
            onClick={handleTableRow}
            onWithdrawal={withdrawal}
          />
        </div>
        <div className={styles.paging}>
          <PaginationEx
            count={data?.pageInfo?.pageEnd}
            page={data?.pageInfo?.page}
            onChangePage={handlePageChange}
          />
        </div>
      </div>
    </>
  );
}


const tableHeadData = ['아이디', '이름', '권한', '우편번호', '주소', '가입일자', '탈퇴여부']

export default UserListPage;