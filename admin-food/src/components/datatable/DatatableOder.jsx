import React, { useEffect, useState } from 'react'
import './DatatableOder.scss'
import { Link } from "react-router-dom";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { onValue,ref as ref1 } from 'firebase/database';
import { db } from '../../firebase';
import {useNavigate} from "react-router-dom";
import Single from '../../pages/single/Single';


const DatatableOder = () => {

  const [Products, setDelivery] = useState([]);
    //read
    const [Carts, setCarts] = useState([]);
    const [totalAmount, settotalAmount] = useState(0);
    const navigate = useNavigate();
    useEffect(() => {
      onValue(ref1(db,`/infor_Oder`), (snapshot) => {
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
 <div className="datatableoder">
  <div className="datatableTitle">
    DANH SÁCH ĐẶT HÀNG
  </div>
  <TableContainer component={Paper} className='table'>
    <Table sx={{ minWidth: 650 }} aria-label="simple table">
      <TableHead>
        <TableRow>
          <TableCell className='tableCell product'>Tên khách hàng</TableCell>
          <TableCell className='tableCell email'>Email</TableCell>
          <TableCell className='tableCell phone'>Số điện thoại</TableCell>
          <TableCell className='tableCell country'>Địa chỉ</TableCell>
          {/* {/* <TableCell className='tableCell sold'>Đã bán</TableCell> */}
          <TableCell className='tableCell amount'>Tổng tiền</TableCell> 
          <TableCell className='tableCell status'>Trạng thái</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
      {Carts.map((Product) => (
          <TableRow >
            <TableCell className='tableCell product'>{Product.Name}</TableCell>
            <TableCell className='tableCell email'>{Product.email}</TableCell>
            <TableCell className='tableCell phone'>{Product.phone}</TableCell>
            <TableCell className='tableCell phone'>{Product.diachi}</TableCell>
            <TableCell className='tableCell country'>{Product.total}</TableCell>
            {/* <TableCell className='tableCell sold'>{Product.City}</TableCell>
            <TableCell className='tableCell amount'>{Product.PostalCode}</TableCell> */}
            <TableCell className='tableCell status'>
              <span className='on-sale'>{Product.status}</span>
            </TableCell>
            <TableCell className='tableCell status'>
            <Link to={`/oder/sing/${Product.id_inf}`}  className="link"><span className='on-sale'>Xem chi tiết</span></Link>
            </TableCell>
          </TableRow>
       ))}
      </TableBody>
    </Table>
  </TableContainer >
 </div>
  )
}

export default DatatableOder