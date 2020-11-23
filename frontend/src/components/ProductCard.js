import React from 'react'
import {Carousel} from 'react-materialize'
import ShopContext from './context/shop-context';
import '../styles/ProductCard.css'

const ProductCard = (props) => {
const product = props.product
const imagesOfProducts = (product) =>{
    //console.log(product)
  const images = product.images
  
  return (
     
    <Carousel
    carouselId="Carousel-2"
    images={images}
    options={{
      dist: -100,
      duration: 200,
      fullWidth: false,
      indicators: false,
      noWrap: false,
      numVisible: 5,
      onCycleTo: null,
      padding: 0,
      shift: 0
    }}
  />
     
  )
  }


return (
  <ShopContext.Consumer>
      {context => (
        <React.Fragment>
            <div className="col s3" id='cardOfProducts'>
            <div className="card" id="cardId">
              <div className="card-image">
                {imagesOfProducts(product)}
              </div>
              <div id= "cardContent" className="card-content">
                <strong><h5>{product.itemName}</h5></strong>
                <p>Stock: {product.stock}</p>
                <p>Precio: {product.itemPrice}</p>
                <p>Precio promocional: {product.promotionalPrice}</p>
              </div>
              <div className="card-action">
                {product.stock === 0 ?
                <button id="botonAgregarAlCarrito" disabled >Sin Stock</button>
                :
                <button id="botonAgregarAlCarrito" onClick={context.addProductToCart.bind(this, product)}>Agregar al Carrito</button>
                }
              </div>
            </div>
            </div>
        </React.Fragment>
      )}
    </ShopContext.Consumer>






)
}

export default ProductCard;