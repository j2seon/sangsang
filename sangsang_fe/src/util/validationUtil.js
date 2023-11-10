export const changeAuth = (auth) => {
  return auth === 'ROLE_USER' ? "USER" : "ADMIN"
};


export const authKor = (auth) => {
  return auth === 'ROLE_USER' ? "일반회원" : "관리자"
};