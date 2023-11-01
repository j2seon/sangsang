import './App.css';
import './components/common/default.css';
import './components/common/input/Input'
import {Route, Routes} from "react-router-dom";
import Layouts from "./layouts/Layouts";
import {AuthContextProvider} from "./context/AuthContext";
import LoginPage from "./pages/login/LoginPage";
import Main from "./pages/main/Main";


function App() {
  return (
      <AuthContextProvider>
          <Routes >
            <Route path="/" element={<Layouts />} >
                <Route path="/dashboard" element={<Main/>}/>

            </Route>
            <Route path="/login" element={<LoginPage/>}/>

          </Routes>
      </AuthContextProvider>
  );
}

export default App;
