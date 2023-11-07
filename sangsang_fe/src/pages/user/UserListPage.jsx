import React, {useState} from 'react';
import {Link, useSearchParams} from "react-router-dom";
import AdminHeader from "../../components/admin/AdminHeader";
import styles from "./UserListPage.module.css";
import PaginationEx from "../../components/common/pagination/PaginationEx";
import BasicTable from "../../components/common/table/BasicTable";

import Select from '@mui/material/Select';

function UserListPage() {
  const [search] = useSearchParams();
  const [currentPage, setCurrentPage] = useState(1);

  const handlePageChange = (event, newPage) => {
    setCurrentPage(newPage);
    console.log(newPage)
  };
  const [list, setList] = useState({
    totalPage: 1,
    list: [],
  });

  // const {isLoading, isError, data} = useQuery({
  //     queryKey: ["memberList"],
  //     queryFn: memberList,
  //     staleTime: 60 * 1000,
  // })


  // if (isLoading) {
  //     return <LoadingSpinner/>;
  // }
  //
  // if (isError) {
  //
  //     return <div> 오류발생! 오류페이지로 대체하자</div>
  // }

  return (
      <>
        <AdminHeader
            value={"회원조회"}
        />
        <div className={styles.container}>
          <Link to="add"
            style={{backgroundColor:'aqua'}}
          >회원 추가</Link>


          <BasicTable/>

          <PaginationEx
              count={100}
              page={currentPage}
              onChangePage={handlePageChange}
          />



        </div>


      </>
  );
}

export default UserListPage;