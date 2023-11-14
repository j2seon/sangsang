import {Navigate, Outlet} from "react-router-dom";
import {useAuth} from "../context/AuthContext";
import {message} from "antd";

const ProtectedRoute = ({children, isAdmin}) => {
    const { contextValue: { user, accessToken }, loading } = useAuth();
    const isUserAdmin = user.isAuthenticated && (isAdmin && user.auth.includes("ADMIN"));

  if(loading){
    return null;
  }

    // if (!accessToken || !user.isAuthenticated) {
    //     return <Navigate to="/login" replace={true}/>;
    // }

  console.log(isUserAdmin)
    if (!isUserAdmin) {
      console.log(user)
      message.error("접근 권한이 없습니다")
      return <Navigate to="/" replace={true}/>;
   }

    return children ? children : <Outlet/>;
};

export default ProtectedRoute;