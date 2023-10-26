import {createContext, useState} from "react";


const AuthContext = createContext();

export const AuthContextProvider = ({children}) => {
    const [auth, setAuth ] = useState(); // 권한 관련

    // 사용자 인증 및 권한 체크 로직 작성

    return(<AuthContext.Provider>
            {children}
        </AuthContext.Provider>
    );
}

