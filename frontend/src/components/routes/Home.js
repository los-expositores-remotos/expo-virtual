import React from "react";
import M from 'materialize-css'
import '../../styles/Home.css'

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.slider');
  var instances = M.Slider.init(elems, {});
});




const Home = () => {
  return (
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
  );
};

export default Home;
