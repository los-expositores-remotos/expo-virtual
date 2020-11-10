import React from "react";
import {useEffect, useState} from "react"
import M from 'materialize-css'
import '../../styles/Home.css'
import {Carousel} from "react-materialize"
import SliderProducts from '../SliderProducts'
import Banner from '../Banner'


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
    <div>
    <Banner/>
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
