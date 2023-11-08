import React from 'react';
import styles from './Header.module.css';
import Logo from "../common/other/Logo";
import Avatar from "../common/other/Avatar";
import {useAuth} from "../../context/AuthContext";

export default function TopNav() {
    const {user} = useAuth();


    return (
        <div className={styles.topNav_wrap}>
            <Logo
                styles={styles.nav_logo_wrap}
                imgSize={styles.nav_logo}
            />
            <div className={styles.profile}>
                <p className={styles.profile_name}>{user.memberId}</p>
                <Avatar isLink={user.isAuthenticated}
                        profile={user.profile}
                />
            </div>
        </div>
    );
}
