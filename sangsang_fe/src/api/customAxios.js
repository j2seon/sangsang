import axios from "axios";
import {refresh} from "./auth/authApi";
import { message } from 'antd';

export const api = axios.create({
  baseURL: process.env.REACT_APP_SERVER_BASE_URL,
  headers: {
    Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
  },
});


api.interceptors.request.use(function (config) {
  // 요청이 전달되기 전에 작업 수행
  config.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`;
  return config;
}, function (error) {

  return Promise.reject(error);
});

api.interceptors.response.use(
  (res) => {
    console.log(res)
    return res;
  }, async (error) => {
    const {config, response} = error;
    const originRequest = config;

    if (response.status === 401) {

      // if(originRequest.url === 'auth/logout'){
      //   localStorage.removeItem('accessToken');
      //   return Promise.reject(error);
      // }

      const response = await refresh();
      console.log(response)
      if (response.status === 200) {
        const newAccessToken = response.data.token;
        localStorage.setItem('accessToken', response.data.token);

        axios.defaults.headers.common.Authorization = `Bearer ${newAccessToken}`;
        originRequest.headers.Authorization = `Bearer ${newAccessToken}`;
        return axios(originRequest);
      } else if (response.status === 401) {
        localStorage.removeItem('accessToken');
        // 돔을 건드는거라 싫은데..............
        //window.location.href = '/login';
        console.log("d")
        message.error("다시 로그인 해주세요")
      }
    }
    return Promise.reject(error);
  });