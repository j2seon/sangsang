export const handleImgError = (e) => {
  e.target.src = '/img/default.jpg';
};

export const renderImg = (isEditing= true , isChange= false, image, serverImg) => {
  // 등록시 isediting false change 아직은 false img 없음
  // 수정시 isediting true change false img 있음
  // 수정클릭 isediting tru change true img 잇음

  const isSame = image === serverImg;

   console.log('isSame', isSame)
   console.log('isChange', isChange)
   console.log('isEditing', isEditing)
   console.log('image', image)
   console.log('serverImg', serverImg)

  if(!image && !serverImg){
    return process.env.PUBLIC_URL + '/img/default.png';
  }

  if ((isEditing && isSame && !isChange)) {
    console.log("나")
    return process.env.REACT_APP_SERVER_IMG_URL + image;
  }else if (isEditing && !isSame && isChange) {
    console.log("나나나")
    return image;
  }else if ((!isEditing && isSame && !isChange) || (!isEditing && !isSame && !isChange)) {
    console.log("나나나22222")
    return process.env.REACT_APP_SERVER_IMG_URL + serverImg;
  }

}
