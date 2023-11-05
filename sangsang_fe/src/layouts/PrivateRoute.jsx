import {Navigate, Outlet} from "react-router-dom";
import {useAuth} from "../context/AuthContext";

const PrivateRoute = ({isAdmin}) => {
    const { user } = useAuth();
    const isUserAdmin = user.isAuthenticated && user.auth.includes("ADMIN");

    if (!user.isAuthenticated) {
        return <Navigate to="/login" replace/>;
    }

    if (user.isAuthenticated && !isUserAdmin) {
        return <Navigate to="/" replace/>;
    }

    return <Outlet/>;
};
export default PrivateRoute;