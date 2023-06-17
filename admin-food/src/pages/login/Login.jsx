import "./login.scss"
import { useNavigate } from "react-router-dom";
import { signInWithEmailAndPassword } from "firebase/auth";
import { useState } from "react";
import { auth } from "../../firebase";
import { getAuth } from "firebase/auth";

const Login = () => {
  const navigate = useNavigate();
  const [values, setValues] = useState({
    email: "",
    pass: "",
  });
  const [errorMsg, setErrorMsg] = useState("");
  const [submitButtonDisabled, setSubmitButtonDisabled] = useState(false);

  const handleSubmission = () => {
    if (!values.email || !values.pass) {
      setErrorMsg("Đăng nhập thành công");
      return;
    }
    setErrorMsg("");

    setSubmitButtonDisabled(true);
    signInWithEmailAndPassword(auth, values.email, values.pass)
      .then(async (res) => {
        setSubmitButtonDisabled(false);

        let auth = getAuth();
        let user = auth.currentUser;
        if (user !== null) {
          let uid = user.uid
          if (uid === "csZ7B3DvhJZMXviPYjZqmnLuOJI3") {
            navigate("/home");
          } else {
            alert("Tài Khoản đăng nhập không chính xác!");
          }

        }



      })
      .catch((err) => {
        setSubmitButtonDisabled(false);
        setErrorMsg(err.message);
      });
  };

  return (
    <>
      <div className="box-login">
        <div className="login-page">
          <div className="form">
            <input
              type="text"
              placeholder="Tài khoản"
              name='username'
              onChange={(event) =>
                setValues((prev) => ({ ...prev, email: event.target.value }))
              }
            />
            <input
              type="password"
              placeholder="Mật khẩu"
              name='passwork'
              onChange={(event) =>
                setValues((prev) => ({ ...prev, pass: event.target.value }))
              }
            />
            <b>{errorMsg}</b>
            <button type="submit"
              disabled={submitButtonDisabled} onClick={handleSubmission}>
              Đăng nhập</button>
          </div>

        </div >
      </div >
    </>
  )
}

export default Login