import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';

const Register = () => {
    const [nombre, setNombre] = useState("")
    const [dni, setDni] = useState("")
    const [apellido, setApellido] = useState("")
    const [error,setError]  = useState(false)
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
            .then(success => { history.push("/login")})
            .catch(e => {
                console.log(e.response)
                setError(true)
            })
    
        }

    return (
        <div className="boxInicio">
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
                        <button type='button' className= "btn btn-info" onClick={registrarse}> Register </button>
                    </div>
                    <div className = "error">
                        {error &&
                         <label className = "mesajeError">Usuario ya existente</label>
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}
export default Register;