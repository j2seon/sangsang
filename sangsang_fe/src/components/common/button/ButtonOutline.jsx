import styles from './button.module.css';
const ButtonOutline = ({style, value, onClick, isCancel, className}) => {

    className = !className && [styles.btn, styles.colorHard, isCancel? styles.cancelOutline : ''].join(' ');

    return (
        <button className={className} onClick={onClick} style={style}>{value}</button>
    )
}

export default ButtonOutline;