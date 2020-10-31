import React from "react";
import {useState, useEffect} from "react";
import '../styles/SliderProducts.css'
import {Carousel, Card, Icon, Button,
  CardTitle, Slider, Slide, Caption} from "react-materialize"

const SliderProducts = () => {
  const [products, setproducts] = useState([])
  
  useEffect(() => {
      fetch("http://localhost:7000/products", {
        headers: {
          "Content-Type":"application/json"
        }
      })
        .then((res)=> {
          console.log(res)
          if(res.ok){
              return res.json()
          }
          })
        .then((result)=>{
         
            setproducts(result)        
          
        })
        .catch((err => {
          console.log(err)
        }))
    
  }, [products])
  const dinamicIndex = (index) => {
    if(index <= 3){
      return index++
    }else{
      index = 0
      return index
    }
  }

  const getListOfProducts = () => {
      if(!products){
          return (
            <div class="preloader-wrapper active">
            <div class="spinner-layer spinner-red-only">
              <div class="circle-clipper left">
                <div class="circle"></div>
              </div><div class="gap-patch">
                <div class="circle"></div>
              </div><div class="circle-clipper right">
                <div class="circle"></div>
              </div>
            </div>
          </div>
          
            )
        }else{
        let index = 0 
        const placenment = ["center", "right", "left"]
        console.log(products)
        if(products){
        const productlist = products.map((product)=>   
            <Slide image={<img className="prodImg" alt="" src={product.images[0]}/>}>
                <Caption id="prodcaption" placement={placenment[index <= 3 ?  index++ : index - 3 ]}>
                {console.log(index)}
                    <h3>
                        {product.itemName}
                    </h3>         
                    <Button>
                        Comprar a solo $ {product.promotionalPrice}
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
  }
  return (getListOfProducts());
};

export default SliderProducts;
