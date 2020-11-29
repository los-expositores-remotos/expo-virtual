import React from "react";
import {useEffect, useState} from "react";
import M from 'materialize-css'
import ProveedorConProductos from '../ProveedorConProductos'

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.slider');
  M.Slider.init(elems, {});
});

const Suppliers = () => {
  const [companies, setCompanies] = useState(null)
  useEffect(() => {
    if(!companies){
    fetch("http://localhost:7000/companies", {
      headers: {
    
      }
    }) 
      .then((res)=> {
      console.log(res)
      if(res.ok){
        return res.json()
      }
    })
    .then((result)=>{
      console.log(result)

        setCompanies(result)        
 
      //console.log(companies)
    })
    .catch((err => {
      //console.log(err)
    }))
  }
  }, [companies])


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
            <div>
              <div class="preloader-wrapper big active">
                <div class="spinner-layer spinner-blue">
                  <div class="circle-clipper left">
                    <div class="circle">
                    </div>
                  </div>
                    <div class="gap-patch">
                      <div class="circle">
                      </div>
                    </div>  
                    <div class="circle-clipper right">
                    <div class="circle">
                    </div>
                </div>
              </div>
              </div>
            </div>
            :
            getSProveedores()
          }
    </div>

  );
};

export default Suppliers;
