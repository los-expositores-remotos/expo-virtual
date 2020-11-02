import React from "react";
import M from 'materialize-css'
import '../../styles/Howtobuy.css'

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.autocomplete');
  var instances = M.Autocomplete.init(elems, {});
});

const Howtobuy = () => {
 
  
    return (
  
     <p> estoy en el Como comprar </p>
  
    );
  
};

export default Howtobuy;
