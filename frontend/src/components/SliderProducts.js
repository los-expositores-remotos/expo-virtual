import React from "react";
import {useState, useEffect} from "react";
import '../styles/SliderProducts.css'
import { Button, Slider, Slide, Caption} from "react-materialize"
import ShopContext from './context/shop-context';

const SliderProducts = () => {
  const [products, setproducts] = useState(null)
    
    useEffect(() => {
      if(!products){
          fetch("http://localhost:7000/products", {
            headers: {
   
            }
          })
            .then((res)=> {
              //console.log(res)
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
          }
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
            <div className="preloader-wrapper active">
            <div className="spinner-layer spinner-red-only">
              <div className="circle-clipper left">
                <div className="circle"></div>
              </div><div className="gap-patch">
                <div className="circle"></div>
              </div><div className="circle-clipper right">
                <div className="circle"></div>
              </div>
            </div>
          </div>
          
            )
        }else{
        let index = 0 
        const placenment = ["center", "right", "left"]
        //console.log(products)
        const productlist = products.map((product)=>  
        <ShopContext.Consumer>
            {context => (
              <React.Fragment> 
            <Slide image={<img className="prodImg" alt={product.itemName} src={product.images[0]}/>}>
                <Caption id="prodcaption" placement={placenment[dinamicIndex(index)]}>
     
                    <h3>
                        {product.itemName}
                    </h3>         
                    <Button onClick={context.addProductToCart.bind(this, product)}>
                        Comprar a solo $ {product.promotionalPrice}
                    </Button>
                </Caption>
                </Slide>
                </React.Fragment>
      )}
    </ShopContext.Consumer>
        )
          return (
            
            <Slider
            fullscreen={false}
            options={{
              duration: 500,
              indicators: true,
              interval: 6000
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
