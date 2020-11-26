import React, { useState, useEffect } from "react";
import {Slider, Slide, Caption} from 'react-materialize'

const Banner = () => {
  const [banners, setbanners] = useState(null)

  useEffect(() => {
    if(!banners){
      fetch("http://localhost:7000/banners/HOME", {
        headers: {
        }
      })
        .then((res)=> {
          if(res.ok){
            return res.json()
        }})
        .then((result)=>{
          //console.log(result)
          
          setbanners(result)        
        })
        .catch((err => {
          console.log(err)
        }))
    }
  }, [banners])

  const getBanners = () => {
    if(banners){
      const list = banners.map((banner) => {
        return (

          <Slide image={<img alt="" src={banner.image}/>}>
              <Caption placement="center">
              </Caption>
            </Slide>
        )    
      }
                              )
      return list
    }
  }


  return (

    <Slider
    fullscreen={false}
    options={{
      duration: 500,
      height: 400,
      indicators: true,
      interval: 6000
    }}
  >
    {getBanners()}
  </Slider>

  );
};
;

export default Banner;
