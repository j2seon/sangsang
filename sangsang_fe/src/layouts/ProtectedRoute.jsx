import {Navigate, Outlet} from "react-router-dom";
import {useAuth} from "../context/AuthContext";

const ProtectedRoute = ({children, isAdmin}) => {
    const { user } = useAuth();
    const isUserAdmin = user.isAuthenticated && (isAdmin && user.auth.includes("ADMIN"));

    if (!user.isAuthenticated) {
        return null;
    }

    if (user.isAuthenticated && !isUserAdmin) {
        return <Navigate to="/" replace/>;
    }
    console.log(user)

    return children ? children : <Outlet/>;
};

export default ProtectedRoute;