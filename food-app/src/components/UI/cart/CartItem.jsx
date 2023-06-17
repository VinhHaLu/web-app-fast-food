import React from "react";
import { useState, useEffect } from "react";
import { ListGroupItem } from "reactstrap";

import "../../../styles/cart-item.css";

import { useDispatch } from "react-redux";
import { cartActions } from "../../../store/shopping-cart/cartSlice";

import { db } from "../../../firebase";
import { getAuth, signOut} from "firebase/auth";
// import { uid } from "uid";
import { set, onValue, ref as ref1 ,remove, update } from "firebase/database";
const CartItem = ({ item }) => {
  const { id_cart, title_cart, tv_price, img_cart, number_cart, totalPrice } = item;

  const dispatch = useDispatch();
  const [Carts, setCart] = useState([]);
  const [toprice, settoprice] = useState(1);
  const auth = getAuth();
  const user = auth.currentUser;
  let uid;
  if (user !== null){
   uid = user.uid
  }
  
  useEffect(() => {
    onValue(ref1(db,`/Carts/${uid}`), (snapshot) => {
    const data = snapshot.val();
      if (data !== null) {
        Object.values(data).map((Cart) => {        
          setCart((oldArray) => [...oldArray, Cart]);
           settoprice( Number(Cart.number_cart) * Number(tv_price) + Number(tv_price)  )
            // setquantity( Number(Cart.Quantity) +1  )
        });
      }
    });
  }, []);
  const incrementItem = () => {
    // dispatch(
    //   cartActions.addItem({
    //     id,
    //     NameProduct,
    //     ImageURL,
    //     Price,
    //   })
    // );
     Carts.map((Cart) => {
        update(ref1(db, `/Carts/${uid}/${id_cart}`), {
          number_cart: Number(Cart.number_cart) +1, 
          totalPrice: toprice,
           
         });

    })
  };

  const decreaseItem = () => {
    Carts.map((Cart) => {
      update(ref1(db, `/Carts/${uid}/${id_cart}`), {
        number_cart: Number(Cart.number_cart) -1, 
        totalPrice: toprice,
         
       });

  })
  };

  const deleteItem = () => {
    remove(ref1(db, `/Carts/${uid}/${id_cart}`));
  };

  return (
    <ListGroupItem className="border-0 cart__item">
      <div className="cart__item-info d-flex gap-2">
        <img src={img_cart} alt="product-img" />

        <div className="cart__product-info w-100 d-flex align-items-center gap-4 justify-content-between">
          <div>
            <h6 className="cart__product-title">{title_cart}</h6>
            <p className=" d-flex align-items-center gap-5 cart__product-price">
              {number_cart}x <span>${number_cart*tv_price}</span>
            </p>
            <div className=" d-flex align-items-center justify-content-between increase__decrease-btn">
              <span className="increase__btn" onClick={incrementItem}>
                <i class="ri-add-line"></i>
              </span>
              <span className="quantity">{number_cart}</span>
              <span className="decrease__btn" onClick={decreaseItem}>
                <i class="ri-subtract-line"></i>
              </span>
            </div>
          </div>

          <span className="delete__btn" onClick={deleteItem}>
            <i class="ri-close-line"></i>
          </span>
        </div>
      </div>
    </ListGroupItem>
  );
};

export default CartItem;
