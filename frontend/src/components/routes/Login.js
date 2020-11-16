import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import M from 'materialize-css'
import '../../styles/Login.css'

document.addEventListener('DOMContentLoaded', function() {
    var elems = document.querySelectorAll('.autocomplete');
    M.Autocomplete.init(elems, {});
  });

const Login = () => {

  const [dni, setdni] = useState("")
  const history = useHistory();

  let myColor = { background: '#0E1717', text: "#FFFFFF" };

  const logearse = () => {
        fetch("http://localhost:7000/login", {
            method: "POST",
            headers: {
              "Content-type": "application/json",
            },
            body: JSON.stringify({
              "dni": dni
            })
          })
          .then(response => console.log(response.statusText))
          .catch(error => console.log(error.statusText));
  /*      .then(response =>{
            if (!response.ok) {console.log(response)};
            })
            .then(response => {
                localStorage.setItem('tokenValido', response.headers.authorization)
                history.push("/")})
            .catch(error => {
                console.log("error : ", "error.message");
                M.toast({ html: error, classes: "#c62828 red darken-3" });
            });*/
        }

  const handleUsername = (e) => {
    setdni(e.target.value);
  }

  return (
    <div className="container">
      <div className="user-login">
{/*     <img id="login_logo" src={logo} alt="expovirtual" /> */}
        <div className="user-into">
          <input id="inputdni" type="text" value={dni} name='dni' placeholder='Dni' onChange={handleUsername} />
        </div>
        <div className="boton-login" >
          <button type='button' className="btn-small pink accent-1" onClick={logearse}>Login</button>
        </div>
        <Link className= "btn-small btn-info" to="/register">Registro</Link>
    </div>
    </div>
  )

}
export default Login;