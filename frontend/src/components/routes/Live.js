import React, { useEffect, useState } from "react";
import '../../styles/Live.css';

const Live = () => {
  const [classes, setClasses] = useState(null)
  const [schedule, setschedule] = useState(null)

  useEffect(() => {
    if(!schedule){
    fetch("http://localhost:7000/banners/SCHEDULE", {
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

          setschedule(result)        
        })
        .catch((err => {
          console.log(err)
        }))

      if(!classes){
        fetch("http://localhost:7000/banners/CLASS", {
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
                    <img id="imgClasses" src={clase.image} alt="logo de clases"/>
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
              <img id="imgSchedule" src={schedule[0].image}></img>
            :
              <p>Loading...</p>
          }
      </div>
      {bannerClasses()}
   </div>


  );
};

export default Live;
