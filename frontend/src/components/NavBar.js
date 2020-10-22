import React from "react";
import {Link} from 'react-router-dom'
import "../styles/Navbar.css"
import M from 'materialize-css'


document.addEventListener('DOMContentLoaded', function() {
    var elems = document.querySelectorAll('.sidenav');
    var instances = M.Sidenav.init(elems, {});
});

const elems = () => {
    return (
        <div className="row">
                    <div className="col">
                        <Link to="/">Inicio</Link>
                    </div>
                    <div className="col">
                        <Link to="/suppliers">Empresas</Link>
                    </div>
                    <div className="col">
                        <Link to="/live">En Vivo</Link>
                    </div>
                    <div className="col">
                        <Link to="/faqs">Preguntas Frecuentes</Link>
                    </div>
                    <div className="col">
                        <Link to="/howtobuy">Como comprar?</Link>
                    </div>
                    <div className="col">
                        <Link to="/aboutus">Quienes Somos?</Link>
                    </div>
                    <div className="col">
                        <Link to="/contact">Contacto</Link>
                    </div>
                </div>
    )
}

const NavBar = () => {
    

      return (
    
       <div className="NavBar"> 
           <div className="row">
               <div className="col s4">
                   <img className='img-logo' src={require('../images/logo.png')} alt="logo"/>
               </div>
               <div className="col s4">
                   <form className="form-inline">
                       <input className="form-control sm-2" id='inputSearch' type="search" placeholder="Search" aria-label="Search"/>
                   </form>
               </div>
               <div className="col s4">
                   <Link to="/">
                       <i className="material-icons left" id="iconSearch">search</i>
                   </Link>     
               </div>
           </div>           

           <nav>
                <div class="nav-wrapper">
                <a href="#" data-target="mobile-demo" class="sidenav-trigger"><i class="material-icons">menu</i></a>
                <ul class="right hide-on-med-and-down">
                    <li><Link to="/">Inicio</Link></li>
                    <li><Link to="/suppliers">Empresas</Link></li>
                    <li><Link to="/live">En Vivo</Link></li>
                    <li><Link to="/faqs">Preguntas Frecuentes</Link></li>
                    <li><Link to="/howtobuy">Como comprar?</Link></li>
                    <li><Link to="/aboutus">Quienes Somos?</Link></li>
                    <li><Link to="/contact">Contacto</Link></li>
                </ul>
                </div>
            </nav>

            <ul class="sidenav" id="mobile-demo">
                <li><Link to="/">Inicio</Link></li>
                <li><Link to="/suppliers">Empresas</Link></li>
                <li><Link to="/live">En Vivo</Link></li>
                <li><Link to="/faqs">Preguntas Frecuentes</Link></li>
                <li><Link to="/howtobuy">Como comprar?</Link></li>
                <li><Link to="/aboutus">Quienes Somos?</Link></li>
                <li><Link to="/contact">Contacto</Link></li>
            </ul>
    </div>
  );
};

export default NavBar;
