import React from "react";
import { useState, useEffect } from "react";
import "../../../styles/product-card.css";

import { Link } from "react-router-dom";

// import { useDispatch } from "react-redux";
// import { cartActions } from "../../../store/shopping-cart/cartSlice";

import { db } from "../../../firebase";
// import { uid } from "uid";
import { set, onValue, ref as ref1 ,remove, update } from "firebase/database";

import { getAuth} from "firebase/auth";
import {useNavigate} from "react-router-dom";

const ProductCard = (props) => {


  const auth = getAuth();
  const user = auth.currentUser;

  let uid;
  if (user !== null){
   uid = user.uid
  }
  


  const { id_product, name_product, image_product, price_product } = props.item;
  // const quantity = 1;
  const [Carts, setCart] = useState([]);
  const [datacheck, setdatacheck] = useState(1);

  const [toprice, settoprice] = useState(1);
  
  const [quantity, setquantity] = useState(1);

      useEffect(() => {
  
      
        onValue(ref1(db,`/Carts/${uid}`), (snapshot) => {
        const data = snapshot.val();
        setdatacheck(1);
          if (data !== null) {
            setdatacheck(0);
            Object.values(data).map((Cart) => {        
              setCart((oldArray) => [...oldArray, Cart]);
               settoprice( Number(Cart.number_cart) * Number(price_product) + Number(price_product)  )
                // setquantity( Number(Cart.Quantity) +1  )
            });
          }
        });

        
      }, []);

    const addToCart = () => {

      if(datacheck === 1){

        set(ref1(db, `/Carts/${uid}/${id_product}`), {
          title_cart: name_product,
          number_cart: quantity,  
          img_cart: image_product,
          tv_price:  Number(price_product),      
          totalPrice: Number(price_product),
          check:"true",
          id_cart: id_product,
        });
      }else if(datacheck === 0){
        {Carts.map((Cart) => {
          if(Cart.id_cart === id_product){
            update(ref1(db, `/Carts/${uid}/${id_product}`), {
              number_cart: Number(Cart.number_cart) +1, 
              totalPrice:toprice,
               
             });
  
          }else {
  
            set(ref1(db, `/Carts/${uid}/${id_product}`), {
              title_cart: name_product,
              number_cart: quantity,  
              check:"true",
              img_cart: image_product,
              tv_price:  Number(price_product),      
              totalPrice: Number(price_product),
              id_cart: id_product,
            });
          }
  
        })}

      }
      
    };
  const navigate = useNavigate();
  const Login = () => {
    navigate("/login", {replace:true})
  };

  return (
    <div className="product__item">
      <div className="product__img">
        <img src={image_product} alt="product-img" className="w-50" />
      </div>

      <div className="product__content">
        <h5>
          <Link to={`/foodsDetails/${id_product}`}>{name_product}</Link>
        </h5>
        <div className=" d-flex align-items-center justify-content-between ">
          <span className="product__price">${price_product}</span>

          {user ? (

              // <span onClick={() => setShow(true)}>
              //         <MdLogout />
              //  </span>
              <button className="addTOCart__btn" onClick={addToCart}>
                Thêm vào giỏ
              </button>
              ) : (
              <button className="addTOCart__btn" onClick={Login}>
                Thêm vào giỏ
              </button>
  )}
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
