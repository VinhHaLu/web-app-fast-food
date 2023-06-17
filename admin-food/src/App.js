import Home from "./pages/home/Home";
import Login from "./pages/login/Login";
import List from "./pages/list/List";
import Single from "./pages/single/Single";
import New from "./pages/new/New";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { productInputs, userInputs } from "./formSource";
import "./style/dark.scss";
import { useContext, useEffect, useState } from "react";
import { DarkModeContext } from "./context/darkModeContext";
import ListOder from "./pages/list/ListOder";

import { auth } from "./firebase";


function App() {
  const { darkMode } = useContext(DarkModeContext);
  const [userName, setUserName] = useState("");

  useEffect(() => {
    auth.onAuthStateChanged((user) => {
      if (user) {
        setUserName(user.displayName);
      } else setUserName("");
    });
  }, []);
  return (
    <div className={darkMode ? "app dark" : "app"}>
      <BrowserRouter>
        <Routes>
          <Route path="/">
            <Route index element={<Login />} />
            <Route path="home" element={<Home name={userName} />}></Route>
            <Route path="users">
              <Route index element={<List />} />
              <Route
                path="new"
                element={<New />}
              />
            </Route>
            <Route path="products">
              <Route index element={<List />} />
              
              <Route
                path="new"
                element={<New/>}
              />
            </Route>
            <Route path="oder" >
            <Route index element={<ListOder/>} />
              
        
              <Route path="sing/:id_inf" element={<Single />} />
            </Route>
          </Route>
          <Route path="home" element={<Home />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
