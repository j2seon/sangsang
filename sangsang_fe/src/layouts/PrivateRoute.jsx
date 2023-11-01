import {Navigate} from "react-router-dom";
import {useAuth} from "../context/AuthContext";

const PrivateRoute = ({children}) => {
    const {user} = useAuth();


    if (!user.token) {
        return <Navigate to="/login" />;
    }

    return children;
};

export default PrivateRoute;