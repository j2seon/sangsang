import React from 'react';
import SideBar from "../../components/admin/AdminSidBar";
import styles from "./AdminDashborad.module.css"
import {Outlet} from "react-router-dom";
function AdminDashboard() {
  console.log(`한번`)
  return (
      <main>
        어드민 메인
      </main>
  );
}

export default AdminDashboard;