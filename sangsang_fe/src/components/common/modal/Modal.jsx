import React from 'react';
import styles from './Modal.module.css';
import ButtonInline from "../button/ButtomInline";
function Modal({children, title, onConfirm, onCancel}) {

    const style = {
        padding: '10px 15px',
        marginLeft: '10px'
    };

    return (
        <div className={styles.modal}>
            <div className={styles.modal_content}>
                <h2 className={styles.title}>{title}</h2>
                <div className={styles.main_content}>
                    {children}
                </div>
                <div className={styles.btn_wrap}>
                    <ButtonInline
                      value="확인"
                      onClick={onConfirm}
                      style={style}
                    />
                    <ButtonInline
                      value="취소"
                      onClick={onCancel}
                      style={style}
                    />
                </div>

            </div>
        </div>
    );
}

export default Modal;