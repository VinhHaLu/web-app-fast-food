import React, { useState, useEffect } from "react";
import Helmet from "../components/Helmet/Helmet";
import CommonSection from "../components/UI/common-section/CommonSection";

import { Container, Row, Col } from "reactstrap";

import products from "../assets/fake-data/products";
import ProductCard from "../components/UI/product-card/ProductCard";
import ReactPaginate from "react-paginate";

import "../styles/all-foods.css";
import "../styles/pagination.css";

import { db } from "../firebase";
import { onValue, ref as ref1 } from "firebase/database";
import { getAuth, signOut} from "firebase/auth";
const AllFoods = () => {
  const [searchTerm, setSearchTerm] = useState("");

  const [pageNumber, setPageNumber] = useState(0);

  const [Products, setProduct] = useState([]);
  //read

  const auth = getAuth();
  const user = auth.currentUser;
  let uid;
  if (user !== null){
   uid = user.uid
  }
  


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


  const searchedProduct = Products.filter((item) => {
    if (searchTerm.value === "") {
      return item;
    }
    if (item.name_product.toLowerCase().includes(searchTerm.toLowerCase())) {
      return item;
    } else {
      return console.log("not found");
    }
  });
  const productPerPage = 8;
  const visitedPage = pageNumber * productPerPage;
  const displayPage = Products.slice(
    visitedPage,
    visitedPage + productPerPage
  );

  const pageCount = Math.ceil(searchedProduct.length / productPerPage);

  const changePage = ({ selected }) => {
    setPageNumber(selected);
  };
  
  
  return (
    <Helmet title="All-Foods">
      <CommonSection title="Món Ăn" />

      <section>
        <Container>
          <Row>
            <Col lg="6" md="6" sm="6" xs="12">
              <div className="search__widget d-flex align-items-center justify-content-between ">
                <input
                  type="text"
                  placeholder="Nhập tên...."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                />
                <span>
                  <i class="ri-search-line"></i>
                </span>
              </div>
            </Col>
            <Col lg="6" md="6" sm="6" xs="12" className="mb-5">
              <div className="sorting__widget text-end">
                <select className="w-50">
                  <option>Phân loại</option>
                  <option value="ascending">Danh sách món, A-Z</option>
                  <option value="descending">Danh sách món, Z-A</option>
                  {/* <option value="high-price">High Price</option>
                  <option value="low-price">Low Price</option> */}
                </select>
              </div>
            </Col>

            {displayPage.map((item) => (
              <Col lg="3" md="4" sm="6" xs="6" key={item.id_product} className="mb-4">
                <ProductCard item={item} />
              </Col>
            ))}

            <div>
              <ReactPaginate
                pageCount={pageCount}
                onPageChange={changePage}
                previousLabel={"Trước"}
                nextLabel={"Sau"}
                containerClassName=" paginationBttns "
              />
            </div>
          </Row>
        </Container>
      </section>
    </Helmet>
  );
};

export default AllFoods;
