import {createContext, useContext, useEffect, useState} from "react";
import {login, logout} from "../api/auth/authApi";


const AuthContext = createContext();

export const AuthContextProvider = ({children}) => {
    
    // 유저 내용 담기
    const [user, setUser] = useState({
        isAuthenticated: true,
        auth: "ROLE_",
        memberId: "임시",
    });

    useEffect(()=>{

    }, []);



    return(<AuthContext.Provider value={{user, login, logout}}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => useContext(AuthContext);
