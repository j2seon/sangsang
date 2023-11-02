import React from 'react';
import styles from "../pages/admin/AdminDashborad.module.css";
import SideBar from "../components/nav/AdminSidBar";
import {Outlet} from "react-router-dom";

function AdminLayout() {
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

export default AdminLayout;
