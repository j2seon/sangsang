import {api} from "../customAxios";
import { message } from 'antd';

export const memberAdd = async (user)  => {
  const requestUrl = '/api/v1/member/join';

  return await api.post(requestUrl, user)
    .then(res => {
      message.success("회원등록완료")
      return res.data;
    })
    .catch(err => {
      console.log(err)
      message.error(err.response.data.message);
      throw err;
    });
};

export const selectMember = async (memberId)  => {
  const requestUrl = `/api/v1/member/${memberId}`;

  return await api.get(requestUrl)
    .then(res => {
      return res.data;
    })
    .catch(err => {
      message.error(err.response.data.message)
      throw err;
    });
};

export const memberList = async (pageInfo)  => {
  const requestUrl = `/api/v1/member/members`;
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
        message.error(err.response.data.message)
        console.log(err)
        throw err;
      });
};

export const memberUpdate = async (memberId, form)  => {
  const requestUrl = `/api/v1/member/${memberId}`;

  console.log(form)
  return await api.patch(requestUrl, form)
    .then(res => {
      message.success(res.data.message)
      return res.data;
    })
    .catch(err => {
      console.log(err)
      return err.response;
    });
};


// 탈퇴회원
export const memberWithdrawal = async (memberId)  => {
  const requestUrl = `/api/v1/member/withdrawal/${memberId}`;

  return await api.patch(requestUrl, memberId)
    .then(res => {
      return res.data;
    })
    .catch(err => {
      console.log(err)
      return err.response;
    });
};
