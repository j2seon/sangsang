import axios from "axios";

export const memberAdd = async (user)  => {
  const requestUrl = 'http://localhost:8081/auth/login';
  const header = {'Content-Type': 'application/json',}

  return await axios.post(requestUrl, user, {headers: header})
    .then(res => {
      return res.data;
    })
    .catch(err => {
      return err.response.data;
    });
};
