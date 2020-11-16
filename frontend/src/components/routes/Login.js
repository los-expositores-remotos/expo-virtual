import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Notifications,{notify} from 'react-notify-toast';


const Login = ({/*props*/}) => {

  const [dni, setdni] = useState("")//verificar que sea INT
  const [error,setError] = useState(false)
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
        .then(success =>{
                console.log("success", success);
                localStorage.setItem('tokenValido', success.headers.authorization);
                history.push("/");
            })
            .catch(error => {
                console.log("error : ", error.response.data.message);
                const errorUser = error.response.data.message ;
                notify.show(errorUser,"error",5000,myColor);     
            })
        }

  const handleUsername = (e) => {
    setdni(e.target.value);
  }

  return (
    <div className="boxInicio">
        <Notifications/>
      <div className="user-login">
{/*     <img id="login_logo" src={logo} alt="expovirtual" /> */}
        <div className="user-into">
          <input id="inputdni" type="text" value={dni} name='dni' placeholder='User' onChange={handleUsername} />
        </div>
        <div className="boton-login" >
          <button type='button' className= "btn btn-info" onClick={logearse}>Login</button>
        </div>
        <span className="regis"> <Link to="/register">Registrar</Link></span>
        <div className = "error">
           {error &&
                     <label className = "mesajeError">Dni incorrecto o inexistente</label>

           }
        </div>
      </div>
    </div>
  )
        }
export default Login;