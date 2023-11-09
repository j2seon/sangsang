import React, {useEffect, useState} from 'react';
import styles from "./UserAddPage.module.css";
import ImageInput from "../../components/common/input/ImageInput";
import InputEle from "../../components/common/input/Input";
import ButtonInline from "../../components/common/button/ButtomInline";
import Modal from "../../components/common/modal/Modal";
import DaumPostcode from "react-daum-postcode";
import {selectMember} from "../../api/admin/adminApi";
import {useParams} from "react-router-dom";
import {useQuery} from "@tanstack/react-query";
import {LoadingSpinner} from "../../components/common/other/LoadingSpinner";
import AdminHeader from "../../components/admin/AdminHeader";
import SelectEle from "../../components/common/select/SelectEle";
import {changeAuth} from "../../util/validationUtil";

function UserInfo() {
  const {userId} = useParams();

  const [isEditing, setIsEditing] = useState(false);
  const [open, setOpen] = useState(false);

  const [form, setForm] = useState({
    memberId: '',
    memberName: '',
    auth: '',
    profile: '',
    zipCode: '',
    address: '',
    addressDetail: ''
  });

  const {isLoading, isError, data} = useQuery({
    queryKey: [userId],
    queryFn: async () => selectMember(userId),
    staleTime: 60 * 1000,
  })

  useEffect(() => {
    if (data) {
      // Update the form state with the data
      setForm((prev) => ({
        ...prev,
        ...data.data, auth: changeAuth(data.data.auth)
      }));
    }
  }, [data, userId]);

  const style = {
    maxWidth: '150px'
  }


  const handleOpenModal = () => {
    setOpen(true);
  }

  const handleCloseModal = () => {
    setOpen(false);
  }
  const handleEditClick = () => {
    setIsEditing(!isEditing);
  };


  const handleChange = (e) => {
    const {value, name} = e.target;
    console.log(value, name)
    setForm((prevForm) => ({
      ...prevForm,
      [name]: value
    }));
    console.log(form)
  }
  const handleFile = (file) => {
    setForm((prev) => ({
      ...prev,
      'profile': file
    }));
  }

  const handleComplete = (data) => {
    let fullAddress = data.address;
    const code = data.zonecode;
    let extraAddress = '';

    if (data.addressType === 'R') {
      if (data.bname !== '') {
        extraAddress += data.bname;
      }
      if (data.buildingName !== '') {
        extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName);
      }
      fullAddress += (extraAddress !== '' ? ` (${extraAddress})` : '');
    }
    setForm({ ...form, address : fullAddress, zipCode: code});
    setOpen(false);
  }

  if (isLoading) {
    return <LoadingSpinner/>;
  }

  if (isError) {

    return <div> 오류발생! 오류페이지로 대체하자</div>
  }

  return (
    <>
      {
        isEditing ?
          <AdminHeader
            value={"회원수정"}
          />  
          :
          <AdminHeader
            value={"회원세부"}
          />
      }
      <div className={styles.container}>
        <ImageInput
          style={style}
          img={form.profile}
          isEditing={isEditing}
          onImageChange={handleFile}
        />
        <div className={styles.input_wrap}>
          <div className={styles.space}>
            <label htmlFor="memberId">아이디</label>
            <InputEle
              id="memberId"
              name='memberId'
              value={form.memberId}
              placeholder="아이디"
              disabled
              onChange={handleChange}
            />
          </div>
          <div className={styles.space}>
            <label htmlFor="memberName">이름</label>
            <InputEle
              id="memberName"
              name='memberName'
              placeholder="이름"
              value={form.memberName}
              disabled={!isEditing}
              onChange={handleChange}
            />
          </div>
          <div className={styles.space}>
            <label htmlFor="auth">권한</label>
            <SelectEle
              id="auth"
              name="auth"
              disabled={!isEditing}
              value={form.auth}
              onChange={handleChange}
              options={[
                {id:1, value:'ADMIN', text:'관리자'},
                {id:2, value:'USER', text:'일반회원'},
              ]}
            />
          </div>
          <div className={styles.space}>
            <label htmlFor="address">주소</label>
            <div className={styles.flex}>
              <InputEle
                id="address"
                name='address'
                placeholder="주소"
                value={form.address}
                disabled={!isEditing}
                onChange={handleChange}
              />
              {!isEditing ? "" :
                <>
                  <ButtonInline onClick={handleOpenModal} value="검색" style={{width: '100px'}}/>
                  {open && (
                    <Modal
                      title="주소검색"
                      onCancel={handleCloseModal}
                    >
                      <DaumPostcode
                        onComplete={handleComplete}
                      />
                    </Modal>
                  )}
                </>
              }
            </div>
            <InputEle
              id="addressDetail"
              name='addressDetail'
              placeholder="상세주소"
              value={form.addressDetail}
              disabled={!isEditing}
              onChange={handleChange}
            />
          </div>
        </div>
        <div>
          {
            isEditing ?
              <>
                <ButtonInline
                  style={{padding: '10px 15px', marginRight: '5px'}}
                  // onClick={se}
                  value='완료'
                />
                <ButtonInline
                  style={{padding: '10px 15px'}}
                  onClick={handleEditClick}
                  value='취소'
                />
              </>
              :
              <ButtonInline
                style={{padding: '10px 15px'}}
                onClick={handleEditClick}
                value='수정'
              />
          }
        </div>
      </div>
    </>
  );
}


export default UserInfo;
