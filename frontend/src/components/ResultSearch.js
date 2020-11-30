import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import ResultSearchProduct from './ResultSearchProduct'



const ResultSearch = () => {

    const [products, setProductos] = useState([]) 
    let {textsearch} = useParams()
    
    const textoBusqueda = () => {
        if(products.length > 0){
            return (
                <h2>
                   Resultado de búsqueda para "{textsearch}" 
                </h2>
            )
        }else{
          if(textsearch === "_"){
            return(
            <h2>
              No existen Resultados de búsqueda para " " 
            </h2>)
          }else{

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
          }
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