import { jwtDecode } from "jwt-decode";
export const decodeJwt= (token)=> {
    if (token === null) return null;
    return jwtDecode(token);
}