import React, { useContext } from 'react'
import ShopContext from './context/shop-context'
import '../styles/MercadoPagoProducto.css'
const MercadoPagoProducto = (props) => {
    const context = useContext(ShopContext);

    const producto = props.producto  
    return(
                     <div class="col s3">
                        <div class="col-md-3">
                            <img id='imagenMPC'  src={producto.images[0]} alt={producto.itemName}/>
                        </div>
                        <div id="detalleProdMPC" class="col-md-4 product-detail">
                            <h5>{producto.itemName}</h5>
                        <div class="product-info">
                            <p><b>Description: </b><span id="product-description">{producto.description}</span><br/>
                            <b>Price:</b> $ <span id="unit-price" >{producto.itemPrice}</span></p>
                        </div>
                        </div>
                        <div class="col-md-3 product-detail">
                            <label for="quantity"><h5>Cantidad</h5></label>
                           
                            <p id="inptCartCant"> {producto.quantity}</p>
                         
                        </div>
                    </div>
    )
}

export default MercadoPagoProducto;