import React from 'react';
import styles from './Logo.module.css';
import {Link} from "react-router-dom";
function Logo(props) {
    return (
        <div className={props?.styles ?? styles.logo_wrap}>
            <Link to='/'>
                <img className={props?.imgSize} src='/logo192.png'/>
            </Link>
        </div>
    );
}

export default Logo;