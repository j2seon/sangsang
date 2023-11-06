import React from 'react';
import SideBarItem from "./SideBarItem";
import {PersonOutline} from "@mui/icons-material";
import ButtonInline from "../common/button/ButtomInline";
import styles from "./Sidebar.module.css";
import {logout} from "../../api/auth/authApi";
function AdminSidBar() {

    const buttonStyle = {
        width: '100%',
        padding: '15px',
};
    return (
        <>
            <div className={styles.side_list}>
                <ul>
                    <SideBarItem
                        url={"/admin/users"}
                        text="회원관리"
                        icon={<PersonOutline fontSize="large"/>}
                    />
                </ul>
            </div>
            <div className={styles.logout}>
                <ButtonInline
                  value="로그아웃"
                  style={buttonStyle}
                  onClick={logout}
                />
            </div>
        </>

    );
}

export default AdminSidBar;