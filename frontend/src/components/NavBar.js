import React from "react";
import {Link} from 'react-router-dom'
import "../styles/Navbar.css"



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

           <nav className="center-align">
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
            </nav>
    </div>
  );
};

export default NavBar;
