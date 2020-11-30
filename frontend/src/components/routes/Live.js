import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import '../../styles/Live.css';

const Live = () => {
  const [classes, setClasses] = useState(null)
  const [schedule, setschedule] = useState(null)

  useEffect(() => {
    if(!schedule){
    fetch("http://localhost:7000/banners/SCHEDULE", {
        headers: {
       
        }
      })
        .then((res)=> {
          if(res.ok){
            return res.json()
        }})
        .then((result)=>{
          setschedule(result)
        })
        .catch((err => {
          console.log(err)
        }))

      if(!classes){
        fetch("http://localhost:7000/banners/CLASS", {
        headers: {
       
        }
      })
        .then((res)=> {
          if(res.ok){
            return res.json()
        }})
        .then((result)=>{
          setClasses(result)
        })
        .catch((err => {
          console.log(err)
        }))}
      }  
    
  }, [classes, schedule])


  const bannerClasses = () => {
    if(classes){
    const list = classes.map((clase)=>{
        return(
                <div className='col s6'>
                  <a target="_blank"  rel="noreferrer" href='https://www.youtube.com/watch?v=vcG6bS4Kn-c&ab_channel=TobiasTorres'>
                    <img id="imgClasses" src={clase.image} alt="logo de clases"/>
                  </a>
                </div>
        )
    })
    return (
      <div className='row' id="rowId">
        {list}
      </div>
    )}
  }


  return (

   <div id='live'>
      <div>
          {
            schedule ? 
              <img atl="img" id="imgSchedule" src={schedule[0].image}></img>
            :
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
          }
      </div>
      {bannerClasses()}
   </div>


  );
};

export default Live;
