import "./single.scss";
import Sidebar from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";
import Chart from "../../components/chart/Chart";
import List from "../../components/table/Table";
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';

import { useParams } from "react-router-dom";

import React, { useEffect, useState } from 'react'
import { onValue,ref as ref1 } from 'firebase/database';
import { db } from '../../firebase';
import {useNavigate} from "react-router-dom";
const Single = () => {


  const {id_inf} = useParams();
  const [name, setname] = useState();
  const [email, setemail] = useState();
  const [sdt, setsdt] = useState();
  const [diachi, setdiachi] = useState();
  const [trangthai, settrangthai] = useState();
 
  const [Carts, setCarts] = useState([]);
  const [totalAmount, settotalAmount] = useState(0);
  const navigate = useNavigate();

  const [in4, setin4] = useState([]);

    useEffect(() => {
      onValue(ref1(db,`/infor_Oder`), (snapshot) => {
        setin4([]);
        const data = snapshot.val();
        if (data !== null) {
  
          Object.values(data).map((Cart) => {
             setin4((oldArray) => [...oldArray, Cart]);
             if(Cart.id_inf === id_inf){
              setname(Cart.Name)
              setemail(Cart.email)
              setsdt(Cart.phone)
              setdiachi(Cart.diachi)
              settrangthai(Cart.status)
              settotalAmount(Cart.total)
            }
          });
       console.log(in4)
     
        }
      });
    }, []);




   



  useEffect(() => {
    onValue(ref1(db,`/Detailschekout/${id_inf}`), (snapshot) => {
      setCarts([]);
      const data = snapshot.val();
      if (data !== null) {

        Object.values(data).map((Cart) => {
          setCarts((oldArray) => [...oldArray, Cart]);

        });
  
      }
    });
  }, []);

 


  return (
    <div className="single">
      <Sidebar />
      <div className="singleContainer">
        <Navbar />
        <div className="top">
          <div className="left">
            <div className="editButton">Edit</div>
            <h1 className="title">Information</h1>
            <div className="item">
              <img
                src="https://images.pexels.com/photos/733872/pexels-photo-733872.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260"
                alt=""
                className="itemImg"
              />
              <div className="details">
                <h1 className="itemTitle">Name: {name}</h1>
                <div className="detailItem">
                  <span className="itemKey">Email: </span>
                  <span className="itemValue">{email}</span>
                </div>
                <div className="detailItem">
                  <span className="itemKey">Phone:</span>
                  <span className="itemValue">{sdt}</span>
                </div>
                <div className="detailItem">
                  <span className="itemKey">Address:</span>
                  <span className="itemValue">
                  {diachi}
                  </span>
                </div>
                <div className="detailItem">
                  <span className="itemKey">Total:</span>
                  <span className="itemValue">{totalAmount}</span>
                </div>
                <div className="detailItem">
                  <span className="itemKey">Trạng thái:</span>
                  <span className="itemValue">{trangthai}</span>
                </div>
              </div>
            </div>
          </div>
          <div className="right">
          <h1 className="title">Last Transactions</h1>
        <TableHead >
          <TableRow>
            <TableCell className='tableCell id'>Image</TableCell>
            <TableCell className='tableCell product'>Tên sản phẩm</TableCell>
            <TableCell className='tableCell price'>Giá</TableCell>
            <TableCell className='tableCell discount'>Số lượng</TableCell>
          
          </TableRow>
        </TableHead>
        <TableBody>
        {Carts.map((Product) => (
        <>
          <TableRow >
            <TableCell className='tableCell id'>
            <div>
              {/* <img
                  src={Product.img_cart}
                  alt="uploaded image"
                  className=" object-cover"
                /> */}
            </div>
            </TableCell>
            <TableCell className='tableCell product'> {Product.title_cart}</TableCell>
            <TableCell className='tableCell price'>{Product.tv_price}</TableCell>
            <TableCell className='tableCell discount'>{Product.number_cart}</TableCell>
           
          </TableRow>
      </>
        ))}
        </TableBody>
          </div>
        </div>
        <div className="bottom">
       
        </div>
      </div>
    </div>
  );
};

export default Single;
