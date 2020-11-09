import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const Search = () => {

    const [result, setResult] = useState(null);

    let {search} = useParams()

    useEffect(() => {
        if(!result){
          fetch("http://localhost:7000/products/search?text="+search, {
            headers: {
              "Content-Type":"application/json"
            }
          })
            .then((res)=> {
              if(res.ok){
                return res.json()
            }})
            .then((result)=>{
              setResult(result)
              console.log(result)
            })
            .catch((err => {
              console.log(err)
            }))
        }
    
      }, [result]);
        

    return(

        <div>
            <h1>resultados:</h1>
        </div>
    )


}

export default Search;