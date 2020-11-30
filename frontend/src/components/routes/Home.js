import React from "react";
import {useEffect, useState} from "react"
import M from 'materialize-css'
import '../../styles/Home.css'
import {Carousel} from "react-materialize"
import SliderProducts from '../SliderProducts'
import Banner from '../Banner'


  document.addEventListener('DOMContentLoaded', function() {
    var elems2 = document.querySelectorAll('.slider');
    M.Slider.init(elems2, {});
  });

const Home = () => {
  const [companyImage, setConpanyImage] = useState(null)
  
  useEffect(() => {
    if(!companyImage){
      fetch("http://localhost:7000/companies", {
        headers: {
         
        }
      })
        .then((res)=> {
          console.log(res)
          if(res.ok){
            return res.json()
        }})
        .then((result)=>{
          console.log(result)
          if(result){
          const rta = result.map((company)=> 
            company.companyImage
            )
            console.log(rta)
            setConpanyImage(rta)}        
          })
          .catch((err => {
            console.log(err)
          }))
        }
        }, [companyImage]);
  
  return (
    <div>
    <Banner/>
   <div>
      <h5 id= "googleFont" className='productos'>Productos Destacados</h5>
   </div>
    <SliderProducts />
    <div>
        <h5 id="googleFont" className='productos'> Nuestras Empresas</h5>
    </div>
   <div>
      {
        ! companyImage ? 
        <div className="preloader-wrapper active">
          <div className="spinner-layer spinner-red-only">
            <div className="circle-clipper left">
              <div className="circle"></div>
            </div><div className="gap-patch">
              <div className="circle"></div>
            </div><div className="circle-clipper right">
              <div className="circle"></div>
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
