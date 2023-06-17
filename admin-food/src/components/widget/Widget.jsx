import "./widget.scss";
import { useState, useEffect } from "react";
import KeyboardArrowUpIcon from "@mui/icons-material/KeyboardArrowUp";
import PersonOutlinedIcon from "@mui/icons-material/PersonOutlined";
import ShoppingCartOutlinedIcon from "@mui/icons-material/ShoppingCartOutlined";
import MonetizationOnOutlinedIcon from "@mui/icons-material/MonetizationOnOutlined";
import { getAuth} from "firebase/auth";
import { set, onValue, ref as ref1, update } from "firebase/database";
import { db } from "../../firebase";
const Widget = ({ type }) => {
  let data;
 let us =6;
let uss =5;
let usss =243;
// const [us, setus] = useState(0);
  useEffect(() => {
    onValue(ref1(db,`/infor_Oder`), (snapshot) => {
  
      const data = snapshot.val();
      if (data !== null) {

        Object.values(data).map((Cart) => {
         us = us +1
        
        });
        console.log(us)

      }
    });
  }, []);

  useEffect(() => {
    onValue(ref1(db,`/User`), (snapshot) => {
  
      const data = snapshot.val();
      if (data !== null) {
        Object.values(data).map((Cart) => {
         uss = uss +1
        
        });
        console.log(uss)

      }
    });
  }, []);
  //temporary
  const amount = 100;
  const diff = 20;

  switch (type) {
    case "user":
      data = {
        count:us,
        title: "USERS",
        isMoney: false,
        link: "See all users",
        icon: (
          <PersonOutlinedIcon
            className="icon"
            style={{
              color: "crimson",
              backgroundColor: "rgba(255, 0, 0, 0.2)",
            }}
          />
        ),
      };
      break;
    case "order":
      data = {
        count:uss,
        title: "ORDERS",
        isMoney: false,
        link: "View all orders",
        icon: (
          <ShoppingCartOutlinedIcon
            className="icon"
            style={{
              backgroundColor: "rgba(218, 165, 32, 0.2)",
              color: "goldenrod",
            }}
          />
        ),
      };
      break;
    case "earning":
      data = {
        count:usss,
        title: "EARNINGS",
        isMoney: true,
        link: "View net earnings",
        icon: (
          <MonetizationOnOutlinedIcon
            className="icon"
            style={{ backgroundColor: "rgba(0, 128, 0, 0.2)", color: "green" }}
          />
        ),
      };
      break;
    
    default:
      break;
  }

  return (
    <div className="widget">
      <div className="left">
        <span className="title">{data.title}</span>
        <span className="counter">
              {data.count}
        </span>
        <span className="link">{data.link}</span>
      </div>
      <div className="right">
        <div className="percentage positive">
          <KeyboardArrowUpIcon />
          {diff} %
        </div>
        {data.icon}
      </div>
    </div>
  );
};

export default Widget;
