import React, {useState} from 'react';
import {Link, useSearchParams} from "react-router-dom";
import AdminHeader from "../../components/admin/AdminHeader";
import styles from "./UserListPage.module.css";


function UserListPage() {
  const [search] = useSearchParams();

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





        </div>


      </>
  );
}

export default UserListPage;