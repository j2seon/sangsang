import React, {useEffect, useState} from 'react';
import styles from './input.module.css';
import CreateOutlinedIcon from '@mui/icons-material/CreateOutlined';
import {handleImgError, renderImg} from "../../../util/imgUtil";

function ImageInput({onImageChange, style, img, isEditing= true}) {
  const [image, setImage] = useState(img);
  const [isChange, setIsChange] = useState(false);

  console.log("img : "+ img)
  console.log("image : "+ image)

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    console.log(file)
    if (file) {
      const reader = new FileReader();
      reader.onload = (event) => {
        setImage(event.target.result);
        setIsChange(true)
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
        disabled={!isEditing}
      />

      <div className={styles.img_label}>
        <label htmlFor="imageInput" >
          <div className={styles.img_wrap}>
            <img
              src={renderImg(isEditing, isChange, image)}
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