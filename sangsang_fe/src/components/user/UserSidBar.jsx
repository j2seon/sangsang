import React from 'react';
import {useNavigate} from "react-router-dom";
import {useAuth} from "../../context/AuthContext";
import {logout} from "../../api/auth/authApi";
import styles from "../nav/Sidebar.module.css";
import SideBarItem from "../nav/SideBarItem";
import {PersonOutline} from "@mui/icons-material";
import ButtonInline from "../common/button/ButtomInline";

const initialState= {
  isAuthenticated: false,
  auth: "",
  memberId: "",
  profile: "",
}

function UserSidBar(props) {
  const navigate = useNavigate();
  const {contextValue:{setUser}} = useAuth();
  const buttonStyle = {
    width: '100%',
    padding: '15px',
  };

  const handelLogout =  ()=>{
    logout().then(res=>{
      setUser(initialState);
      navigate("/login");
    })
  }

  return (
    <>
      <div className={styles.side_list}>
        <ul>
          <SideBarItem
            url={"/info"}
            text="내 정보"
            icon={<PersonOutline fontSize="large"/>}
          />
        </ul>
      </div>
      <div className={styles.logout}>
        <ButtonInline
          value="로그아웃"
          style={buttonStyle}
          onClick={handelLogout}
        />
      </div>
    </>

  );
}

export default UserSidBar;