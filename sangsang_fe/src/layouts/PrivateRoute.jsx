import {Navigate, Outlet} from "react-router-dom";
import {useAuth} from "../context/AuthContext";

const PrivateRoute = ({isAdmin}) => {
    const { user } = useAuth();

    if (!user.isAuthenticated) {
        return <Navigate to="/login" replace/>;
    }

    if (user.isAuthenticated && (isAdmin && !user.auth.includes("ADMIN"))) {
        return <Navigate to="/" />;
    }

     //if (user.isAuthenticated && ( user.auth.includes("ADMIN"))) {
      //   return <Navigate to="/admin" replace/>;
     //}


    return <Outlet/>;
};
export default PrivateRoute;