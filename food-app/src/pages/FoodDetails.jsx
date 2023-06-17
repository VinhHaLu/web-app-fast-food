import React, { useState, useEffect } from "react";


import Helmet from "../components/Helmet/Helmet";
import CommonSection from "../components/UI/common-section/CommonSection";
import { Container, Row, Col } from "reactstrap";

// import products from "../assets/fake-data/products";
import { useParams } from "react-router-dom";
// import { useDispatch } from "react-redux";
// import { cartActions } from "../store/shopping-cart/cartSlice";

import "../styles/product-details.css";
import { getAuth} from "firebase/auth";
import { set, onValue, ref as ref1, update } from "firebase/database";
import { db } from "../firebase";
import {useNavigate} from "react-router-dom";

const FoodDetails = (props) => {

  const [tab, setTab] = useState("desc");
  const [enteredName, setEnteredName] = useState("");
  const [enteredEmail, setEnteredEmail] = useState("");
  const [reviewMsg, setReviewMsg] = useState("");
  
  const {id_product} = useParams();
  //const { id_product, name_product, image_product, price_product, desc_product }

  const [id_products, setIdproduct] = useState();
  const [name_product, setNameproduct] = useState();
  const [image_product, setImageproduct] = useState();
  const [price_product, setPriceproduct] = useState();
  const [desc_product, setDescproduct] = useState();



  const auth = getAuth();
  const user = auth.currentUser;
  let uid;
  if (user !== null){
   uid = user.uid
  }
  const [Carts, setCart] = useState([]);
  const [toprice, settoprice] = useState(1);
  const [datacheck, setdatacheck] = useState(1);
  const [quantity, setquantity] = useState(1);
  //  console.log(id_product);

  const [Products, setProduct] = useState([]);

  useEffect(() => {
    onValue(ref1(db,`/products`), (snapshot) => {
      setProduct([]);
    const data = snapshot.val();
      if (data !== null) {
        Object.values(data).map((Product) => {
          if(Product.id_product ===id_product){
            setProduct((oldArray) => [...oldArray, Product]);
          }
      
     

        });
      }
    });
  }, []);
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

  const reloaddata = () => {
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
  
  };


 
  const submitHandler = (e) => {
    e.preventDefault();

    console.log(enteredName, enteredEmail, reviewMsg);
  };

  const navigate = useNavigate();
  const Login = () => {
    navigate("/login", {replace:true})
  };

  const addToCart = () => {
    
    Products.map((Product) => {
      setIdproduct(Product.id_product)
     setNameproduct(Product.name_product)
     setImageproduct(Product.image_product)
     setPriceproduct(Product.price_product)

   })


    if(datacheck === 1){

      set(ref1(db, `/Carts/${uid}/${id_products}`), {
        title_cart: name_product,
        number_cart: quantity,  
        img_cart: image_product,
        tv_price:  Number(price_product),      
        totalPrice: Number(price_product),
        check:"true",
        id_cart: id_products,

      });
      reloaddata()
    }else if(datacheck === 0){
      reloaddata()
      {Carts.map((Cart) => {
        if(Cart.id_cart === id_products){
          update(ref1(db, `/Carts/${uid}/${id_products}`), {
            number_cart: Number(Cart.number_cart) +1, 
            totalPrice:toprice,
             
           });
         
        }else {
          set(ref1(db, `/Carts/${uid}/${id_products}`), {
            title_cart: name_product,
            number_cart: quantity,  
            img_cart: image_product,  
            check:"true", 
            tv_price:  Number(price_product),      
            totalPrice: Number(price_product),
            id_cart: id_products,
          });
          reloaddata()
        }

    

      })}

    }
    
  };


  return (
    <Helmet title="Product-details">   
      <CommonSection title="Chi tiết sản phẩm" />
      <section>
        <Container>
          <Row>
            {Products.map((Product) => (
            <>
            <Col lg="6" md="6">
              <div className="product__main-img">
                <img src={Product.image_product} alt="" className="w-100" />
              </div>
            </Col>

            <Col lg="6" md="6">
              <div className="single__product-content">
                <h2 className="product__title mb-3">{Product.name_product}</h2>
                <p className="product__price">
                  {" "}
                  Giá: <span>${Product.price_product}</span>
                </p>
                <p className="category mb-5">
                  Loại: <span>{Product.category_product}</span>
                </p>

             
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
            </Col>

            <Col lg="12">
              <div className="tabs d-flex align-items-center gap-5 py-3">
                <h6
                  
                  className={` ${tab === "desc" ? "tab__active" : ""}`}
                  onClick={() => setTab("desc")}
                >
                  Giới thiệu
                </h6>
                <h6
                  className={` ${tab === "rev" ? "tab__active" : ""}`}
                  onClick={() => setTab("rev")}
                >
                  Đánh giá
                </h6>
              </div>

              {tab === "desc" ? (
                <div className="tab__content">
                  <p>{Product.desc_product}</p>
                </div>
              ) : (
                <div className="tab__form mb-3">
                  <div className="review pt-5">
                    <p className="user__name mb-0">Ngoc Nguyen</p>
                    <p className="user__email">ngocnguyen@gmail.com</p>
                    <p className="feedback__text">great product</p>
                  </div>

                  <div className="review">
                    <p className="user__name mb-0">The hien</p>
                    <p className="user__email">thehien@gmail.com</p>
                    <p className="feedback__text">great product</p>
                  </div>

                  <div className="review">
                    <p className="user__name mb-0">Ngoc Quy</p>
                    <p className="user__email">ngocquy@gmail.com</p>
                    <p className="feedback__text">great product</p>
                  </div>
                  <form className="form" onSubmit={submitHandler}>
                    <div className="form__group">
                      <input
                        type="text"
                        placeholder="Enter your name"
                        onChange={(e) => setEnteredName(e.target.value)}
                        required
                      />
                    </div>

                    <div className="form__group">
                      <input
                        type="text"
                        placeholder="Enter your email"
                        onChange={(e) => setEnteredEmail(e.target.value)}
                        required
                      />
                    </div>

                    <div className="form__group">
                      <textarea
                        rows={5}
                        type="text"
                        placeholder="Write your review"
                        onChange={(e) => setReviewMsg(e.target.value)}
                        required
                      />
                    </div>

                    <button type="submit" className="addTOCart__btn">
                      Submit
                    </button>
                  </form>
                </div>
              )}
            </Col>
            </>
            ))}
            {/* <Col lg="12" className="mb-5 mt-4">
              <h2 className="related__Product-title">You might also like</h2>
            </Col> */}
            
            {/* {relatedProduct.map((item) => (
              <Col lg="3" md="4" sm="6" xs="6" className="mb-4" key={item.id}>
                <ProductCard item={item} />
              </Col>
            ))} */}
          </Row>
        </Container>
      </section>
      
    </Helmet>
  );
};

export default FoodDetails;
