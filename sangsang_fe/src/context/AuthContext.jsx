import {createContext, useContext, useEffect, useState} from "react";
import {login, logout} from "../api/auth/authApi";
import {decodeJwt} from "../util/tokenUtil";


const AuthContext = createContext();

const initialState= {
    isAuthenticated: false,
    auth: "",
    memberId: "",
    profile: "",
}

export const AuthContextProvider = ({children}) => {
    const accessToken = localStorage.getItem("accessToken");

    // 유저 내용 담기
    const [user, setUser] = useState({
        isAuthenticated: !!accessToken ?? false,
        auth: "",
        memberId: "",
        profile: "",
    });

    useEffect(() => {
        if (accessToken) {
            const decodeToken = decodeJwt(accessToken);
            const newUser = {
                isAuthenticated: true,
                auth: decodeToken.auth,
                memberId: decodeToken.memberId,
                profile: decodeToken.profile,
            };
            setUser(newUser);
        }
    }, []);

    return(<AuthContext.Provider value={{user, setUser ,login, logout}}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => useContext(AuthContext);
