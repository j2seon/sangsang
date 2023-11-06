import React, {useEffect, useState} from 'react';
import styles from "./UserAddPage.module.css";
import ImageInput from "../../components/common/input/ImageInput";
import InputEle from "../../components/common/input/Input";
import ButtonInline from "../../components/common/button/ButtomInline";
import Modal from "../../components/common/modal/Modal";
import DaumPostcode from "react-daum-postcode";
import {memberAdd} from "../../api/admin/adminApi";

function UserInfo() {
    const [data, setForm] = useState({
        id: '',
        pwd: '',
        name: '',
        profile: '',
        zipCode: '',
        address: '',
        addressDetail:''
    });

    useEffect(() => {},[]); // 조회 api 날리기

    const style ={
        maxWidth: '150px'
    }


    return (
        <div className={styles.wrap}>
            <div className={styles.title}>회원 조회</div>
            <div className={styles.container}>
                {/* avatar로 바꾸면 될듯 */}
                <ImageInput
                    style={style}
                />
                <div className={styles.input_wrap}>
                    <div className={styles.space}>
                        <label htmlFor="id">아이디</label>
                        <InputEle
                            id="id"
                            name='id'
                            value={data.id}
                            placeholder="아이디"
                        />
                    </div>
                    <div className={styles.space}>
                        <label htmlFor="pwd">비밀번호</label>
                        <InputEle
                            id="pwd"
                            name='pwd'
                            type="password"
                            value={data.pwd}
                            placeholder="비밀번호"
                        />
                    </div>
                    <div className={styles.space}>
                        <label htmlFor="name">이름</label>
                        <InputEle
                            id="name"
                            name='name'
                            placeholder="이름"
                        />
                    </div>
                    <div className={styles.space}>
                        <label htmlFor="address">주소</label>
                        <div className={styles.flex}>
                            <InputEle
                                id="address"
                                name='address'
                                placeholder="주소"
                                value={data.address}
                                disabled={true}
                            />
                            {/*<ButtonInline  value="검색" style={{width: '100px'}}/>*/}
                        </div>
                        <InputEle
                            id="addressDetail"
                            name='addressDetail'
                            placeholder="상세주소"
                            value={data.addressDetail}
                        />
                    </div>

                </div>
                <div>
                    <ButtonInline
                        style={{padding:'10px 15px'}}
                        // onClick={handleRequest}
                        value='수정'
                    />
                </div>
            </div>
        </div>

    );
}

export default UserInfo;
