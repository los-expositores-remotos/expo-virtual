import React from 'react';

const TestForm = () => {

  function options(){
    var list = []
    fetch("https://api.mercadopago.com/v1/identification_types?public_key=TEST-147fd98d-a235-429b-aa09-a5b157a1fe61", {
    headers: {
      "Content-type": "application/json",
    }
    }).then((res) => {
      if (res.ok) {
        return res.json()
      }
    }).then((response)=>{
      response.map(option => {
          list.push(option)
      })
    })
    return list
  }

  function select(list){
    return(
      <select>
        {list.map(opt => {
          return(<option value={opt.id}>{opt.name}</option>)
        })}
      </select>
    )
  }



  return (
 
    <div>
      {select(options())}
      <select id="Category" form="bannerform" type="text" class="validate">
                    <option>Seleccione una categoría...</option>
                    <option value="HOME">Inicio</option>
                    <option value="SCHEDULE">Calendario de clases</option>
                    <option value="CLASS">Clase en vivo</option>
                    <option value="COURRIER">Correo</option>
                    <option value="PAYMENTMETHODS">Metodos de pago</option>
                </select>
    </div>
  );
  }
export default TestForm;