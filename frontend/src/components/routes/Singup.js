import React, { useState } from "react";
import "../../styles/Singin.css";
import { Link, useHistory } from "react-router-dom";
import M from "materialize-css";
import logo from "../../images/logo.png"
const Singup = () => {
  const history = useHistory();
  const [nombre, setnombre] = useState(null);
  const [apellido, setapellido] = useState(null);
  const [dni, setdni] = useState(null);

 
  const uploadFiedls = () => {

    if (!(nombre && apellido && dni)) {
      M.toast({ html: "Debe ingresar todos los datos", classes: "#c62828 red darken-3" });
    } else {
      fetch("http://localhost:7000/register", {
        method: "POST",
        headers: {
          "Content-type": "application/json",
        },
        body: JSON.stringify({
          nombre,
          apellido,
          dni
        }),
      })
        .then((res) => {
          console.log(res)
          if(! res.ok){
            M.toast({ html: "Datos invalidos o el usuario ya existe", classes: "#c62828 red darken-3" });
          }else{
            M.toast({
              html: "Usuario creado exitosamente",
              classes: "#388e3c green darken-2",
            });
            history.push("/login");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };
  const PostData = () => {
      uploadFiedls();
  };

  return (
    <div className="mycard">
      <div id="fondoTarjetaLogin"  className="card auth-card input-field">
         <img alt='logo' className="logo-login" src={logo}/>

        <input
          type="text"
          placeholder="Intruduzca su nombre"
          id='inputLogin'
          value={nombre}
          onChange={(e) => setnombre(e.target.value)}
        />
        <input
          type="text"
          placeholder="Intruduzca su apellido"
          id='inputLogin'
          value={apellido}
          onChange={(e) => setapellido(e.target.value)}
        />
        <input
          type="number"
          placeholder="Intruduzca su DNI"
          id='inputLogin'
          value={dni}
          onChange={(e) => setdni(e.target.value)}
        />
        <button
         id="botonLogin"
          className="btn waves-effect waves-light #64b5f6 red darken-1"
          onClick={() => PostData()}
        >
          Registrar
        </button>
        <h5 id="H5Register">
          <Link id="linkRegister" to="/login">Ya te encuentras registrado ?</Link>
        </h5>
      </div>
    </div>
  );
};

export default Singup;
