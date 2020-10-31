import React from "react";
import {useEffect, useState} from "react"
import M from 'materialize-css'
import '../../styles/Home.css'
import {Carousel} from "react-materialize"
import SliderProducts from '../SliderProducts'


  document.addEventListener('DOMContentLoaded', function() {
    var elems2 = document.querySelectorAll('.slider');
    var instances2 = M.Slider.init(elems2, {});
  });

const Home = () => {
  const [companyImage, setConpanyImage] = useState([])
  
  useEffect(() => {
    if(companyImage.length === 0){
      fetch("http://localhost:7000/companies/images", {
        headers: {
          "Content-Type":"application/json"
        }
      })
        .then((res)=> {
          if(res.ok){
            return res.json()
        }})
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
      <li className='bannerImg'>
        <img className='bannerImg'src={require('../../images/slide-1600815112465-2850062423-37effb40d992a60818ee25980b5488cd1600815112-1920-1920.png')} alt="card"/> 
        <div class="caption center-align">
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
      <h5 className='productos'>Productos Destacados</h5>
   </div>
    <SliderProducts />
    <div>
        <h5 className='productos'> Nuestras Empresas</h5>
    </div>
   <div>
      {
        companyImage.length === 0 ? 
        <div class="preloader-wrapper active">
          <div class="spinner-layer spinner-red-only">
            <div class="circle-clipper left">
              <div class="circle"></div>
            </div><div class="gap-patch">
              <div class="circle"></div>
            </div><div class="circle-clipper right">
              <div class="circle"></div>
            </div>
          </div>
        </div>
        :
        <Carousel
      carouselId="Carousel-2"
      images={companyImage}
      options={{
        dist: -100,
        duration: 200,
        fullWidth: false,
        indicators: true,
        noWrap: false,
        numVisible: 4,
        onCycleTo: null,
        padding: 10,
        shift: 0
      }}
    />
      }
    </div>
  </div>
  );
};

export default Home;
