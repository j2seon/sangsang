import './App.css';
import './components/common/default.css';
import './components/common/input/Input'
import {Navigate, Outlet, Route, Routes} from "react-router-dom";
import Layouts from "./layouts/Layouts";
import {AuthContextProvider, useAuth} from "./context/AuthContext";
import LoginPage from "./pages/login/LoginPage";
import Main from "./pages/main/Main";
import AdminDashboard from "./pages/admin/AdminDashboard";
import PrivateRoute from "./layouts/PrivateRoute";
import UserListPage from "./pages/user/UserListPage";
import NotFound from "./pages/error/NotFound";
import UserInfo from "./pages/user/UserInfo";
import UserAddPage from "./pages/user/UserAddPage";
import UpdateUserInfo from "./pages/user/UpdateUserInfo";
import AdminLayout from "./layouts/AdminLayout";
import ProtectedRoute from "./layouts/ProtectedRoute";


function App() {
  return (
      <AuthContextProvider>
        <Routes>
          <Route element={<Layouts/>}>
            <Route path="/" element={<ProtectedRoute><Main/></ProtectedRoute>}>
              {/*<Route index element={<Main/>}/>*/}
            </Route>
            <Route path="/admin" element={<ProtectedRoute isAdmin={true}><AdminLayout/></ProtectedRoute>}>
              <Route index element={<AdminDashboard/>}/>
              <Route path="users">
                <Route index element={<UserListPage/>}/>
                <Route path="add" element={<UserAddPage/>}/>
                <Route path=":userId" element={<UserInfo/>}/>
                <Route path=":userId/edit" element={<UpdateUserInfo/>}/>
              </Route>
            </Route>
          </Route>
          <Route path="/login" element={<LoginPage/>}/>
          <Route path="*" element={<NotFound/>}/>
        </Routes>
      </AuthContextProvider>
  );
}

export default App;
