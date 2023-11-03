import axios from "axios";

const ACCESS_TOKEN = "accessToken";


const api = axios.create({
  baseURL: process.env.SERVER_BASE_URL,
  headers: { "Content-type": "application/json"},
  //timeout: 10000,
});


api.interceptors.request.use(function (config) {
  // 요청이 전달되기 전에 작업 수행
  const accessToken = localStorage.getItem(ACCESS_TOKEN);
  config.headers.common["Authorization"] = `Bearer ${accessToken}`

  return config;
}, function (error) {

  return Promise.reject(error);
});

api.interceptors.response.use(
  (res)=>{
    return res;
  }, async (error) => {
    const statusCode = error.response?.status;
  });