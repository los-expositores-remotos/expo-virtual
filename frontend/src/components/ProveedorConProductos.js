import React from "react";
import {useState} from "react";
import '../styles/ProveedorConProductos.css'
import M from 'materialize-css'
import {Carousel} from 'react-materialize'
import ProductCard from "./ProductCard";

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.slider');
  var instances = M.Slider.init(elems, {});
});

const ProveedorConProductos = (props) => {
  const company = props.company
    
  const [page, setPage] = useState(0)


const listOfProducts = () => {
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
            <ProductCard product={product}/>
      )
    }
    )

    const numerosDePaginacion = () => {
      const paginas = (products.length / 4)
      for (let index = 1; index < paginas; index++) {
        
        return (
          <li className={page === index ? "active" : "waves-effect"}>
            <a onClick={()=>{setPage(index)}}>{index + 1}</a>                                                                         
          </li>
        )
        
      }
    }

    const Paginacion = () => {

      return(
      <ul className="pagination">
        <li className="waves-effect">
          <a onClick={()=>{if(page > 0){setPage(page - 1)}}}><a><i className="material-icons">chevron_left</i></a></a>
          </li>
          <li className={page === 0 ? "active" : "waves-effect"} onClick={()=>{
            setPage(0)
            }}><a>1</a></li>
            {numerosDePaginacion()}
          <li className="waves-effect">
            <a onClick={()=>{if((page + 1) <= (products.length / 4) && (products.length % 4) > 0){ setPage(page + 1)}}}><a><i className="material-icons">chevron_right</i></a></a>
          </li>
      </ul>)
    };
    
    return (
      <div>
        <div className="row">
          {result}
        </div>
          {Paginacion()}
      </div>
      )
    }
  }

  return (
    <div>
          {
            !company ?
              <p>Loading...</p>
            :
            <div>
              <h2>{company.companyName}</h2>
              {listOfProducts(company)}
            </div>
          }
        </div>

  );
};
export default ProveedorConProductos;
