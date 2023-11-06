import axios from "axios";
import {refresh} from "./auth/authApi";

const ACCESS_TOKEN = "accessToken";

export const api = axios.create({
  baseURL: 'http://localhost:8081',
  headers: {
    Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
  },
  // headers: {"Content-type": "application/json"},
  //timeout: 10000,
});

api.interceptors.request.use(function (config) {
  // 요청이 전달되기 전에 작업 수행
  return config;
}, function (error) {

  return Promise.reject(error);
});

api.interceptors.response.use(
  (res) => {
    console.log(res)
    return res;
  }, async (error) => {
    console.log(error)
    const {config, response} = error;

    if (response.status === 401) {
      const originRequest = config;

      const response = await refresh();
      if (response.status === 200) {
        const newAccessToken = response.data.token;
        localStorage.setItem('accessToken', response.data.token);

        axios.defaults.headers.common.Authorization = `Bearer ${newAccessToken}`;
        originRequest.headers.Authorization = `Bearer ${newAccessToken}`;
        return axios(originRequest);
      } else if (response.status === 401) {
        localStorage.removeItem('accessToken');

      }
    }
    return Promise.reject(error);
  });