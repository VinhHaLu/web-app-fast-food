import "./datatable.scss";
import { Link } from "react-router-dom";

import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';

import { db } from "../../firebase";
import { set, onValue, ref as ref1 ,remove, update } from "firebase/database";
import { useState, useEffect } from "react";


const Datatable = () => {
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
  
  return (
    
    <div className="datatable">
      <div className="datatableTitle">
        THÊM SẢN PHẨM
        <Link to="/products/new" className="link">
          THÊM MỚI
        </Link>
      </div>
      <TableHead >
          <TableRow>
            <TableCell className='tableCell id'>Image</TableCell>
            <TableCell className='tableCell product'>Tên sản phẩm</TableCell>
            <TableCell className='tableCell price'>Giá</TableCell>
            <TableCell className='tableCell discount'>Loại</TableCell>
            <TableCell className='tableCell sold'>Mô tả</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
        {Products.map((Product) => (
        <>
          <TableRow >
            <TableCell className='tableCell id'>
            <div>
              <img
                  src={Product.image_product}
                  alt="uploaded image"
                  className=" object-cover"
                />
            </div>
            </TableCell>
            <TableCell className='tableCell product'> {Product.name_product}</TableCell>
            <TableCell className='tableCell price'>{Product.price_product}</TableCell>
            <TableCell className='tableCell discount'>{Product.category_product}</TableCell>
            <TableCell className='tableCell sold'>{Product.desc_product}</TableCell>
         
         
            <TableCell className='tableCell sold'>   <span className="delete__btn" onClick={ () => {
                             remove(ref1(db, `/products/${Product.id_product}`));}}>
                          Xóa
                        </span></TableCell>  
                
         
          </TableRow>
      </>
        ))}
        </TableBody>
    </div>
  );
};

export default Datatable;
