import React from 'react';
import styles from "./MemberManageLayout.module.css";
import {Outlet} from "react-router-dom";
import {EditProvider} from "../context/EditContext";

const MemberManageLayout = () =>{
  return (
    <EditProvider>
      <div className={styles.wrap}>
        <Outlet/>
      </div>
    </EditProvider>
  );
}

export default MemberManageLayout;