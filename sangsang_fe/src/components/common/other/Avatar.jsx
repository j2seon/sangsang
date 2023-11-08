import React from 'react';
import styles from './Avatar.module.css';
import {Link} from "react-router-dom";

function Avatar({isLink, profile, custom}) {
    const onErrorImg = (e) => {
        e.target.src = '/img/snow.jpg';
    }

    return (
        isLink ?
            <div className={styles.avatar_wrap} style={custom}>
                <Link to={isLink}>
                    <img className={styles.img} src={profile ? process.env.REACT_APP_SERVER_IMG_URL + profile : '/img/snow.png'} alt='user_profile' onError={onErrorImg}/>
                </Link>
            </div>
            :
            <div className={styles.avatar_wrap} style={custom}>
                <img className={styles.img} src='/img/snow.png' alt='user_profile' onError={onErrorImg}/>
            </div>
    );
}

export default Avatar;