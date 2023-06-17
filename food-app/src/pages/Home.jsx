import React, { useState, useEffect } from "react";

import Helmet from "../components/Helmet/Helmet.js";
import { Container, Row, Col, ListGroup, ListGroupItem } from "reactstrap";

import heroImg from "../assets/images/hero.png";
import "../styles/hero-section.css";

import { Link } from "react-router-dom";

import Category from "../components/UI/category/Category.jsx";

import "../styles/home.css";

import featureImg01 from "../assets/images/service-01.png";
import featureImg02 from "../assets/images/service-02.png";
import featureImg03 from "../assets/images/service-03.png";

import products from "../assets/fake-data/products.js";

import foodCategoryImg01 from "../assets/images/hamburger.png";
import foodCategoryImg02 from "../assets/images/pizza.png";
import foodCategoryImg03 from "../assets/images/bread.png";

import ProductCard from "../components/UI/product-card/ProductCard.jsx";

import whyImg from "../assets/images/location.png";

import networkImg from "../assets/images/network.png";

import TestimonialSlider from "../components/UI/slider/TestimonialSlider.jsx";

import { db } from "../firebase";
import { onValue, ref as ref1 } from "firebase/database";


const featureData = [
  {
    title: "Giao hàng nhanh",
    imgUrl: featureImg01,
    desc: "Nhân viên phục vụ nhanh nhẹn sẽ không để bạn phải chờ lâu.",
  },

  {
    title: "Chế biến món ăn",
    imgUrl: featureImg02,
    desc: "Nhân viên phục vụ nhanh nhẹn sẽ không để bạn phải chờ lâu.",
  },
  {
    title: "Phục vụ tận tình",
    imgUrl: featureImg03,
    desc: "Nhân viên phục vụ nhanh nhẹn sẽ không để bạn phải chờ lâu.",
  },
];

