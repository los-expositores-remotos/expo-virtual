import React, { useState, useEffect } from 'react'
import {Carousel} from 'react-materialize'

const ProductCard = (props) => {
const product = props.product
const [cant, setCant] = useState(1)

let productModified = {
  description: product.description,
  id: product.id,
  idProveedor: product.idProveedor,
  images: product.images,
  itemName: product.itemName,
  itemPrice: product.itemPrice,
  promotionalPrice: product.promotionalPrice,
  stock: product.stock,
  cant: cant

}

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

  const existElement = (listOfObjects, prod) => {
    const listAux = listOfObjects.map((product)=>product.id)
    console.log(listAux.includes(prod.id))
    return (listAux.includes(prod.id))
  }

  const addToCart = () => {
    var myProducts =  JSON.parse(localStorage.getItem("products")) || []
    if(existElement(myProducts, product)){
      let index = myProducts.findIndex((myproduct)=> myproduct.id === product.id )
      myProducts[index].cant = myProducts[index].cant +  1
      localStorage.setItem("products", JSON.stringify(myProducts))
    }else{
      myProducts.push(productModified)
      localStorage.setItem("products", JSON.stringify(myProducts))
    }

  }

return (
    <div className="col s3" id='cardOfProducts'>
    <div className="card" id="cardId">
      <div className="card-image">
        {imagesOfProducts(product)}
        <span className="card-title">{product.itemName}</span>
      </div>
      <div className="card-content">
        <p>{product.description}</p>
        <p>Stock: {product.stock}</p>
        <p>Precio: {product.itemPrice}</p>
        <p>Precio promocional: {product.promotionalPrice}</p>
      </div>
      <div className="card-action">
        <a href="#" onClick={()=> addToCart()}>Agregar al Carrito</a>
      </div>
    </div>
  </div>
)
}

export default ProductCard;