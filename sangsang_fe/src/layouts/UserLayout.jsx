import styles from "../pages/admin/AdminDashborad.module.css";

import {Outlet} from "react-router-dom";
import React from "react";
import UserSidBar from "../components/user/UserSidBar";


const UserLayout = () => {
  return (
    <main>
      <div className={styles.container}>
        <div className={styles.side_wrap}>
          <UserSidBar/>
        </div>
        <div className={styles.main}>
          <Outlet/>
        </div>
      </div>
    </main>
  );
}

export default UserLayout;