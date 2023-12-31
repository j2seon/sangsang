import './App.css';
import './components/common/default.css';
import './components/common/input/Input'
import Layouts from "./layouts/Layouts";
import {AuthContextProvider} from "./context/AuthContext";
import LoginPage from "./pages/login/LoginPage";
import Main from "./pages/main/Main";
import AdminDashboard from "./pages/admin/AdminDashboard";
import PrivateRoute from "./routes/PrivateRoute";
import UserListPage from "./pages/user/UserListPage";
import NotFound from "./pages/error/NotFound";
import UserInfo from "./pages/user/UserInfo";
import UserAddPage from "./pages/user/UserAddPage";
import AdminLayout from "./layouts/AdminLayout";
import MemberManageLayout from "./layouts/MemberManageLayout";
import {Router} from "./routes/Router";
import {Route, Routes} from "react-router-dom";
import ProtectedRoute from "./routes/ProtectedRoute";
import UserLayout from "./layouts/UserLayout";


function App() {
  return (
      <AuthContextProvider>
        <Routes>
          <Route element={<Layouts/>}>
            <Route path="/" element={<PrivateRoute><UserLayout/></PrivateRoute>}>
              <Route index element={<Main/>}/>
            </Route>
            <Route path="/admin" element={<ProtectedRoute isAdmin={true}><AdminLayout/></ProtectedRoute>}>
              <Route index element={<AdminDashboard/>} />
              <Route path="users" element={<MemberManageLayout/>}>
                <Route index element={<UserListPage/>}/>
                <Route path="add" element={<UserAddPage/>}/>
                <Route path=":userId" element={<UserInfo/>}/>
              </Route>
            </Route>
          </Route>
          <Route path="/login" element={<LoginPage/>}/>
          <Route path="*" element={<NotFound/>}/>
        </Routes>
        {/*<Router/>*/}
      </AuthContextProvider>
  );
}

export default App;
