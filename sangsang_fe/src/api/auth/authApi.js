import Swal from "sweetalert2";
import axios from "axios";
import {api} from "../customAxios";
import {useAuth} from "../../context/AuthContext";
import toast from "react-hot-toast";


const ACCESS_TOKEN = "accessToken";

export const login = async (user) => {
  const requestUrl = 'http://localhost:8081/auth/login';
  const header = {'Content-Type': 'application/json',}

  return await axios.post(requestUrl, user, {headers: header})
    .then(res => {

      const {profile, tokenDto:{accessToken, accessTokenExpiredTime, auth, memberId,}} = res.data.data;

      localStorage.setItem("accessToken", accessToken);

      return {isAuthenticated: !!accessToken ,accessToken:accessToken, auth:auth, profile:profile }
    })
    .catch(err => {
      console.log(err.response.data)
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
      //에러 창 보여주는거
      return null;
    });
};


export const refresh = async () => {
  const requestUrl = '/auth/reissue';
  const header = {'Content-Type': 'application/json',}

  return await axios.post(requestUrl, {headers: header});
};