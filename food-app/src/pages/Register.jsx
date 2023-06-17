import React, { useRef, useState } from "react";
import Helmet from "../components/Helmet/Helmet";
import CommonSection from "../components/UI/common-section/CommonSection";
import { Container, Row, Col } from "reactstrap";
import { Link, useNavigate } from "react-router-dom";
import { createUserWithEmailAndPassword, updateProfile } from "firebase/auth";
import { auth, db} from "../firebase";
import { set, onValue, ref as ref1 ,remove, update } from "firebase/database";
import { getAuth, signOut} from "firebase/auth";

const Register = () => {
 
  
  const navigate = useNavigate();
  const [values, setValues] = useState({
    name: "",
    email: "",
    pass: "",
  });
  const [errorMsg, setErrorMsg] = useState("");
  const [submitButtonDisabled, setSubmitButtonDisabled] = useState(false);

  const handleSubmission = () => {
    if (!values.name || !values.email || !values.pass) {
      setErrorMsg("Fill all fields");
      return;
    }
    setErrorMsg("");

    setSubmitButtonDisabled(true);
    createUserWithEmailAndPassword(auth, values.email, values.pass)
      .then(async (res) => {
        setSubmitButtonDisabled(false);
        const user = res.user;
        await updateProfile(user, {
          displayName: values.name,
        });
        navigate("/");
        let auth = getAuth();
        let usera = auth.currentUser;
     
        set(ref1(db, `/User/${usera.uid}`), {
          email: values.email,
          uid: usera.uid,  
        });

      })
      .catch((err) => {
        setSubmitButtonDisabled(false);
        setErrorMsg(err.message);
      });
  };



  return (
    <Helmet title="Signup">
      <CommonSection title="Signup" />
      <section>
        <Container>
          <Row>
            <Col lg="6" md="6" sm="12" className="m-auto text-center">
              <form className="form mb-5" >
                <div className="form__group">
                  <input
                    type="text"
                    placeholder="Full name"
                    onChange={(event) =>
                      setValues((prev) => ({ ...prev, name: event.target.value }))
                    }
                  />
                </div>
                <div className="form__group">
                  <input
                    type="email"
                    placeholder="Email"
                    onChange={(event) =>
                      setValues((prev) => ({ ...prev, email: event.target.value }))
                    }
                  />
                </div>
                <div className="form__group">
                  <input
                    type="password"
                    placeholder="Password"
                    onChange={(event) =>
                      setValues((prev) => ({ ...prev, pass: event.target.value }))
                    }
                  />
                </div>
                <b >{errorMsg}</b>
                <button type="submit" className="addTOCart__btn" 
                onClick={handleSubmission} disabled={submitButtonDisabled}
                >
                  Sign Up
                </button>
              </form>
              <Link to="/login">Already have an account? Login</Link>
            </Col>
          </Row>
        </Container>
      </section>
    </Helmet>
  );
};

export default Register;
