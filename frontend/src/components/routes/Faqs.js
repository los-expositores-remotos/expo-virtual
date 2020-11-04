import React from "react";
import "../../styles/Faqs.css";

const Faqs = () => {
  return (
    <div>
      <h1 id= "h1Text"> Preguntas frecuentes </h1>

      <h5 id = "h5Text">¿Qué formas de pago puedo aprovechar para realizar mi compra?</h5>
      <p id = "pText"> Disponemos de los siguientes medios de pago:</p>
      <ul>
        <li>
          IMAGENES
        </li>
      </ul>
      
      <h5 id = "h5Text">¿Cuál es el costo de envío?</h5>
      <p id = "pText">El costo de envío será mostrado en base al total de la compra y ubicación, en el checkout, en el momento previo a la compra.</p>
     
      <h5 id = "h5Text">¿Cómo se realizan los envíos?</h5>
      <p id = "pText">Trabajamos con:</p>
      <img alt="CORREO"/> 
     
      <h5 id = "h5Text">¿Dónde puedo recibir mi pedido?</h5>
      <p id = "pText">Realizamos envíos a todo el país.</p>

      <h5 id = "h5Text">¿Cuánto tarda en llegar el pedido?</h5>
      <p id = "pText2">El tiempo de entrega dependerá del tipo de envío seleccionado. En general la demora se encuentra entre 3 y 7 días hábiles luego de acreditado el pago.</p>

    
    </div>
  );
};

export default Faqs;
