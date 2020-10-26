import React from "react";
import {useEffect, useState} from "react"
import M from 'materialize-css'
import '../../styles/Home.css'
import {Carousel} from "react-materialize"
import data from '../../data/empresas-productos.json'


  document.addEventListener('DOMContentLoaded', function() {
    var elems = document.querySelectorAll('#carousel-product');
    var elems2 = document.querySelectorAll('.slider');
    
    var instances = M.Carousel.init(elems, {});
    var instances2 = M.Slider.init(elems2, {});

  });

const CarouselofProdocts = () => {
  const dataAux = data.map((suppliers) => suppliers.productos.flat());
  
  console.log(dataAux.flat())
  let index = 0
  const products = dataAux.flat().map((product)=>   
  <div class="carousel-item red white-text" href="#one!">

        <h2>{product.nombreDelArticulo}</h2>
        <p class="white-text">{product.description}</p>
        <a class="btn waves-effect white grey-text darken-text-2">button</a>
      </div>
  )
  {console.log(products)}
    return (
      <div class="carousel carousel-slider center" id="carousel-product" >
        {products}
    </div>

    )
}





const Home = () => {
  const [companyImage, setConpanyImage] = useState([])

  useEffect(() => {
    const prevCompanyImage = companyImage
    if(companyImage.length === 0){
      fetch("http://localhost:7000/companies/images", {
        headers: {
          "Content-Type":"application/json"
        }
      })
        .then((res)=> res.json())
        .then((result)=>{
          console.log(result)
          const rta = result.map((image)=> 
            image.companyImage
          )
          console.log(rta)
          setConpanyImage(rta)        
        })
        .catch((err => {
          console.log(err)
        }))
    }
  }, [companyImage]);


  return (
    <div class="conteiner">

    <div class="slider">
    <ul class="slides">
      <li>
        <img src={require('../../images/slide-1600815112465-2850062423-37effb40d992a60818ee25980b5488cd1600815112-1920-1920.png')} alt="card"/> 
        <div class="caption center-align">
          <h5 class="light grey-text text-lighten-3"></h5>
        </div>
      </li>
      <li>
        <img src={require('../../images/slide-1601706982904-5167046360-503f2afe72347806320fa0073b8f3a101601706988-1920-1920.png')} alt="card"/> 
        <div class="caption left-align">

          <h5 class="light grey-text text-lighten-3"></h5>
        </div>
      </li>
      <li>
        <img src={require('../../images/slide-1601737383291-1474540258-731b24d59d1dd070fafc76462b9193ef1601737386-1920-1920.png')} alt="card"/> 
        <div class="caption right-align">
          <h5 class="light grey-text text-lighten-3"></h5>
        </div>
      </li>
    </ul>
    </div>
    <div >
      <CarouselofProdocts/>
    </div>
    <div>
      {
        companyImage.length === 0 ? 
        <p>loanding...</p>
        :
        <Carousel
      carouselId="Carousel-2"
      images={companyImage}
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
      }
    </div>
  </div>
  );
};

export default Home;
