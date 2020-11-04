import React from "react";
import {useEffect, useState} from "react";


const Suppliers = () => {
  const [companies, setCompanies] = useState(null)
  useEffect(() => {
    fetch(`http://localhost:7000/companies`, {
      headers: {
        "Content-Type":"application/json"
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
 
      console.log(companies)
    })
    .catch((err => {
      console.log(err)
    }))
  }, [companies])


  const getSProveedores = () =>{
    if(companies){
      const listOfCompanies = companies.map((company)=> {
         
        return  (<li>
                  <p>{company.companyName}</p>
              </li>)
  
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
