import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import ResultSearchProduct from './ResultSearchProduct'



const ResultSearch = () => {

    const [products, setProductos] = useState(null) 
    let {textsearch} = useParams()
    
    const textoBusqueda = () => {
        if(products){
            return (
                <h2>
                   Resultado de búsqueda para "{textsearch}" 
                </h2>
            )
        }else{
            return (
                <h2>
                    No hay Resultado de búsqueda para "{textsearch}" 
                </h2>
            )
        }
    }

    useEffect(() => {  
          fetch(`http://localhost:7000/products/search?text=${textsearch}`, {
            headers: {
        
            }
          })
            .then((res)=> {
              if(res.ok){
                return res.json()
            }})
            .then((result)=>{
              setProductos(result.Products)        
            })
            .catch((err => {
              console.log(err)
            }))
        
    
      }, [textsearch]);
    
      console.log(products)
    return (
        <div>
            {textoBusqueda()}
            <ResultSearchProduct products={products}/>
        </div>
    )

}


export default ResultSearch;