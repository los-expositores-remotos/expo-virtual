import React, { useState, useEffect } from 'react'
import '../styles/UpdatProveedor.css'
import {Link} from 'react-router-dom'
import UpdateProveedorForm from './UpdateProveedorForm'

const UpdateProveedor = () =>{
  const [companies, setCompanies] = useState([])
  const [ cliked , setClicked] = useState(null)

  useEffect(() => {
    fetch("http://localhost:7000/companies", {
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
      console.log(err)
    }))
  }, [companies, cliked])
    
    
    const listOfCompanies = () => {

      if(companies){
        const list = companies.map((company)=> {
          return (
            <li>
            <div class="col s1" id='colCard'>
              <div class="card" id='cardDelete'>
                <div class="card-image">
                  <img src={company.companyImage}/>
                  <span class="card-title">{company.companyName}</span>
                  <a onClick={ ()=> setClicked(<UpdateProveedorForm company={company}/>) } class="btn-floating halfway-fab waves-effect waves-light red"><i class="material-icons">mode_edit</i></a>
                </div>
                <div class="card-content">
                  <a href={"http://"+company.facebook} target="_blank"><p>Facebook</p></a>
                  <a href={"http://"+company.instagram} target="_blank"><p>Instagram</p></a>
                  <a href={"http://"+company.web} target="_blank"><p>Web</p></a>
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
      cliked ? 
        cliked
        :
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
                  !companies ?
                  <p>Loading...</p>
                  :
                  listOfCompanies()
                }
                </div>
                </div>
    )
}

export default UpdateProveedor;