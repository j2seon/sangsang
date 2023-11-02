import {Navigate, Outlet} from "react-router-dom";
import {useAuth} from "../context/AuthContext";

const ProtectedRoute = () => {
    const { user } = useAuth();




    return <Outlet/>;
};

export default ProtectedRoute;