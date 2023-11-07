import React from 'react';
import styles from "./MemberManageLayout.module.css";
import {Outlet} from "react-router-dom";

const MemberManageLayout = () =>{
  return (
      <div className={styles.wrap}>
        <Outlet/>
      </div>
  );
}

export default MemberManageLayout;