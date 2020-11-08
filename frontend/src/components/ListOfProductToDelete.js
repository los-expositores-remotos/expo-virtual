import React, { useState, useEffect, useRef } from 'react'
import {Link} from 'react-router-dom'
import M from 'materialize-css'

const ListOfProductToDelete = (props) =>{
    const company = props.company
  const [products, setproducts] = useState([])
  const [prevProducts, setprevProducts] = useState([]) 
  useEffect(() => {
        
      if(products.length === 0){      
            fetch(`http://localhost:7000/products/supplier/${company.id}`, {
            headers: {
                "Content-Type":"application/json"
            }
            }) 
            .then((res)=> {
            //console.log(res)
            if(res.ok){
                return res.json()
            }
            })
            .then((result)=>{
            // console.log(result)

                setproducts(result)        
        
            //console.log(companies)
            })
            .catch((err => {
            console.log(err)
            }))
            }  
        }, [products])

  const deleteProduct = (id) =>{
    console.log(id)
    fetch(`http://localhost:7000/products/${id}`, {
      method: 'DELETE',
      headers: {
        "Content-Type":"application/json"
      }
    }).then((res)=> 
      {
        M.toast({
          html: "Producto eliminado exitosamente",
          classes: "#388e3c green darken-2",
        });
      }
    ) 
  }
    
    const listOfProducts = () => {

      if(products){
        const list = products.map((product)=> {
          return (
            <li>
            <div class="col s1" id='colCard'>
              <div class="card" id='cardDelete'>
                <div class="card-image">
                  <img src={product.images[0]}/>
                  <span class="card-title">{product.itemName}</span>
                  <a onClick={()=> {
                    setprevProducts(products)
                    
                    deleteProduct(product.id)
                    }} class="btn-floating halfway-fab waves-effect waves-light red"><i class="material-icons">delete</i></a>
                </div>
                <div class="card-content">
                  <p > stock : {product.stock} </p>
                  <p > precio : {product.itemPrice} </p>
                  <p > precio Promocional : {product.promotionalPrice} </p>
                </div>
              </div>
              </div>
              </li>
            )
        })
        return(    
          <ul>    
            <div className='row'>
                {list}
            </div>
          </ul>
        )
        }

    }
    return (
        <div className="row">
          <div className="col s10" id="formimputSearch">
              <form className="form-inline">
                <input className="form-control sm-2" id='inputSearchFormAdmin' type="search" placeholder="Search" aria-label="Search"/>
              </form>
          </div>
          <div class='col s2'>
              <Link>
                  <i className="small material-icons left" id="iconSearchFormAdmin">search</i>
              </Link>     
          </div>
        <div>
          {
            !products ?
              <p>Loading...</p>
            :
              listOfProducts()
          }
        </div>
      </div>
    )
}

export default ListOfProductToDelete;