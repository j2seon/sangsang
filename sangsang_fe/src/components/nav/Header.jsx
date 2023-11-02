import React from 'react';
import TopNav from "./TopNav";
import styles from './Header.module.css';
function Header() {
    return (
        <header className={styles.header}>
            <TopNav/>
        </header>
    );
}

export default Header;