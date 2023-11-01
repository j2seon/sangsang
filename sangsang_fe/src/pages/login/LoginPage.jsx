import React, {useState} from 'react';
import InputEle from "../../components/common/input/Input";
import Logo from "../../components/common/other/Logo";
import styles from './LoginPage.module.css';
import ButtonInline from "../../components/common/button/ButtomInline";

function LoginPage() {
    const [user , setUser] = useState({
        id:'',
        pwd:'',
    });

    const handleChange = (e) => {
        const { value, name } = e.target;
        setUser({
            ...user,
            [name]: value
        });
        console.log(user)
    }

    const handleSubmit = () => {
        //로그인 api
    }

    return (
        <div className={styles.flex}>
            <div className={styles.login_wrap}>
                <Logo/>
                <div className={styles.input_wrap}>
                    <InputEle
                        name='id'
                        placeholder="아이디"
                        style={{marginBottom: '10px'}}
                        onChange={handleChange}
                    />
                    <InputEle
                        name='pwd'
                        placeholder="비밀번호"
                        onChange={handleChange}
                        type='password'
                    />
                    <ButtonInline
                        value='로그인'
                        style={{width : '100%', padding: '15px', margin :'10px 0px', fontSize: '1.2rem' }}
                        onClick={handleSubmit}
                    />
                </div>
            </div>
        </div>
    );
}

export default LoginPage;
