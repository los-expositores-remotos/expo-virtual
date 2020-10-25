import React from "react";
import M from 'materialize-css'
import '../../styles/Home.css'
import data from '../../data/empresas-productos.json'

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.carousel');
  var instances = M.Carousel.init(elems, {});
  var elems2 = document.querySelectorAll('.slider');
  var instances2 = M.Slider.init(elems2, {});
});
  var instance = M.Carousel.init({
    fullWidth: true,
    indicators: true
  });

const CarouselofProdocts = () => {
  const dataAux = data.map((suppliers) => {
    return suppliers.productos
  });
  console.log(dataAux)
  let index = 0
  const products = dataAux.map((product)=>   
  <div class="carousel-item red white-text" href="#one!">
        <h2>Panel Nro {index++}</h2>
        <p class="white-text">This is your first panel</p>
      </div>
  )
    return (
      <div class="carousel carousel-slider center">
      <div class="carousel-fixed-item center">
        <a class="btn waves-effect white grey-text darken-text-2">button</a>
      </div>
        {products}
    </div>

    )
}


const CarouselSuppliers =() =>{
  const dataAux = data
  const result = dataAux.map((suppliers) => 
  <li>
       <a class="carousel-item" href="#one!">
         <img alt="card" src={suppliers.imagenDeLaEmpresa}/>
       </a>
     </li> 
    )
    return (
      <ul>{result}</ul>
    )
  } 

const Home = () => {
  
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
    <div>
      <CarouselofProdocts/>
    </div>
    <div class="carousel">
        <CarouselSuppliers/>
      </div>
  </div>
  );
};

export default Home;
