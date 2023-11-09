import Swal from "sweetalert2";
import axios from "axios";
import {api} from "../customAxios";
import {useAuth} from "../../context/AuthContext";


const ACCESS_TOKEN = "accessToken";

export const login = async (user) => {
  const requestUrl = 'http://localhost:8081/auth/login';
  const header = {'Content-Type': 'application/json',}

  return await axios.post(requestUrl, user, {headers: header})
    .then(res => {
      const { accessToken, auth, memberId, profile } = res.data;
      localStorage.setItem('accessToken', accessToken);

      return { auth, memberId, isAuthenticated: true, profile: profile}
    })
    .catch(err => {
      console.log(err.response)
      return null;
    });
};

export const logout = async () => {
  const requestUrl = 'http://localhost:8081/auth/logout';
  const header = {'Content-Type': 'application/json'}

  return await api.post(requestUrl, {headers: header})
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