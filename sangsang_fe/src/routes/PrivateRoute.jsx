import {Navigate, Outlet} from "react-router-dom";
import {useAuth} from "../context/AuthContext";

const PrivateRoute = ({children}) => {
    const { contextValue: {accessToken, user}, loading } = useAuth();
    //const isUserAdmin = user.isAuthenticated && user.auth.includes("ADMIN");
    console.log(user)

    if(loading){
        return null;
    }

    if(!accessToken || !user.isAuthenticated){
        return <Navigate to="/login" replace={true}/>;
    }


    return  children ? children : <Outlet/>;
};
export default PrivateRoute;