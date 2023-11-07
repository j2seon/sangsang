import {createContext, useContext, useEffect, useState} from "react";
import {login, logout} from "../api/auth/authApi";
import {decodeJwt} from "../util/tokenUtil";


const AuthContext = createContext();

export const AuthContextProvider = ({children}) => {
    
    // 유저 내용 담기
    const [user, setUser] = useState({
        isAuthenticated: true,
        auth: "ADMIN",
        memberId: "admin",
    });

    useEffect(()=>{
        const accessToken = localStorage.getItem("accessToken");

        if (accessToken) {
            const decodeToken = decodeJwt(accessToken);
            const newUser = {
                isAuthenticated: true,
                auth: decodeToken.auth,
                memberId: decodeToken.memberId,
            };
            console.log("User Updated:", newUser); // Debugging: Log the updated user
            setUser(newUser);
        }
        console.log(user)
    }, []);



    return(<AuthContext.Provider value={{user, setUser ,login, logout}}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => useContext(AuthContext);
