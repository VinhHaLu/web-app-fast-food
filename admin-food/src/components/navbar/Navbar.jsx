import "./navbar.scss";
import SearchOutlinedIcon from "@mui/icons-material/SearchOutlined";
import DarkModeOutlinedIcon from "@mui/icons-material/DarkModeOutlined";

import { DarkModeContext } from "../../context/darkModeContext";
import { useContext } from "react";
import { getAuth, signOut} from "firebase/auth";
import {useNavigate} from "react-router-dom";

import { MdLogout } from "react-icons/md";
const Navbar = () => {
  const { dispatch } = useContext(DarkModeContext);
  let auth = getAuth();
        let user = auth.currentUser;

        
const navigate = useNavigate();
const logout = () => {
  signOut(auth).then(() => {
    navigate("/", {replace:true})
  }).catch((error) => {
 console.log(error)
  });
};

  return (
    <div className="navbar">
      <div className="wrapper">
        <div className="search">
          <input type="text" placeholder="Search..." />
          <SearchOutlinedIcon />
        </div>
        <div className="items">
          <div className="item">
            <DarkModeOutlinedIcon
              className="icon"
              onClick={() => dispatch({ type: "TOGGLE" })}
            />
          </div>
          <div onClick={logout} className="item">
          <MdLogout />
           
          </div>
        
        
          <div className="item">
            <img
              src="https://images.pexels.com/photos/941693/pexels-photo-941693.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
              alt=""
              className="avatar"
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Navbar;
