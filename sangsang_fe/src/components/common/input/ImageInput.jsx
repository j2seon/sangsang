import React, {useEffect, useState} from 'react';
import styles from './input.module.css';
import CreateOutlinedIcon from '@mui/icons-material/CreateOutlined';
import {renderImg} from "../../../util/imgUtil";
import {useEdit} from "../../../context/EditContext";
import {useLocation} from "react-router-dom";

function ImageInput({onImageChange, style, img}) {
  const [image, setImage] = useState(img);
  const {formEdit, setFormEdit, imgEdit, setImgEdit } = useEdit();
  const location = useLocation();

  console.log(location.pathname)
  const handleImageChange = (e) => {
    const file = e.target.files[0];
    console.log(file)
    if (file) {
      const reader = new FileReader();
      reader.onload = (event) => {
        setImage(event.target.result);
        setImgEdit(true)
        onImageChange(file);
      };
      reader.readAsDataURL(file);
    }
  };

  return (
    <div className={styles.input_wrap}>
      <input
        type="file"
        id="imageInput"
        name="profile"
        style={{display: "none"}}
        onChange={handleImageChange}
        disabled={location.pathname === '/admin/users/add' ? false : !formEdit}
      />
      <div className={styles.img_label}>
        <label htmlFor="imageInput" >
          <div className={styles.img_wrap}>
            <img
              src={renderImg(formEdit, imgEdit, image, img)}
              alt="Profile"
              style={style}
              // onError={handleImgError}
            />
            <div className={styles.icon}>
              <CreateOutlinedIcon fontSize='sx'/>
            </div>
          </div>
        </label>
      </div>
    </div>
  );
}

export default ImageInput;