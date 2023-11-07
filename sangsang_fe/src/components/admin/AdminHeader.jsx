import React from 'react';
import styles from "./AdminHeader.module.css";

const AdminHeader = (props) => {
  return (
      <h1
          className={styles.title}
          style={props.style}
          value={props.value}
      >
        {props?.value}
      </h1>
  );
}

export default AdminHeader;