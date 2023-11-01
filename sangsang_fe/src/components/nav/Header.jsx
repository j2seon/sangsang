import React from 'react';
import TopNav from "./TopNav";
import styles from './Nav.module.css';
function Header() {
    return (
        <header className={styles.header}>
            <TopNav/>
        </header>
    );
}

export default Header;