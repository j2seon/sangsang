import toast from 'react-hot-toast';
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

function UserInfo() {
  const {userId} = useParams();

  const [isEditing, setIsEditing] = useState(false);
  const [open, setOpen] = useState(false);

  const [form, setForm] = useState({
    memberId: '',
    memberPwd: '',
    memberName: '',
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
        ...data.data
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
    setForm((prevForm) => ({
      ...prevForm,
      [name]: value
    }));
  }
  const handleFile = (file) => {
    setForm((prev) => ({
      ...prev,
      profile: file
    }));
  }

  // const handleComplete = (data) => {
  //   let fullAddress = data.address;
  //   const code = data.zonecode;
  //   let extraAddress = '';
  //
  //   if (data.addressType === 'R') {
  //     if (data.bname !== '') {
  //       extraAddress += data.bname;
  //     }
  //     if (data.buildingName !== '') {
  //       extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName);
  //     }
  //     fullAddress += (extraAddress !== '' ? ` (${extraAddress})` : '');
  //   }
  //   setForm({...form, address: fullAddress, zipCode: code});
  //   setOpen(false);
  // }

  if (isLoading) {
    return <LoadingSpinner/>;
  }

  if (isError) {

    return <div> 오류발생! 오류페이지로 대체하자</div>
  }

  return (
    <div className={styles.wrap}>
      <div className={styles.title}>회원 조회</div>
      <div className={styles.container}>
        {/* avatar로 바꾸면 될듯 */}
        <ImageInput
          style={style}
          img={form.profile}
          onImageChange={handleFile}
        />
        <div className={styles.input_wrap}>
          <div className={styles.space}>
            <label htmlFor="id">아이디</label>
            <InputEle
              id="id"
              name='id'
              value={form.memberId}
              placeholder="아이디"
              disabled={!isEditing}
              onChange={handleChange}
            />
          </div>
          {
            isEditing ?
              <div className={styles.space}>
                <label htmlFor="pwd">비밀번호</label>
                <InputEle
                  id="pwd"
                  name='pwd'
                  type="password"
                  value={form.memberPwd}
                  placeholder="비밀번호"
                  onChange={handleChange}
                />
              </div>
              : ""
          }
          <div className={styles.space}>
            <label htmlFor="name">이름</label>
            <InputEle
              id="name"
              name='name'
              placeholder="이름"
              value={form.memberName}
              disabled={!isEditing}
              onChange={handleChange}
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
                  <ButtonInline value="검색" style={{width: '100px'}}/>
                  {open && (
                    <Modal
                      title="주소검색"
                      // onConfirm={}
                      onCancel={handleCloseModal}
                    >
                      <DaumPostcode
                        // onComplete={handleComplete}
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
    </div>

  );
}


export default UserInfo;
