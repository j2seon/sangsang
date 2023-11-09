export const handleImgError = (e) => {
  e.target.src = '/img/default.jpg';
};

export const renderImg = (isEditing= false , isChange, image) => {
  // 등록시 isediting false change 아직은 false img 없음
  // 수정시 isediting true change false img 있음
  // 수정클릭 isediting tru change true img 잇음


  if (isEditing && image && !isChange) {
    console.log("dmdk")
    return process.env.REACT_APP_SERVER_IMG_URL + image;
  }else if (isEditing && image && isChange) {
    console.log(image)
    return image;
  }else if (!isEditing && image && !isChange) {
    console.log("dsf")
    return process.env.REACT_APP_SERVER_IMG_URL + image;
  } else {
    return process.env.PUBLIC_URL + '/img/default.png';
  }
}
