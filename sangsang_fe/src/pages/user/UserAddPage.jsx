import React, {useState} from 'react';
import InputEle from "../../components/common/input/Input";
import DaumPostcode from 'react-daum-postcode';
import Modal from "../../components/common/modal/Modal";
import ButtonInline from "../../components/common/button/ButtomInline";
import ImageInput from "../../components/common/input/ImageInput";
import styles from "./UserAddPage.module.css";
import {memberAdd, memberUpdate} from "../../api/admin/adminApi";
import AdminHeader from "../../components/admin/AdminHeader";
import {useEdit} from "../../context/EditContext";
import {useNavigate} from "react-router-dom";
import {useMutation, useQueryClient} from "@tanstack/react-query";

const style ={
  maxWidth: '150px'
}
function UserAddPage() {
  const [open, setOpen] = useState(false);
  const {setImgEdit } = useEdit();
  const navigate = useNavigate();
  const [form, setForm] = useState({
    id: '',
    pwd: '',
    name: '',
    profile: '',
    zipCode: '',
    address: '',
    addressDetail:''
  });
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationFn:  async (data) => memberAdd(data),
    onSuccess: () => {
      navigate("/admin/users");
      return queryClient.invalidateQueries({ queryKey: [form] })
    },
    onError: () =>{

    }
  })
  const handleChange = (e) => {
    const { value, name } = e.target;
    setForm((prevForm) => ({
      ...prevForm,
      [name]: value
    }));
    console.log(form)
  }

  const handleFile = (file) => {
    setForm((prevForm) => ({
      ...prevForm,
      'profile': file
    }));
    console.log(form);
  }

  const handleOpenModal = () => {
    setOpen(true);
  }

  const handleCloseModal = () => {
    setOpen(false);
  }

  const createFormData = ()=>{
    const formData = new FormData();
    formData.append('id', form.id);
    formData.append('pwd', form.pwd);
    formData.append('name', form.name);
    formData.append('profile', form.profile);
    formData.append('zipCode', form.zipCode);
    formData.append('address', form.address);
    formData.append('addressDetail', form.addressDetail);
    return formData;
  }

  const handleRequest = (e) => {
    e.preventDefault();
    const formData = createFormData();
    setImgEdit(false);
    mutation.mutate(formData);
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

  return (
      <>
        <AdminHeader
            value={"회원등록"}
        />
        <div className={styles.container}>
          <ImageInput
              style={style}
              onImageChange={handleFile}
          />
          <div className={styles.input_wrap}>
            <div className={styles.space}>
              <label htmlFor="id">아이디</label>
              <InputEle
                  id="id"
                  name='id'
                  placeholder="아이디"
                  onChange={handleChange}
              />
            </div>
            <div className={styles.space}>
              <label htmlFor="pwd">비밀번호</label>
              <InputEle
                  id="pwd"
                  name='pwd'
                  type="password"
                  placeholder="비밀번호"
                  onChange={handleChange}
              />
            </div>
            <div className={styles.space}>
              <label htmlFor="name">이름</label>
              <InputEle
                  id="name"
                  name='name'
                  placeholder="이름"
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
                    disabled={true}
                    onChange={handleChange}
                    style={{marginRight:'5px'}}
                />
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
              </div>
              <InputEle
                  id="addressDetail"
                  name='addressDetail'
                  placeholder="상세주소"
                  onChange={handleChange}
              />
            </div>
          </div>
          <div>
            <ButtonInline
                style={{padding:'10px 15px'}}
                onClick={handleRequest}
                value='등록'
            />
          </div>
        </div>
      </>

  );
}

export default UserAddPage;
