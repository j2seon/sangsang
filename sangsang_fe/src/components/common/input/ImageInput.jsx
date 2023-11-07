import React, {useState} from 'react';
import styles from './input.module.css';
import CreateOutlinedIcon from '@mui/icons-material/CreateOutlined';
import {handleImgError} from "../../../util/imgUtil";

function ImageInput({onImageChange, style, img}) {
  const [image, setImage] = useState(img);

  const notionToken = process.env.SERVER_IMG_URL;
  console.log(notionToken)
  console.log(img)

  const handleImageChange = (e) => {
    const file = e.target.files[0];

    if (file) {
      const reader = new FileReader();
      reader.onload = (event) => {
        setImage(event.target.result);
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
        style={{display: "none"}}
        onChange={handleImageChange}
      />

      <div className={styles.img_label}>
        <label htmlFor="imageInput" >
          <div className={styles.img_wrap}>
            <img
              src={process.env.ser+ `${img}`}
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