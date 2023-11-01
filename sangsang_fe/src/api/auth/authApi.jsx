import Swal from "sweetalert2";
import axios from "axios";


const ACCESS_TOKEN = "ACCESS_TOKEN";

export const login = async (user)  => {
    // axios

};

export const logout = async () => {

    localStorage.removeItem(ACCESS_TOKEN);

};