import {createContext, useContext, useEffect, useMemo, useState} from "react";
import {login, logout} from "../api/auth/authApi";
import {decodeJwt} from "../util/tokenUtil";


const AuthContext = createContext();


export const AuthContextProvider = ({children}) => {
    //const accessToken = localStorage.getItem("accessToken");
    const [accessToken, setAccessToken] = useState(localStorage.getItem("accessToken"));
    console.log(accessToken)
    // 유저 내용 담기
    const [user, setUser] = useState({
        isAuthenticated: false,
        auth: "",
        memberId: '',
        profile: "",
    });
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        console.log("here")
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
        setLoading(false)
    }, [accessToken]);

    const contextValue = useMemo(
      () => ({
          accessToken,
          setAccessToken,
          user,
          setUser
      }),
      [accessToken, user]
    );


    return(<AuthContext.Provider value={{contextValue, login, logout, loading}}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => useContext(AuthContext);
