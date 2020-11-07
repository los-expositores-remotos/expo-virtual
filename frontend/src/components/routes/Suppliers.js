import React from "react";
import {useEffect, useState} from "react";
import M from 'materialize-css'
import {Carousel,Pagination} from 'react-materialize'
import ProveedorConProductos from '../ProveedorConProductos'

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.slider');
  var instances = M.Slider.init(elems, {});
});

const Suppliers = () => {
  const [companies, setCompanies] = useState(null)
  const [page, setPage] = useState(0) 
  useEffect(() => {
    if(!companies){
    fetch(`http://localhost:7000/companies`, {
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
      //console.log(result)

        setCompanies(result)        
 
      //console.log(companies)
    })
    .catch((err => {
      //console.log(err)
    }))
  }
  }, [companies])

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


const listOfProducts = (company) => {
  
  const products = company.products
  console.log(products)
  if(products.length > 0)  {
  const res = []
      for (let index = (page * 4); index < ((page + 1) * 4); index++) {

        //console.log(products)
        //console.log(index)

          console.log(products[index])
          const element = products[index];
          console.log(element)
          if(products[index] === undefined){
            
            console.log("el elemnto es undefined")
            
          }else{
            res.push(element)
            console.log(res)
          }
      }
  
  const result = res.map((product) => {
      return (
            <div class="col s3" id='cardOfProducts'>
              <div class="card">
                <div class="card-image">
                  {imagesOfProducts(product)}
                  <span class="card-title">{product.itemName}</span>
                </div>
                <div class="card-content">
                  <p>{product.description}</p>
                  <p>Stock: {product.stock}</p>
                  <p>Precio: {product.itemPrice}</p>
                  <p>Precio promocional: {product.promotionalPrice}</p>
                </div>
                <div class="card-action">
                  <a href="#">ver</a>
                </div>
              </div>
            </div>
      )
    }
    )
    return (
      <div>
        <div class="row">
          {result}
        </div>
          <Pagination
          activePage={page + 1}
          items={(products.length / 4)+1}
          leftBtn={<a onClick={()=>{if(page > 0){setPage(page - 1)}}}><i class="material-icons">chevron_left</i></a>}
          maxButtons={(products.length / 4)}
          rightBtn={<a onClick={()=>{if((page * 4) < products.length ){ setPage(page + 1)}}}><i class="material-icons">chevron_right</i></a>}
          />
      </div>
      )
    }
  }

  const getSProveedores = () =>{
    if(companies){
      const listOfCompanies = companies.map((company)=> {
         
        return  (
                  <li>
                      <ProveedorConProductos company={company}/>
                  </li>
                )
  
      })
      return (
        <ul>
          {listOfCompanies}
        </ul>
      )
    }
  }
  return (
    <div>
          {
            !companies ?
              <p>Loading...</p>
            :
            getSProveedores()
          }
        </div>

  );
};

export default Suppliers;
