import React from "react";
import {useState} from "react";
import '../styles/ProveedorConProductos.css'
import M from 'materialize-css'
import ProductCard from "./ProductCard";

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.slider');
  M.Slider.init(elems, {});
});

const ProveedorConProductos = (props) => {
  const company = props.company
    
  const [page, setPage] = useState(0)


const listOfProducts = () => {
  const products = company.products
  //console.log(products)
  if(products.length > 0)  {
  const res = []
      for (let index = (page * 4); index < ((page + 1) * 4); index++) {

        //console.log(products)
        //console.log(index)

          //console.log(products[index])
          const element = products[index];
          //console.log(element)
          if(products[index] === undefined){
            
            console.log("el elemnto es undefined")
            
          }else{
            res.push(element)
            //console.log(res)
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
      let list = []
      for (let index = 0; index < paginas; index++) {
         list.push(

         <li className={page === index ? "active" : "waves-effect"}>
            <a href="" onClick={()=>{setPage(index)}}>{index + 1}</a>                                                                         
          </li>
        )         
      }
      return list
    }

    const Paginacion = () => {

      return(
      <ul id="mypaginacion" className="pagination">
        <li className="waves-effect">
          <a href="" onClick={()=>{if(page > 0){setPage(page - 1)}}}><i className="material-icons">chevron_left</i></a>
          </li>
            {numerosDePaginacion()}
          <li className="waves-effect">
            <a href="" onClick={()=>{if((page + 1) <= (products.length / 4) && (products.length % 4) > 0){ setPage(page + 1)}}}><i className="material-icons">chevron_right</i></a>
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
    }else{
      return(
      <h5> Por el Momento la empresa: <strong>{company.companyName}</strong> no tiene productos para mostrar </h5>
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
              <img id="imagenEmpresa" alt={company.companyName} src={company.companyImage}/>
              <br/>
              <h2>{company.companyName}</h2>
              {listOfProducts(company)}
              <hr/>
            </div>
          }
        </div>

  );
};
export default ProveedorConProductos;
