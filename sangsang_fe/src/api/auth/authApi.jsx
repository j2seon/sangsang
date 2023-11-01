import Swal from "sweetalert2";
import axios from "axios";


const ACCESS_TOKEN = "ACCESS_TOKEN";

export const login = async (user)  => {
    const requestUrl = 'http://localhost:8081/auth/login';
    const header = {'Content-Type': 'application/json',}

    return await axios.post(requestUrl, user, {headers: header})
            .then(res => {
                console.log(res);
                return res.data;
            })
            .catch(err => {
                console.log(err);
            });
};

export const logout = async () => {
    const requestUrl = 'http://localhost:8081/auth/login';
    const header = {'Content-Type': 'application/json',}
    localStorage.removeItem(ACCESS_TOKEN);

};