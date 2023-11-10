import {createContext, useContext, useState} from "react";


const EditContext = createContext();


// Context Provider 생성
export const EditProvider = ({ children }) => {
  //1. form edit 여부
  const [formEdit, setFormEdit] = useState(true);
  //2. 이미지 변경여부
  const [imgEdit, setImgEdit] = useState(false);

  const handleForm = ()=> setFormEdit(!formEdit);
  const handleImg = ()=> setImgEdit(!imgEdit);


  return (
    <EditContext.Provider value={{formEdit, setFormEdit, imgEdit, setImgEdit }}>
      {children}
    </EditContext.Provider>
  );
};

export const useEdit = () => useContext(EditContext);

