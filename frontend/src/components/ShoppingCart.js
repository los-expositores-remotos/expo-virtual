import React, { useContext, useState, useEffect } from 'react'
import '../styles/ShoppingCart.css'
import ShopContext from './context/shop-context'


const ShoppingCart = () => {
  
  /**USO EL CONTEXTO DE SHOPCONTEXT */
  const context = useContext(ShopContext);

  useEffect(() => {
    console.log(context);
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
              <td id="tdCart">$ {cartItem.itemPrice * cartItem.quantity  }</td>
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
        }
      </main>
    </React.Fragment>
  );
};

export default ShoppingCart;