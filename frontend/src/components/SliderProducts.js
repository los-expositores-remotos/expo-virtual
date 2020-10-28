import React from "react";
import {useState, useEffect} from "react";
import '../styles/SliderProducts.css'
import {Carousel, Card, Icon, Button,
  CardTitle, Slider, Slide, Caption} from "react-materialize"

const SliderProducts = () => {
  const [products, setproducts] = useState([])
  const [listOfProducts, setlistOfProducts] = useState([])
  const styleItem = {"height": "400px !important"}
  useEffect(() => {
    if(products.length === 0){
      fetch("http://localhost:7000/products", {
        headers: {
          "Content-Type":"application/json"
        }
      })
        .then((res)=> res.json())
        .then((result)=>{
          console.log(result)
          setproducts(result)        
        })
        .catch((err => {
          console.log(err)
        }))
    }
  }, [products, listOfProducts])
  const dinamicIndex = (index) => {
    if(index <= 3){
      return index++
    }else{
      index = 0
      return index
    }
  }

  const getListOfProducts = () => {
    let index = 0
    if(products.length === 0){
      return (<p>Loading...</p>)
    }else{
    let index = 0 
    const placenment = ["center", "right", "left"]
    const productlist = products.map((product)=>   
      <Slide image={<img className="prodImg" alt="" src={product.imagenes[0]}/>}>
         <Caption id="prodcaption" placement={placenment[index <= 3 ?  index++ : index--]}>
           {console.log(index)}
              <h3>
                {product.nombreDelArticulo}
              </h3>         
              <Button>
                Comprar a solo $ {product.precioPromocional}
              </Button>
         </Caption>
        </Slide>
      )
      return (
        <Slider
          fullscreen={false}
          options={{
            duration: 500,
            indicators: true,
            interval: 4000
          }}
        >
        {
          productlist
        }
    </Slider>

      )
    }
  }
  return (getListOfProducts());
};

export default SliderProducts;
