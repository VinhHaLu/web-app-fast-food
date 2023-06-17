import React, { useRef, useEffect, useState } from "react";
import { MdLogout } from "react-icons/md";
import { Container } from "reactstrap";
import logo from "../../assets/images/res-logo.png";
import { NavLink, Link } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

import { cartUiActions } from "../../store/shopping-cart/cartUiSlice";
import { getAuth, signOut} from "firebase/auth";
import {useNavigate} from "react-router-dom";
import "../../styles/header.css";
import { db } from "../../firebase";
import Dropdown from 'react-bootstrap/Dropdown';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Toast from 'react-bootstrap/Toast';
import { onValue, ref as ref1 } from "firebase/database";
const nav__links = [
  {
    display: "Home",
    path: "/home",
  },
  {
    display: "Foods",
    path: "/foods",
  },
  {
    display: "Cart",
    path: "/cart",
  },
  {
    display: "Contact",
    path: "/contact",
  },
];

const Header = () => {
  const menuRef = useRef(null);
  const headerRef = useRef(null);
  // const totalQuantity = useSelector((state) => state.cart.totalQuantity);
  const [show, setShow] = useState(false);

  


  // const uid=0;
  // if (user !== null){
  //  uid = user.uid
  // }
  
  let auth = getAuth();
  let user = auth.currentUser;

  
  const [Carts, setCarts] = useState([]);
  const [totalQty, settotalQty] = useState(0);

  const dispatch = useDispatch();

  const toggleMenu = () => menuRef.current.classList.toggle("show__menu");

  useEffect(() => {
    // const auth = getAuth();
    // const user = auth.currentUser;
    if (user !== null){
    let uid = user.uid
      onValue(ref1(db,`/Carts/${uid}`), (snapshot) => {
        setCarts([]);
        const data = snapshot.val();
        if (data !== null) {
          var sum = 0;  
          Object.values(data).map((Cart) => {
            setCarts((oldArray) => [...oldArray, Cart]);
            //  const tong = 0;
            //  tong +=  Cart.totalPrice
            sum += parseInt(Cart.number_cart);  
            settotalQty(sum);
    
            // console.log(Cart.totalPrice)
            // console.log(totalAmount)
          });
  
          settotalQty(sum);
  
          console.log(totalQty)
        }
      });
     }else if (user === null){
      settotalQty(0);
     }

  
  }, []);

  

  const toggleCart = () => {
    dispatch(cartUiActions.toggle());


  };
  const navigate = useNavigate();
  const logout = () => {
    signOut(auth).then(() => {
      navigate("/login", {replace:true})
    }).catch((error) => {
   console.log(error)
    });
  };


  // useEffect(() => {
  //   window.addEventListener("scroll", () => {
  //     if (
  //       document.body.scrollTop > 80 ||
  //       document.documentElement.scrollTop > 80
  //     ) {
  //       headerRef.current.classList.add("header__shrink");
  //     } else {
  //       headerRef.current.classList.remove("header__shrink");
  //     }
  //   });

  //   return () => window.removeEventListener("scroll");
  // }, []);

  return (
    <header className="header" ref={headerRef}>
      <Container>
        <div className="nav__wrapper d-flex align-items-center justify-content-between">
          <div className="logo">
            <img src={logo} alt="logo" />
            <h5>FastFood</h5>
          </div>

          {/* ======= menu ======= */}
          <div className="navigation" ref={menuRef} onClick={toggleMenu}>
            <div className="menu d-flex align-items-center gap-5">
              {nav__links.map((item, index) => (
                <NavLink
                  to={item.path}
                  key={index}
                  className={(navClass) =>
                    navClass.isActive ? "active__menu" : ""
                  }
                >
                  {item.display}
                </NavLink>
              ))}
            </div>
          </div>

          {/* ======== nav right icons ========= */}
          <div className="nav__right d-flex align-items-center gap-4">
            <span className="cart__icon" 
            onClick={toggleCart}
            >
              <i class="ri-shopping-basket-line"></i>
              <span className="cart__badge">
                {totalQty}
              </span>
            </span>

            <span className="user">
            
                {user ? (

                  // <span onClick={() => setShow(true)}>
                  //         <MdLogout />
                  //  </span>
                    <span onClick={logout}>
                    <MdLogout />
                   </span>
                 
                              
              
                  ) : (


                    <Link to="/login">
                  <i class="ri-user-line"></i>
                  </Link>


                  )}
            
            
            </span>

            <span className="mobile__menu" onClick={toggleMenu}>
              <i class="ri-menu-line"></i>
            </span>
          </div>
        </div>
 </Container>
              {/* <Row>
                  <Col md={10}>
                  
                   
                  </Col>
                  <Col md={2}>
                  <Toast className="mb-3" onClose={() => setShow(false)} show={show} delay={3000} autohide>
                    <Toast.Header >
                    </Toast.Header>
                    <Toast.Body>
                     <h7 onClick={logout}>
                     Logout
                     </h7>


                    </Toast.Body>
                  </Toast>
                  </Col>
              
                </Row>      */}
    </header>
    
  );
};

export default Header;
