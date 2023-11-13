import axios from "axios";
import {api} from "../customAxios";
import { message } from 'antd';


const ACCESS_TOKEN = 'accessToken';
export const login = async (user) => {
  const requestUrl = 'http://localhost:8081/auth/login';
  const header = {'Content-Type': 'application/json',}

  return await axios.post(requestUrl, user, {headers: header})
    .then(res => {

      const {profile, tokenDto:{accessToken, accessTokenExpiredTime, auth, memberId,}} = res.data.data;
      message.success(memberId +"님 환영합니다.")
      localStorage.setItem("accessToken", accessToken);
      return {isAuthenticated: !!accessToken ,accessToken:accessToken, auth:auth, profile:profile, memberId:memberId }
    })
    .catch(err => {
      console.log(err.response.data)
      const msg = err.response.data.message;
      message.error(msg)
      return null;
    });
};

export const logout = async () => {
  const requestUrl = '/auth/logout';

  return await api.post(requestUrl)
    .then(res => {
      console.log(res)
      message.success("로그아웃 하셨습니다.")
      localStorage.removeItem(ACCESS_TOKEN);
      return res.data;
    })
    .catch(err => {
      console.log(err)
      return null;
    });
};


export const refresh = async () => {
  const requestUrl = 'http://localhost:8081/auth/reissue';
  const header = {'Content-Type': 'application/json',}

  return await axios.post(requestUrl, {headers: header})
    .then(res => {
      console.log(res);
      return res.data;
    })
    .catch(err => {
      console.log(err)
      return err.response;
    });
};