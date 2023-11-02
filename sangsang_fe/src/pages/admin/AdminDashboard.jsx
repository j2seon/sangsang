import React from 'react';
import SideBar from "../../components/nav/AdminSidBar";
import styles from "./AdminDashborad.module.css"
import {Outlet} from "react-router-dom";
function AdminDashboard() {
  return (
      <main>
        <div className={styles.container}>
            <div className={styles.side_wrap}>
                <SideBar/>
            </div>
            <div className={styles.main}>
                <Outlet/>
            </div>
        </div>
      </main>
  );
}

export default AdminDashboard;