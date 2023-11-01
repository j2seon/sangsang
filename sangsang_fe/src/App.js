import './App.css';
import './components/common/default.css';
import './components/common/input/Input'
import {Navigate, Outlet, Route, Routes} from "react-router-dom";
import Layouts from "./layouts/Layouts";
import {AuthContextProvider, useAuth} from "./context/AuthContext";
import LoginPage from "./pages/login/LoginPage";
import Main from "./pages/main/Main";
import AdminMain from "./pages/admin/AdminMain";
import PrivateRoute from "./layouts/PrivateRoute";


function App() {
  return (
      <AuthContextProvider>
        <Routes >
          <Route element={<Layouts/>}>
            <Route path="/" element={<PrivateRoute/>}/>
            <Route path="/dashboard" element={<PrivateRoute><Main/></PrivateRoute>}/>
            <Route path="/admin" element={<PrivateRoute isAdmin={true}><AdminMain/></PrivateRoute>}/>
          </Route>
          <Route path="/login" element={<LoginPage/>}/>

        </Routes>
      </AuthContextProvider>
  );
}

export default App;
