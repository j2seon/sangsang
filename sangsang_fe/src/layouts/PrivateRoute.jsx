import {Navigate, Outlet} from "react-router-dom";
import {useAuth} from "../context/AuthContext";

// const PrivateRoute = ({children}) => {
//     const {user} = useAuth();
//
//
//     if (!user.isAuthenticated) {
//         return <Navigate to="/login" />;
//     }
//
//     return children;
// };

const PrivateRoute = ({children, isAdmin}) => {
    const { user } = useAuth();

    if (!user.isAuthenticated) {
        return <Navigate to="/login" replace/>;
    }

    if (user.isAuthenticated && (isAdmin && user.auth === 'ROLE_ADMIN')) {
        console.log("sjdurldd")
        return <Navigate to="/admin" replace/>;
    }

    return children;
};
export default PrivateRoute;