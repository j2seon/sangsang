import {createContext, useContext, useEffect, useState} from "react";
import {login, logout} from "../api/auth/authApi";


const AuthContext = createContext();

export const AuthContextProvider = ({children}) => {
    
    // 유저 내용 담기
    const [user, setUser] = useState({
        //token: localStorage.getItem("ACCESS_TOKEN"),
        token: 'z',
        auth: "",
        memberId: "임시",
    });


    return(<AuthContext.Provider value={{user, login, logout}}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => useContext(AuthContext);
