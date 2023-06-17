import React, { useEffect, useState } from "react";
import { set, onValue, ref as ref1 ,remove, update } from "firebase/database";
import { db } from "../firebase";
import { getAuth, signOut} from "firebase/auth";

import CommonSection from "../components/UI/common-section/CommonSection";
import Helmet from "../components/Helmet/Helmet";
import "../styles/cart-page.css";
import { useSelector, useDispatch } from "react-redux";
import { Container, Row, Col } from "reactstrap";
import { cartActions } from "../store/shopping-cart/cartSlice";
import { Link } from "react-router-dom";


const Cart = (item) => {
  // const cartItems = useSelector((state) => state.cart.cartItems);
  // const totalAmount = useSelector((state) => state.cart.totalAmount);

  const auth = getAuth();
  const user = auth.currentUser;
  let uid;
  if (user !== null){
   uid = user.uid
  }
  

  
  
  const [id_cart, setid_cart] = useState(1);
  const [Carts, setCarts] = useState([]);
  const [totalAmount, settotalAmount] = useState(0);
    //read
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
    

  return (
    <Helmet title="Cart">
      <CommonSection title="Giỏ hàng của bạn" />
      <section>
        <Container>
          <Row>
            <Col lg="12">
              {/* {cartItems.length === 0 ? (
                <h5 className="text-center">Giỏ hàng của bạn đang trống</h5>
              ) : ( */}
                <table className="table table-bordered">
                  
                  <thead>
                  {Carts.map((ca) => (
                    <tr>
                      <th>
                        <div>
                          <img
                              src={ca.img_cart}
                              alt="uploaded image"
                              className=" object-cover"
                            />
                        </div>
                      </th>
                      <th className="product">{ca.title_cart}</th>
                      <th className="price">${ca.tv_price}</th>
                      <th className="cate">{ca.number_cart}</th>
                      <th className="delete" >
                        <span className="delete__btn" onClick={ () => {
                             remove(ref1(db, `/Carts/${uid}/${ca.id_cart}`));}}>
                          <i class="ri-close-line"></i>
                        </span>
                      </th>
                    </tr>
                  ))}

                  </thead>
                 
                  {/* <tbody>
                    {cartItems.map((item) => (
                      <Tr item={item} key={item.id} />
                    ))}
                  </tbody> */}
                </table>
              {/* )} */}

              <div className="mt-4">
                <h6>
                  Giá: $
                  <span className="cart__subtotal">{totalAmount}</span>
                </h6>
                <p>Phí vận chuyển sẽ được tính khi thanh toán</p>
                <div className="cart__page-btn">
                  <button className="addTOCart__btn me-4">
                    <Link to="/foods">Tiếp tục mua</Link>
                  </button>
                  <button className="addTOCart__btn">
                    <Link to="/checkout">Tiến hành thanh toán</Link>
                  </button>
                </div>
              </div>
            </Col>
          </Row>
        </Container>
      </section>
    </Helmet>
  );
};


export default Cart;