const Home = () => {
  

  const [category, setCategory] = useState("ALL");

  const [allProducts, setAllProducts] = useState(products);

  const [hotPizza, setHotPizza] = useState([]);

  const [Products, setProduct] = useState([]);
  //read
  useEffect(() => {
    onValue(ref1(db,`/products`), (snapshot) => {
      setProduct([]);
      const data = snapshot.val();
      if (data !== null) {
        Object.values(data).map((Product) => {
          setProduct((oldArray) => [...oldArray, Product]);
        });
      }
    });
  }, []);


  useEffect(() => {
    const filteredPizza = products.filter((item) => item.category_product === "Pizza");
    const slicePizza = filteredPizza.slice(0, 4);
    setHotPizza(slicePizza);
  }, []);

  useEffect(() => {
    if (category === "ALL") {
      onValue(ref1(db,`/products`), (snapshot) => {
        setProduct([]);
        const data = snapshot.val();
        if (data !== null) {
          Object.values(data).map((Product) => {
            setProduct((oldArray) => [...oldArray, Product]);
          });
        }
      });
    }

    if (category === "BURGER") {
      const filteredProducts = products.filter(
        // (item) => item.category === "Burger",
        onValue(ref1(db,`/products`), (snapshot) => {
          setProduct([]);
          const data = snapshot.val();
          if (data !== null) {
            Object.values(data).map((Product) => {
              if(Product.category_product  === "Burger"){
                setProduct((oldArray) => [...oldArray, Product]);
              }
            
            });
          }
        })

        
      );

      setAllProducts(filteredProducts);
    }

    if (category === "PIZZA") {
      const filteredProducts = products.filter(
      
        onValue(ref1(db,`/products`), (snapshot) => {
          setProduct([]);
          const data = snapshot.val();
          if (data !== null) {
            Object.values(data).map((Product) => {
              if(Product.category_product  === "Pizza"){
                setProduct((oldArray) => [...oldArray, Product]);
              }
            
            });
          }
        })
      );

      setAllProducts(filteredProducts);
    }

    if (category === "BREAD") {
      const filteredProducts = products.filter(
        onValue(ref1(db,`/products`), (snapshot) => {
          setProduct([]);
          const data = snapshot.val();
          if (data !== null) {
            Object.values(data).map((Product) => {
              if(Product.category_product  === "Bread"){
                setProduct((oldArray) => [...oldArray, Product]);
              }
            
            });
          }
        })
      );

      setAllProducts(filteredProducts);
    }
  }, [category]);

  return (
    <>
    {/* <Header/> */}
    <Helmet title="Home">
      <section>
        <Container>
          <Row>
            <Col lg="6" md="6">
              <div className="hero__content  ">
                <h5 className="mb-3">Giao hàng tận nơi</h5>
                <h1 className="mb-4 hero__title">
                  <span>ĐÓI BỤNG?</span>Đã có 
                  <span> FastFood Lo</span>
                </h1>

                <p>
                Nếu bạn đói thì đã có FastFood lo. Hãy Đặt hàng để nhân được nhiều ưu đãi.
                </p>

                <div className="hero__btns d-flex align-items-center gap-5 mt-4">
                  <button className="order__btn d-flex align-items-center justify-content-between">
                    Đặt hàng ngay <i class="ri-arrow-right-s-line"></i>
                  </button>

                  <button className="all__foods-btn">
                    <Link to="/foods">Xem thực đơn</Link>
                  </button>
                </div>

                <div className=" hero__service  d-flex align-items-center gap-5 mt-5 ">
                  <p className=" d-flex align-items-center gap-2 ">
                    <span className="shipping__icon">
                      <i class="ri-car-line"></i>
                    </span>{" "}
                    Vận chuyển thần tốc
                  </p>

                  <p className=" d-flex align-items-center gap-2 ">
                    <span className="shipping__icon">
                      <i class="ri-shield-check-line"></i>
                    </span>{" "}
                    100% Thanh toán an toàn
                  </p>
                </div>
              </div>
            </Col>

            <Col lg="6" md="6">
              <div className="hero__img">
                <img src={heroImg} alt="hero-img" className="w-100" />
              </div>
            </Col>
          </Row>
        </Container>
      </section>

      <section className="pt-0">
        <Category />
      </section>

      <section>
        <Container>
          <Row>
            <Col lg="12" className="text-center">
              <h5 className="feature__subtitle mb-4">Dịch vụ FastFood</h5>
              <h2 className="feature__title">Chỉ cần ngồi tại nhà</h2>
              <h2 className="feature__title">
                Chúng tôi sẽ <span>Chăm sóc bạn</span>
              </h2>
              <p className="mb-1 mt-4 feature__text">
                FastFood - Quán ăn Online ship đồ về tận nơi bạn ở.
              </p>
              <p className="feature__text">
              Dành cho nhũng tín đồ đam mê ăn uống nhưng không muốn đi ra ngoài vì ngại đường xa hoặc không tìm được quán ưng ý.{" "}
              </p>
            </Col>

            {featureData.map((item, index) => (
              <Col lg="4" md="6" sm="6" key={index} className="mt-5">
                <div className="feature__item text-center px-5 py-3">
                  <img
                    src={item.imgUrl}
                    alt="feature-img"
                    className="w-25 mb-3"
                  />
                  <h5 className=" fw-bold mb-3">{item.title}</h5>
                  <p>{item.desc}</p>
                </div>
              </Col>
            ))}
          </Row>
        </Container>
      </section>

      <section>
        <Container>
          <Row>
            <Col lg="12" className="text-center">
              <h2>Thực đơn phổ biến</h2>
            </Col>

            <Col lg="12">
              <div className="food__category d-flex align-items-center justify-content-center gap-4">
                <button
                  className={`all__btn  ${
                    category === "ALL" ? "foodBtnActive" : ""
                  } `}
                  onClick={() => setCategory("ALL")}
                >
                  All
                </button>
                <button
                  className={`d-flex align-items-center gap-2 ${
                    category === "BURGER" ? "foodBtnActive" : ""
                  } `}
                  onClick={() => setCategory("BURGER")}
                >
                  <img src={foodCategoryImg01} alt="" />
                  Burger
                </button>

                <button
                  className={`d-flex align-items-center gap-2 ${
                    category === "PIZZA" ? "foodBtnActive" : ""
                  } `}
                  onClick={() => setCategory("PIZZA")}
                >
                  <img src={foodCategoryImg02} alt="" />
                  Pizza
                </button>

                <button
                  className={`d-flex align-items-center gap-2 ${
                    category === "BREAD" ? "foodBtnActive" : ""
                  } `}
                  onClick={() => setCategory("BREAD")}
                >
                  <img src={foodCategoryImg03} alt="" />
                  Bread
                </button>
              </div>
            </Col>

            {Products.map((Product) => (
              <Col lg="3" md="4" sm="6" xs="6" key={Product.id} className="mt-5">
                <ProductCard item={Product} />
              </Col>
            ))}

          </Row>
        </Container>
      </section>

      <section className="why__choose-us">
        <Container>
          <Row>
            <Col lg="6" md="6">
              <img src={whyImg} alt="why-tasty-treat" className="w-100" />
            </Col>

            <Col lg="6" md="6">
              <div className="why__tasty-treat">
                <h2 className="tasty__treat-title mb-4">
                  Tại sao lại chọn <span>EnDyVy?</span>
                </h2>
                <p className="tasty__treat-desc">
                Đến với FastFood mọi người sẽ được tận hưởng những hương vị mà bạn chưa được nếm thử. Sẽ khiến cho bạn không thể nào quên được.
                </p>

                <ListGroup className="mt-4">
                  <ListGroupItem className="border-0 ps-0">
                    <p className=" choose__us-title d-flex align-items-center gap-2 ">
                      <i class="ri-checkbox-circle-line"></i> Thực phẩm tươi ngon
                    </p>
                    <p className="choose__us-desc">
                      Luôn luôn đáp úng được những thực phẩm tươi ngon để đem đến cho khách hàng nhiều hương vị thật đặc biệt. 
                    </p>
                  </ListGroupItem>

                  <ListGroupItem className="border-0 ps-0">
                    <p className="choose__us-title d-flex align-items-center gap-2 ">
                      <i class="ri-checkbox-circle-line"></i> Hỗ trợ nhiệt tình
                    </p>
                    <p className="choose__us-desc">
                      Được phục vụ quý khách là niềm vinh hạnh của chúng tôi.
                    </p>
                  </ListGroupItem>

                  <ListGroupItem className="border-0 ps-0">
                    <p className="choose__us-title d-flex align-items-center gap-2 ">
                      <i class="ri-checkbox-circle-line"></i>Đặt hàng mọi lúc{" "}
                    </p>
                    <p className="choose__us-desc">
                      Nếu bạn đói thì đã có EnDyVy lo. Hãy Đặt hàng để nhân được nhiều ưu đãi.
                    </p>
                  </ListGroupItem>
                </ListGroup>
              </div>
            </Col>
          </Row>
        </Container>
      </section>
{/* 
      <section className="pt-0">
        <Container>
          <Row>
            <Col lg="12" className="text-center mb-5 ">
              <h2>Hot Pizza</h2>
            </Col>

            {hotPizza.map((item) => (
              <Col lg="3" md="4" sm="6" xs="6" key={item.id}>
                <ProductCard item={item} />
              </Col>
            ))}
          </Row>
        </Container>
      </section> */}

      <section>
        <Container>
          <Row>
            <Col lg="6" md="6">
              <div className="testimonial ">
                <h5 className="testimonial__subtitle mb-4">Phản hồi</h5>
                <h2 className="testimonial__title mb-4">
                  Phản hồi  <span>Khách hàng</span> Như thế nào
                </h2>
                <p className="testimonial__desc">
                  Đến với FastFood mọi người sẽ được tận hưởng những hương vị mà bạn chưa được nếm thử. Sẽ khiến cho bạn không thể nào quên được.
                </p>

                <TestimonialSlider />
              </div>
            </Col>

            <Col lg="6" md="6">
              <img src={networkImg} alt="testimonial-img" className="w-100" />
            </Col>
          </Row>
        </Container>
      </section>
    </Helmet>
    {/* <Footer/> */}
    </>
  );
};

export default Home;
