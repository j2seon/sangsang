import Swal from "sweetalert2";
import axios from "axios";


const ACCESS_TOKEN = "accessToken";

export const login = async (user) => {
  const requestUrl = 'http://localhost:8081/auth/login';
  const header = {'Content-Type': 'application/json',}

  return await axios.post(requestUrl, user, {headers: header})
    .then(res => {
      console.log(res.data)
      return res.data;
    })
    .catch(err => {
      console.log(err.response)
      return err.response.data;
    });
};

export const logout = async () => {
  const requestUrl = 'http://localhost:8081/auth/logout';
  const header = {'Content-Type': 'application/json'}

  return await axios.post(requestUrl, {headers: header})
    .then(res => {
      console.log(res);
      localStorage.removeItem(ACCESS_TOKEN);
      return res.data;
    })
    .catch(err => {
      console.log(err);
    });
};


export const refresh = async () => {
  const requestUrl = '/auth/reissue';
  const header = {'Content-Type': 'application/json',}

  return await axios.post(requestUrl, {headers: header});
};