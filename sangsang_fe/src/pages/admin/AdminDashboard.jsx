import React from 'react';
import SideBar from "../../components/admin/AdminSidBar";
import styles from "./AdminDashborad.module.css"
import {Outlet} from "react-router-dom";
function AdminDashboard() {
  console.log(`한번`)
  return (
      <main style={{height:'100%', display:'flex', justifyContent:'center'}}>
        <p style={{fontSize:'2rem', display:'flex', alignItems:'center'}}>ADMIN MAIN PAGE</p>
      </main>
  );
}

export default AdminDashboard;