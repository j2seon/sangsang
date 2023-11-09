export const changeAuth = (auth) => {
  return auth === 'ROLE_USER' ? "USER" : "ADMIN"
};