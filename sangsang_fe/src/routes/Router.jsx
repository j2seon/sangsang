import {useAuth} from "../context/AuthContext";
import PrivateRoute from "./PrivateRoute";
import Layouts from "../layouts/Layouts";
import Main from "../pages/main/Main";
import ProtectedRoute from "./ProtectedRoute";
import AdminLayout from "../layouts/AdminLayout";
import MemberManageLayout from "../layouts/MemberManageLayout";
import LoginPage from "../pages/login/LoginPage";
import NotFound from "../pages/error/NotFound";
import AdminDashboard from "../pages/admin/AdminDashboard";
import UserListPage from "../pages/user/UserListPage";
import UserAddPage from "../pages/user/UserAddPage";
import UserInfo from "../pages/user/UserInfo";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import {useEffect} from "react";

export const Router = () => {
  const {contextValue: { accessToken, user }} = useAuth();

  console.log(user)
  useEffect(() => {
  }, [user]);

  console.log()

  const routesForAuthenticatedOnly = [
    {
      path: "/",
      element: <PrivateRoute><Layouts/></PrivateRoute>,
      errorElement: <NotFound />,
      children: [
        {
          path: "",
          element: <Main/>,
        },

      ],
    },
  ];

  const routesForAdminOnly = [
    {
      path: "admin",
      element: <ProtectedRoute isAdmin={true}><AdminLayout/></ProtectedRoute>,
      children: [
        {
          path: "admin",
          element: <AdminDashboard />,
        },
        {
          path: "users",
          element: <MemberManageLayout/>,
          children: [
            {
              path: "",
              element: <UserListPage />,
            },
            {
              path: "add",
              element: <UserAddPage />,
            },
            {
              path: ":userId",
              element: <UserInfo />,
            },
          ],
        },
      ]
    }
  ];

  const routesForNotAuthenticatedOnly = [
    // {
    //   path: "/",
    //   element: <div>main Page</div>,
    // },
    {
      path: "login",
      element: <LoginPage/>,
      errorElement: <NotFound />,
    },
  ];

  console.log(user);
  const router = createBrowserRouter([
    ...routesForAdminOnly,
    ...(!user.isAuthenticated ? routesForNotAuthenticatedOnly : []),
    //...(user.auth.includes("ADMIN") ? routesForAdminOnly : routesForAuthenticatedOnly)
  ]);

  return <RouterProvider router={router} />;
}