import { FadeLoader } from 'react-spinners';
import styles from './LoadingSpiner.module.css';

export const LoadingSpinner = () => {
    return <div className={styles.loading}>
                <FadeLoader color='#6CE5FF' />
           </div>;
}