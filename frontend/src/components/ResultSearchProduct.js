import React, { useState } from 'react'
import {Carousel} from 'react-materialize'
import {useParams } from 'react-router-dom'
import {Dropdown, Button, Divider, Icon} from 'react-materialize'
import ProductCard from './ProductCard'



const ResultSearchProduct = (props) => {
    const products = props.products
    let {textsearch} = useParams()
    console.log(products)
    const [orderProduct, setOrderProduct] = useState([]) 

    const orderButton = () => {

        return (
                <Dropdown
                    id="Dropdown_6"
                    options={{
                        alignment: 'left',
                        autoTrigger: true,
                        closeOnClick: true,
                        constrainWidth: true,
                        container: null,
                        coverTrigger: true,
                        hover: false,
                        inDuration: 150,
                        onCloseEnd: null,
                        onCloseStart: null,
                        onOpenEnd: null,
                        onOpenStart: null,
                        outDuration: 250
                    }}
                            trigger={<Button node="button">Ordenar por</Button>}>
                    <a href="#" onClick={()=>ordenarprecioAsc() }> Precio Ascendente  </a>
                    <a href="#" onClick={()=>ordenarprecioDesc() }> Precio Descendente  </a>
                    <Divider />  
                    <a href="#" onClick={()=>ordenarAlfabeticamenteAsc() }> Alfabeticamente Ascendente</a>
                    <a href="#" onClick={()=>ordenarAlfabeticamenteDesc() }>Alfabeticamente Desscendente</a>
            </Dropdown>
            )
              
    }

    const ordenarprecioAsc = () =>{
        const list = products.sort((a, b) => parseFloat(a.itemPrice) - parseFloat(b.itemPrice));
        setOrderProduct(list)
    }
    const ordenarprecioDesc = () =>{
        const list = products.sort((a, b) => parseFloat(b.itemPrice) - parseFloat(a.itemPrice));
        setOrderProduct(list)
    }
    const ordenarAlfabeticamenteAsc = () =>{
        const list = products.sort(function(a, b){
            if(a.itemName < b.itemName) { return -1; }
            if(a.itemName > b.itemName) { return 1; }
            return 0;
        })
        setOrderProduct(list)
    }
    const ordenarAlfabeticamenteDesc = () =>{
        const list = products.sort(function(a, b){
            if(a.itemName > b.itemName) { return -1; }
            if(a.itemName < b.itemName) { return 1; }
            return 0;
        })
        setOrderProduct(list)
    }



    
    const listOfProducts = (productos) => {
        console.log(products)
        if(productos.length > 0)  {
        const res = []
            for (let index = 0; index < productos.length ; index++) {
      
              //console.log(productos)
              //console.log(index)
      
                console.log(productos[index])
                const element = productos[index];
                console.log(element)
                if(productos[index] === undefined){
                  
                  console.log("el elemnto es undefined")
                  
                }else{
                  res.push(element)
                  console.log(res)
                }
            }
        
        const result = res.map((product) => {
            return (
                 <ProductCard product={product}/>
            )
          }
          )
          return (
            <div>
              <div className="row">
                {result}
              </div>
            </div>
            )
          }
        }
         
        return(
            products ? 
                orderProduct.length === 0 ?
                <div>
                    <div>
                        {orderButton()}
                    </div>
                    <div className='row'>                  
                            {listOfProducts(products)}
                    </div>
                </div>
                :
                <div>
                    <div>
                        {orderButton()}
                    </div>
                    <div className='row'>                  
                        {listOfProducts(orderProduct)}
                    </div>
                </div>
            :
            <p>No existen resultados para " {textsearch} "</p>
        )
    }

export default ResultSearchProduct;