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

export const selectMember = async (memberId)  => {
  const requestUrl = `api/v1/member/${memberId}`;
  const headers = {
    Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
  }
  return await api.get(requestUrl, {
    headers: headers
  })
    .then(res => {
      return res.data;
    })
    .catch(err => {
      console.error("요청 실패:", err);
      throw err;
    });
};

export const memberList = async (pageInfo)  => {
  const requestUrl = `api/v1/member/members`;
  const headers = {
    Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
  }

  return await api.get(requestUrl, {
    headers: headers,
    params : {
      kind: pageInfo.kind,
      content: pageInfo.content,
      size: pageInfo.size,
      page: pageInfo.page
    }
  })
      .then(res => {
        return res.data.data;
      })
      .catch(err => {
        console.error("요청 실패:", err);
        throw err;
      });
};

export const memberUpdate = async (user)  => {
  const requestUrl = '/api/v1/member/join';

  return await api.post(requestUrl, user )
    .then(res => {
      return res.data;
    })
    .catch(err => {
      console.log(err)
      return err.response;
    });
};
