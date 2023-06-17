import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { Container, Row, Col } from "reactstrap";
import {useNavigate} from "react-router-dom";
import CommonSection from "../components/UI/common-section/CommonSection";
import Helmet from "../components/Helmet/Helmet";

import "../styles/checkout.css";
import { db } from "../firebase";

import { getAuth, signOut} from "firebase/auth";
import { set, onValue, ref as ref1 ,remove, update } from "firebase/database";
const Checkout = () => {
  const [enterName, setEnterName] = useState("");
  const [enterEmail, setEnterEmail] = useState("");
  const [enterNumber, setEnterNumber] = useState("");
  const [enterCountry, setEnterCountry] = useState("");
  //const [enterCity, setEnterCity] = useState("");
  //const [postalCode, setPostalCode] = useState("");

  const shippingInfo = [];
  const cartTotalAmount = useSelector((state) => state.cart.totalAmount);
  const shippingCost = 30;

  // const totalAmount = cartTotalAmount + Number(shippingCost);

  // const submitHandler = (e) => {
  //   e.preventDefault();
  //   const userShippingAddress = {
  //     name: enterName,
  //     email: enterEmail,
  //     phone: enterNumber,
  //     country: enterCountry,
  //     city: enterCity,
  //     postalCode: postalCode,


  //   };

  //   shippingInfo.push(userShippingAddress);
  //   console.log(shippingInfo);
  // };

  const auth = getAuth();
  const user = auth.currentUser;
  let uid;
  if (user !== null){
   uid = user.uid
  }
  
    const [Carts, setCarts] = useState([]);
    const [totalAmount, settotalAmount] = useState(0);
    const navigate = useNavigate();
    useEffect(() => {
      onValue(ref1(db,`/Carts/${uid}`), (snapshot) => {
        setCarts([]);
        const data = snapshot.val();
        if (data !== null) {
          var sum = 0;  
          Object.values(data).map((Cart) => {
            setCarts((oldArray) => [...oldArray, Cart]);
            sum += parseInt(Cart.tv_price);  
            settotalAmount(sum);
  
          });

          settotalAmount(sum);
          console.log(Carts)
        }
      });
    }, []);

    const writeToDatabase = () => {
      if(enterName === null || enterEmail === "" || enterCountry === ""){
        var message = "Vui lòng điền đầy đủ thông tin vận chuyển";
        alert(message); 
      }else{

    
        const ran = Math.floor(Math.random() * (1000000 - 1)) + 1;
        const iui = Math.floor(Math.random() * (1000000 - 1)) + 1;
    
        
        set(ref1(db, `/infor_Oder/${ran}`), {
          id_user: uid,      
          id_inf: ran,
          status:"Mới",
          total:totalAmount,
          Name: enterName,
          email: enterEmail,
          phone: enterNumber,
          diachi:enterCountry ,
          //City:enterCity,
          //PostalCode:postalCode,
        });


   
        Carts.map((Cart) => {
          set(ref1(db, `/Detailschekout/${ran}/${iui}`), {
  
            title_cart: Cart.title_cart,
            number_cart: Cart.number_cart,  
            img_cart: Cart.img_cart,
            tv_price: Cart.tv_price,      
            id_cart: Cart.id_cart,
            check: "true",
        });

      })
    
        setEnterName("");
        setEnterEmail("");
        setEnterNumber(0);
        setEnterCountry("");
        //setEnterCity("");
        //setPostalCode(0);
        
        navigate("/CheckoutSuccess", {replace:true})
        
      }

    };

    
  



  return (
    <Helmet title="Checkout">
      <CommonSection title="Thanh toán" />
      <section>
        <Container>
          <Row>
            <Col lg="8" md="6">
              <h6 className="mb-4">Thông tin vận chuyển</h6>

                <div className="form__group">
                  <input
                    type="text"
                    placeholder="Nhập tên ..."
                    required
                    onChange={(e) => setEnterName(e.target.value)}
                  />
                </div>

                <div className="form__group">
                  <input
                    type="email"
                    placeholder="Nhập email..."
                    required
                    onChange={(e) => setEnterEmail(e.target.value)}
                  />
                </div>
                <div className="form__group">
                  <input
                    type="number"
                    placeholder="Nhập số điện thoại..."
                    required
                    onChange={(e) => setEnterNumber(e.target.value)}
                  />
                </div>
                <div className="form__group">
                  <input
                    type="text"
                    placeholder="Nhập Địa chỉ"
                    required
                    onChange={(e) => setEnterCountry(e.target.value)}
                  />
                </div>
                {/* <div className="form__group">
                  <input
                    type="text"
                    placeholder="City"
                    required
                    onChange={(e) => setEnterCity(e.target.value)}
                  />
                </div>
                <div className="form__group">
                  <input
                    type="number"
                    placeholder="Postal code"
                    required
                    onChange={(e) => setPostalCode(e.target.value)}
                  />
                </div> */}
                <button  onClick={writeToDatabase} className="addTOCart__btn">
                  Thanh toán
                </button>
  
            </Col>

            <Col lg="4" md="6">
              <div className="checkout__bill">
                <h6 className="d-flex align-items-center justify-content-between mb-3">
                  Giá: <span>${totalAmount}</span>
                </h6>
                <h6 className="d-flex align-items-center justify-content-between mb-3">
                  Vận chuyển: <span>${shippingCost}</span>
                </h6>
                <div className="checkout__total">
                  <h5 className="d-flex align-items-center justify-content-between">
                    Tổng: <span>${totalAmount + shippingCost}</span>
                  </h5>
                </div>
              </div>
            </Col>
          </Row>
        </Container>
      </section>
    </Helmet>
  );
};

export default Checkout;
