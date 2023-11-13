import React, {useEffect, useState} from 'react';
import InputEle from "../../components/common/input/Input";
import Logo from "../../components/common/other/Logo";
import styles from './LoginPage.module.css';
import ButtonInline from "../../components/common/button/ButtomInline";
import {useAuth} from "../../context/AuthContext";
import {Navigate, useNavigate} from "react-router-dom";
import toast from "react-hot-toast";

function LoginPage() {
    const navigate = useNavigate();
    const {user, login, setUser} = useAuth();

    const [form , setForm] = useState({
        id:'',
        pwd:'',
    });

    const handleChange = (e) => {
        const { value, name } = e.target;
        setForm({
            ...form,
            [name]: value
        });
    }

    const handleSubmit = async () => {
        const loginUser = await login(form);
        console.log(loginUser)
        if (!loginUser) {
            return ;
        }

        setUser(loginUser);

        if(loginUser.auth.includes("ADMIN")){
            navigate("/admin");
        }else{
            navigate("/");
        }
    }

    console.log(user)
    if(user?.isAuthenticated && !user?.auth.includes("ADMIN")){
        console.log("이동")
        return <Navigate to="/" replace/>;
    }

    if(user?.isAuthenticated && user?.auth.includes("ADMIN")){
        console.log("이동a")
        return <Navigate to="/admin" replace/>;
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
