import React, { useContext, useEffect, useState } from 'react'
import {Link} from 'react-router-dom'
import '../styles/ShoppingCart.css'
import ShopContext from './context/shop-context'

export var sendMethodCostTopLevel = null
export var sendMethodNameTopLevel = null


export const precioTotal = (products) => {
  let contador = 0
  products.forEach((cartItem)=>{
    contador = contador + (cartItem.itemPrice * cartItem.quantity)
  })
  return contador
}

export const pesoTotal = (products) => {
  let acumulador = 0
  products.forEach((cartItem)=>{
    acumulador = acumulador + (cartItem.pesoKg * cartItem.quantity)
  })
  return acumulador
}

export const volumenTotal = (products) => {
  let anchoTotal = 0
  let longitudTotal = 0
  let alturaTotal = 0
  products.forEach((cartItem)=>{
    anchoTotal += cartItem.ancho * cartItem.quantity
    longitudTotal += cartItem.longitud * cartItem.quantity
    alturaTotal += cartItem.alto * cartItem.quantity
  })
  return `${longitudTotal}x${anchoTotal}x${alturaTotal}`
}

const ShoppingCart = () => {
  
  /**USO EL CONTEXTO DE SHOPCONTEXT */
  const context = useContext(ShopContext);
  const [codigoPostal, setCodigoPostal] = useState(null)
  const [sendMethodName, setSendMethodName] = useState(null)
  const [sendMethodCost, setSendMethodCost] = useState(null)

  function calcularEnvio(){
    if(codigoPostal >= 1000 && codigoPostal <= 9999){
      console.log("Hola")
      console.log(context.cart)
      fetch(`https://api.mercadolibre.com/sites/MLA/shipping_options?zip_code_from=1875&zip_code_to=${codigoPostal}&dimensions=${volumenTotal(context.cart)},${pesoTotal(context.cart)}`, {
      headers: {
        "Content-type": "application/json",
      }
    }) 
      .then((res)=> {
      if(res.ok){
        return res.json()
      }
    }).then((response)=>{
      console.log(response)
      let option = response.options.find(option => option.shipping_method_id === 503045)
      sendMethodNameTopLevel = option.name
      setSendMethodName(option.name)
      sendMethodCostTopLevel = option.cost
      setSendMethodCost(option.cost)
      console.log(option.name)
      console.log(option.cost)
    })
    .catch((err => {
      console.log(err)
    }))
  }
  }

  function precioTotalConEnvio(){
    return sendMethodCost ? "$ " + (precioTotal(context.cart) + sendMethodCost) : "" 
  }
  

  useEffect(() => {
    //console.log(context);
  }, []);

  return (
    /**
     * 1) AL MAINNAVIGATION LE PASO COMO PROPS EL CART DEL CONTEXTO, LE HAGO UN REDUCE PARA OBTENER LA CANTIDAD DE 
     *    ELEMENTOS EN EL CARRITO
     */
    <React.Fragment>
     
      <main className="cart">
        {
          context.cart.length <= 0 ? 
          <p>No Item in the Cart!</p>
          :
          <div>
          <table>
          <thead>
              <tr>
                  <th>Imagen</th>
                  <th>Nombre</th>
                  <th>Cantidad</th>
                  <th>Precio</th>
              </tr>
          </thead>
          {context.cart.map(cartItem => (
              <tbody>
              <td id="tdCart"><img id="imgCart" alt={cartItem.itemName} src={cartItem.images[0]}/></td>
              <td id="tdCart">{cartItem.itemName}</td>
              <td id="tdCart">
              <div className="input-field col s12">
              <button
                    onClick={context.removeProductFromCart.bind(
                      this,
                      cartItem.id
                    )}
                  >-</button>
                  <input id="inptCartCant" className="validate" value={cartItem.quantity}/>
                  <button onClick={context.addProductToCart.bind(this, cartItem)}>+</button>
              </div>    
              </td>
              <td id="tdCart">$ {cartItem.itemPrice * cartItem.quantity}</td>
              <td id="tdCart"> <button
                    onClick={context.deleteProductFromCart.bind(
                      this,
                      cartItem.id
                    )}
                  >
                  <i class="material-icons">delete</i>
                  </button></td>
          </tbody>
            ))}
         </table>
         <div className="row">
           <div className="col s6">

            <h3>
              <strong>
                  Precio Total (Sin envío): $ {precioTotal(context.cart)}
              </strong>
            </h3>
           </div>
          </div>
          <div className="row">
            <div className="col s6">
                    <input id = "inptCartCant" value={codigoPostal} onChange={(e) => setCodigoPostal(e.target.value)}/>
                    <button onClick={calcularEnvio}>Calcular envío</button>
           </div>
          </div>
          <div className="row">
          <div className="col s6">
          <h4>
              <strong>
                  {sendMethodName}
              </strong>
            </h4>
          </div>
          <div className="col s6">
          <h4>
              <strong>
                  {(sendMethodCost ? "$ " + sendMethodCost : "")}
              </strong>
            </h4>
          </div>
         </div>
         
          <div className="row">
           <h3>
              <strong> 
                   Precio Total: {precioTotalConEnvio()}
              </strong>
            </h3>
           <div className="col s6 offset-s6">
             <Link to="/testform">
                <button>
                      Comprar
                </button>
             </Link>
           </div>
          </div>
         </div>
        }
      </main>
    </React.Fragment>
  );
};

export default ShoppingCart;