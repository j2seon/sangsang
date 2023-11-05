import {Navigate, Outlet} from "react-router-dom";
import {useAuth} from "../context/AuthContext";

const ProtectedRoute = ({children, isAdmin}) => {
    const { user } = useAuth();
    const isUserAdmin = user.isAuthenticated &&(isAdmin && user.auth.includes("ADMIN"));
    const isUser = user.isAuthenticated;

    if (!isUser) {
        return <Navigate to="/login" replace/>;
    }

    if(isUser){
        return children;
    }

    if (user.isAuthenticated && !isUserAdmin) {
        return <Navigate to="/" replace/>;
    }
    return children;
};

export default ProtectedRoute;