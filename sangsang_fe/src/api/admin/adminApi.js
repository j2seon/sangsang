import axios from "axios";
import {api} from "../customAxios";

export const memberAdd = async (user)  => {
  const requestUrl = '/api/v1/member/join';

  console.log(user)
  return await api.post(requestUrl, user )
    .then(res => {
      console.log(res)
      return res.data;
    })
    .catch(err => {
      console.log(err)
      return err.response;
    });
};
