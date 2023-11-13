import React, {useEffect, useState} from 'react';
import styles from "./UserAddPage.module.css";
import ImageInput from "../../components/common/input/ImageInput";
import InputEle from "../../components/common/input/Input";
import ButtonInline from "../../components/common/button/ButtomInline";
import Modal from "../../components/common/modal/Modal";
import DaumPostcode from "react-daum-postcode";
import {memberAdd, memberUpdate, selectMember} from "../../api/admin/adminApi";
import {useParams} from "react-router-dom";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {LoadingSpinner} from "../../components/common/other/LoadingSpinner";
import AdminHeader from "../../components/admin/AdminHeader";
import SelectEle from "../../components/common/select/SelectEle";
import {changeAuth} from "../../util/validationUtil";
import {useEdit} from "../../context/EditContext";



function UserInfo() {
  const queryClient = useQueryClient();
  const {userId} = useParams();

  const {formEdit, setFormEdit, imgEdit, setImgEdit } = useEdit();

  console.log(formEdit)

  const [open, setOpen] = useState(false);

  const [form, setForm] = useState({
    memberId: userId,
    memberName: '',
    auth: '',
    profile: '',
    zipCode: '',
    address: '',
    addressDetail: ''
  });


  // 1. 조회용 api 요청
  const {isPending, isError, data} = useQuery({
    queryKey: [userId],
    queryFn: async () => selectMember(userId),
    staleTime: 60 * 1000,
  })

  //2. 수정용 api 요청
  const mutation = useMutation({
    mutationFn:  async (data) => memberUpdate(userId, data),
    onSuccess: () => {
      return queryClient.invalidateQueries({ queryKey: [userId] })
    },
  })

  console.log()
  const createFormData = ()=>{
    setForm((prev)=> ({...prev, memberName:data.data.memberName}));

    console.log(form)

    const formData = new FormData();
    formData.append('memberName', form.name || data.data.memberName);
    formData.append('auth', form.auth || changeAuth(data.data.auth));
    formData.append('zipCode', form.zipCode || data.data.zipCode);
    formData.append('address', form.address || data.data.address);
    formData.append('addressDetail', form.addressDetail || data.data.addressDetail);
    formData.append('profile', form.profile || data.data.profile);

    return formData;
  }

  const handleRequest = (e) => {
    e.preventDefault();
    const formData = createFormData();

    mutation.mutate(formData);
    handleEditClick();
    //setImgEdit(!imgEdit);
  }

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
    setFormEdit(!formEdit);
  };


  const handleChange = (e) => {
    const {value, name} = e.target;
    console.log(value, name)
    setForm((prevForm) => ({
      ...prevForm,
      [name]: value
    }));
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

  if (isPending) {
    return <LoadingSpinner/>;
  }

  if (isError) {
    return <div> 오류발생! 오류페이지로 대체하자</div>
  }

  return (
    <>
      {
        formEdit ?
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
          img={data.data.profile}
          onImageChange={handleFile}
        />
        <div className={styles.input_wrap}>
          <div className={styles.space}>
            <label htmlFor="memberId">아이디</label>
            <InputEle
              id="memberId"
              name='memberId'
              value={data.data.memberId}
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
              defaultValue={data.data.memberName}
              // value={data.data.memberName}
              disabled={!formEdit}
              onChange={handleChange}
            />
          </div>
          <div className={styles.space}>
            <label htmlFor="auth">권한</label>
            <SelectEle
              id="auth"
              name="auth"
              disabled={!formEdit}
              // value={changeAuth(data.data.auth)}
              defaultValue={changeAuth(data.data.auth)}
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
                // value={data.data.address}
                defaultValue={data.data.address}
                disabled={!formEdit}
                onChange={handleChange}
              />
              {!formEdit ? "" :
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
              defaultValue={data.data.addressDetail}
              disabled={!formEdit}
              onChange={handleChange}
            />
          </div>
        </div>
        <div>
          {
            formEdit ?
              <>
                <ButtonInline
                  style={{padding: '10px 15px', marginRight: '5px'}}
                  onClick={handleRequest}
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
