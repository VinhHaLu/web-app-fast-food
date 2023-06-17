import { useState, useEffect } from "react";

import { ListGroup } from "reactstrap";
import { Link } from "react-router-dom";
import CartItem from "./CartItem";

import { useDispatch, useSelector } from "react-redux";
import { cartUiActions } from "../../../store/shopping-cart/cartUiSlice";

import "../../../styles/shopping-cart.css";


import { db } from "../../../firebase";
import { onValue, ref as ref1 } from "firebase/database";
import { getAuth, signOut} from "firebase/auth";
const Carts = () => {
  const dispatch = useDispatch();
  // const cartProducts = useSelector((state) => state.cart.cartItems);
  // const totalAmount = useSelector((state) => state.cart.totalAmount);

  const auth = getAuth();
  const user = auth.currentUser;
  let uid;
  if (user !== null){
   uid = user.uid
  }
  

  const [Carts, setCarts] = useState([]);
  const [totalAmount, settotalAmount] = useState(0);
  useEffect(() => {
    onValue(ref1(db,`/Carts/${uid}`), (snapshot) => {
      setCarts([]);
      const data = snapshot.val();
      if (data !== null) {
        var sum = 0;  
        Object.values(data).map((Cart) => {
          setCarts((oldArray) => [...oldArray, Cart]);
          //  const tong = 0;
          //  tong +=  Cart.totalPrice
          sum += parseInt(Cart.tv_price);  
          settotalAmount(sum);
  
          // console.log(Cart.totalPrice)
          // console.log(totalAmount)
        });

        settotalAmount(sum);

        console.log(totalAmount)
      }
    });
  }, []);

  const toggleCart = () => {
    dispatch(cartUiActions.toggle());
  };
  return (
    <div className="cart__container">
      <ListGroup className="cart">
        <div className="cart__close">
          <span onClick={toggleCart}>
            <i class="ri-close-fill"></i>
          </span>
        </div>

        <div className="cart__item-list">
          {Carts.length === 0 ? (
            <h6 className="text-center mt-5">Không thấy món ăn của bạn</h6>
          ) : (
            Carts.map((item, index) => (
           
              <CartItem item={item} key={index} />     

            ))
          )}


        </div>

        <div className="cart__bottom d-flex align-items-center justify-content-between">
          <h6>
            Giá : <span>${totalAmount}</span>
          </h6>
          <button>

            <Link to="/checkout" onClick={toggleCart}>
              Thanh Toán
            </Link>

            
          </button>
        </div>
      </ListGroup>
    </div>
  );
};

export default Carts;
