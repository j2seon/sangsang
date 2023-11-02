import React from 'react';
import {NavLink} from "react-router-dom";
import styles from './SidbarItem.module.css';


function SideBarItem({icon, text, url}) {
    return (
        <li className={styles.li}>
            <NavLink to={url}>
                <div className={styles.text_wrap}>
                    <p>{icon}</p>
                    <p className={styles.text}>{text}</p>
                </div>
            </NavLink>
        </li>
    );
}

export default SideBarItem;
