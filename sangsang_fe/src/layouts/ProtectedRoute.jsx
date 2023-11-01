import {Navigate} from "react-router-dom";

const ProtectedRoute = ({ auth, children }) => {

    if (auth !== 'ROLE_ADMIN') {
        return <Navigate to="/dashboard" />;
    }

    return children;
};