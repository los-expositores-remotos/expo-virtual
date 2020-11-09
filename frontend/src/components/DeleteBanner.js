import React, { useState, useEffect } from 'react'
import '../styles/DeleteBanner.css'
import {Link} from 'react-router-dom'
import M from 'materialize-css'

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('select');
  var instances = M.FormSelect.init(elems, {});
});

const DeleteBanner = () =>{
  const [banners, setBanners] = useState([])
  const [category, setcategory] = useState(null)
  useEffect(() => {
    fetch(`http://localhost:7000/banners`, {
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

        setBanners(result)        
 
      //console.log(companies)
    })
    .catch((err => {
      console.log(err)
    }))
  }, [banners])
    
  const deleteBanner = (id) =>{
    console.log(id)
    fetch(`http://localhost:7000/banners/${id}`, {
      method: 'DELETE',
      headers: {
        "Content-Type":"application/json"
      }
    }).then((res)=> 
      {
        M.toast({
          html: "Banner eliminado exitosamente",
          classes: "#388e3c green darken-2",
        });
      }
    ) 
  }
    
    const listOfBanners = (category) => {
      
      if(banners){
        const bannersByCategory = banners.filter(banner => banner.category == category)
        const list = bannersByCategory.map((banner)=> {
          return (
            <li>
            <div class="col s1" id='colCard'>
              <div class="card" id='cardDelete'>
                <div class="card-image">
                  <img src={banner.image}/>
                  <span class="card-title">{banner.category}</span>
                  <a onClick={()=> {
                    deleteBanner(banner.id)
                    }} class="btn-floating halfway-fab waves-effect waves-light red"><i class="material-icons">delete</i></a>
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
        <div>
          {
            !banners ?
              <p>Loading...</p>
            :
            <div class="row">
              <form class="col s12" id="bannerform">
              <div class="row">
                  <div class="input-field col s12">
                    <select class="input-field" id="Category" form="bannerform" type="text" onChange={(e) => setcategory(e.target.value)} class="validate" value={category}>
                        <option value={null}>Seleccione una categoría...</option>
                        <option value="HOME">Inicio</option>
                        <option value="SCHEDULE">Calendario de clases</option>
                        <option value="CLASS">Clase en vivo</option>
                        <option value="COURRIER">Correo</option>
                        <option value="PAYMENTMETHODS">Metodos de pago</option>
                    </select>
                    <label class="active" htmlFor="Category">Categoría</label>
                  </div>
              </div>
              </form>
              {listOfBanners(category)}
            </div>
          }
        </div>
    )
}

export default DeleteBanner;