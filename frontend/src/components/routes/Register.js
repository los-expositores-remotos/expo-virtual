import { data } from 'jquery';
import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import M from 'materialize-css'
import '../../styles/Register.css'

document.addEventListener('DOMContentLoaded', function() {
    var elems = document.querySelectorAll('.autocomplete');
    M.Autocomplete.init(elems, {});
  });

const Register = () => {
    const [nombre, setNombre] = useState("")
    const [dni, setDni] = useState("")
    const [apellido, setApellido] = useState("")
    const history = useHistory();

    const handleNombre = (e) => {
        setNombre(e.target.value)
    }

    const handleDni = (e) => {
        setDni(e.target.value)
    }

    const handleApellido = (e) => {
        setApellido(e.target.value)
    }

    const registrarse = () => {
        fetch("http://localhost:7000/register", {
            method: "POST",
            headers: {
              "Content-type": "application/json",
            },
            body: JSON.stringify({
              "nombre": nombre,
              "apellido": apellido,
              "dni": dni
            })
          })
          .then((res) => res.json())
          .then((data) => {
            if (data.error) {
              M.toast({ html: data.error, classes: "#c62828 red darken-3" });
            } else {
              M.toast({
                html: "Registro exitoso",
                classes: "#388e3c green darken-2",
              });
              history.push("/login");
            }
          })
          .catch((err) => {
            console.log(err);
          });
        
      };
    
    return (
        <div className="container">
            <div className="user-register">
                <div className="Register-form">
                    <div>
                        <input type="text" value={nombre} name='nombre' placeholder='Nombre' onChange={handleNombre} />
                    </div>
                    <div>
                        <input type="text" value={apellido} name='apellido' placeholder='Apellido' onChange={handleApellido} />
                    </div>
                    <div>
                        <input type="dni" value={dni} name="dni" placeholder="Dni" onChange={handleDni} />
                    </div>
 
                    <div className="boton-register">
                        <button type='button' className= "btn-small pink accent-1" onClick={registrarse}> Register </button>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default Register;