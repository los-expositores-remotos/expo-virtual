import React, { useState, useEffect, useRef } from 'react'
import {Link, useHistory} from 'react-router-dom'
import M from 'materialize-css'

const ListOfProductToDelete = (props) =>{
  const company = props.company
  const history = useHistory()
  const [products, setproducts] = useState([])
  const [prevProducts, setprevProducts] = useState([]) 
  useEffect(() => {
        
      if(products.length === 0){      
            fetch(`http://localhost:7000/products/supplier/${company.id}`, {
            headers: {
     
            }
            }) 
            .then((res)=> {
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

  const deleteProduct = (id) =>{
    fetch(`http://localhost:7000/products/${id}`, {
      method: 'DELETE',
      headers: {
       
      }
    }).then((res)=> 
      {
        M.toast({
          html: "Producto eliminado exitosamente",
          classes: "#388e3c green darken-2",
        });
        history.push("/admin");
      }
    ) 
    .then(()=>{
      setproducts([])
  })  
  }
    
    const listOfProducts = () => {

      if(products){
        const list = products.map((product)=> {
          return (
            <li>
            <div className="col s1" id='colCard'>
              <div className="card" id='cardDeleteProducto'>
                <div className="card-image">
                  <img src={product.images[0]}/>
                  <a onClick={()=> {
                    setprevProducts(products)
                    
                    deleteProduct(product.id)
                    }} className="btn-floating halfway-fab waves-effect waves-light red"><i className="material-icons">delete</i></a>
                </div>
                <div className="card-content">
                   <strong><p> {product.itemName}</p></strong>
                   <hr/>
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
                <input className="form-control sm-2" id='inputSearchFormAdmin' type="search" placeholder="Buscar" aria-label="Search"/>
              </form>
          </div>
          <div className='col s2'>
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