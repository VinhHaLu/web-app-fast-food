import "./new.scss";

import Sidebar from "../../components/sidebar/Sidebar";
import Navbar from "../../components/navbar/Navbar";

import { db } from "../../firebase";
import { uid } from "uid";
import { set, onValue, ref as ref1 ,remove, update } from "firebase/database";
import { useState, useEffect } from "react";


import {
  deleteObject,
  ref,
  getDownloadURL,
  uploadBytesResumable,
} from "firebase/storage";
import { storage } from "../../firebase";

const New = ({ inputs, title }) => {
  const [file, setFile] = useState("");

  const [nameProduct, setProduct] = useState("");
  const [Price, setPrice] = useState("");
  const [Desc, setDesc] = useState("");

  const [imageAsset, setImageAsset] = useState(null);
  const [fields, setFields] = useState(false);
  const [alertStatus, setAlertStatus] = useState("danger");
  const [msg, setMsg] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  const getInitialState = () => {
    const catePro = "Burger";
    return catePro;
  };
  const [catePro, setcatePro] = useState(getInitialState);

  const handleChange = (e) => {
    setcatePro(e.target.value);
  };

    const writeToDatabase = () => {
      if(imageAsset === null || nameProduct === "" || Desc === ""){
        var message = "Vui lòng chọn hình ảnh và điền đầy đủ thông tin sản phẩm";
        alert(message); 
      }else{
        setIsLoading(true);
        const uuid = uid();
        set(ref1(db, `/products/${uuid}`), {
          name_product: nameProduct,
          category_product: catePro,  
          image_product: imageAsset,
          price_product: Price,
          desc_product: Desc,
          id_product: uuid,
        });
    
        setProduct("");
        setPrice("0");
        setDesc("");
        setImageAsset(null);
      }


    };

    const uploadImage = (e) => {
      setIsLoading(true);
      const imageFile = e.target.files[0];
      const storageRef = ref(storage, `Images/${Date.now()}-${imageFile.name}`);
      const uploadTask = uploadBytesResumable(storageRef, imageFile);
  
      uploadTask.on(
        "state_changed",
        (snapshot) => {
          const uploadProgress =
            (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
        },
        (error) => {
          console.log(error);
          setFields(true);
          setMsg("Error while uploading : Try AGain 🙇");
          setAlertStatus("danger");
          setTimeout(() => {
            setFields(false);
            setIsLoading(false);
          }, 4000);
        },
        () => {
          getDownloadURL(uploadTask.snapshot.ref).then((downloadURL) => {
            setImageAsset(downloadURL);
            setIsLoading(false);
            setFields(true);
            setMsg("Image uploaded successfully 😊");
            setAlertStatus("success");
            setTimeout(() => {
              setFields(false);
            }, 4000);
          });
        }
      );
    };
  
    const deleteImage = () => {
      setIsLoading(true);
      const deleteRef = ref(storage, imageAsset);
      deleteObject(deleteRef).then(() => {
        setImageAsset(null);
        setIsLoading(false);
        setFields(true);
        setMsg("Image deleted successfully 😊");
        setAlertStatus("success");
        setTimeout(() => {
          setFields(false);
        }, 4000);
      });
    };
   
  return (
    <div className="new">
      <Sidebar />
      <div className="newContainer">
        <Navbar />
        <div className="top">
          <h1>{title}</h1>
        </div>
        <div className="bottom">
          <div className="left">
        
       {!imageAsset ? (
                <>
                  <label className="w-full h-full flex flex-col items-center justify-center cursor-pointer">
                    <div className="w-full h-full flex flex-col items-center justify-center gap-2">     
                    <img
                      src={
                        file
                          ? URL.createObjectURL(file)
                          : "https://icon-library.com/images/no-image-icon/no-image-icon-0.jpg"
                      }
                      alt=""
                    />     
                      <p className="text-gray-500 hover:text-gray-700">
                        Click here to upload
                      </p>
                    </div>
                    <input
                      type="file"
                      name="uploadimage"
                      accept="image/*"
                      onChange={uploadImage}
                      className="w-0 h-0"
                    />
                  </label>
                </>
              ) : (
                <>
                  <div className="relative h-full">
                  
                    <img
                      src={imageAsset}
                      alt="uploaded image"
                      className="w-full h-full object-cover"
                    />
                    <button
                      type="button"
                      className="w-0 h-0"
                      onClick={deleteImage}
                    >
                    </button>
                  </div>
                </>
              )}
         
          </div>
          <div className="right">
 
       
            <div class="relative ">
              <div className="label">
                <label for="fname">Tên Sản Phẩm</label>
                <label for="lname">Loại</label>
                <label for="lname">Giá ($)</label>
                <label for="lname">Mô Tả</label>
              </div>
              <div className="input">
                <input className="a1" type="text" id="fname"   
                 value={nameProduct}
                 onChange={(e) => setProduct(e.target.value)} name="firstname" placeholder="sản phẩm..."/>

                <select type="text" id="lname" name="lastname" 
                value={catePro} onChange={handleChange}>
                  <option value="Burger">Burger</option>
                  <option value="Pizza"> Pizza </option>
                  <option value="Bread">Bread</option>
                </select>

                <input type="number" min="0" max="100" id="lname" name="lastname"
                value={Price}
                onChange={(e) => setPrice(e.target.value)}   placeholder="Giá tiền.."/>
        
                <textarea type="text" id="lname" name="lastname" 
                value={Desc} 
                onChange={(e) => setDesc(e.target.value)} placeholder="Ghi chú..." />
              </div>

                {/* <select id="country" name="country">
                  <option value="australia">Australia</option>
                  <option value="canada">Canada</option>
                  <option value="usa">USA</option>
                </select> */}
              
                
              </div>
              
            <button onClick={writeToDatabase} >Send</button>
     
          </div>
        </div>
      </div>
    </div>
  );
};

export default New;
