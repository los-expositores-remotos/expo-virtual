import React, { useState } from "react";
import {Link, useHistory} from 'react-router-dom'
import logo from '../images/logo.png'
import "../styles/Navbar.css"
import M from 'materialize-css'
import ShopContext from './context/shop-context'


document.addEventListener('DOMContentLoaded', function() {
    var elems = document.querySelectorAll('.sidenav');
    var instances = M.Sidenav.init(elems, {});
});

const NavBar = (props) => {
    const [textsearch, setTextSearch ] = useState('')
    const history = useHistory()
    const handleSubmit = event => {
        event.preventDefault();

        history.push(`/resultsearch/${textsearch}`)
      };
      return (
        <ShopContext.Consumer>
        {context => (
          <React.Fragment>
    
       <div className="NavBar"> 
           <div className="row">
               <div className="col s2" >
                <img id='imgLogo' src={logo}/>
               </div>
               <div className="col s6">
                   <form className="form-inline" onSubmit={handleSubmit}>
                       <input className="form-control sm-2" onKeyPress={event => event.key === 'Enter'   } onChange={(e)=> setTextSearch(e.target.value)} value = {textsearch} id='inputSearch' type="search" placeholder="Search" aria-label="Search"/>
                   </form>
               </div>
               <div className="col s1">
                   <Link to={`/resultsearch/${textsearch}`}>
                       <i className="small material-icons left" id="iconSearch">search</i>
                   </Link>     
               </div>
               <div className="col s1">
                   <Link to="/admin">
                       <i className="small material-icons left" id="iconSearch">settings</i>
                   </Link>     
               </div>
               <div className="col s1">
                   <Link to="/login">
                       <i className="small material-icons left" id="iconSearch">account_box</i>
                   </Link>     
               </div>
               <div className="col s1">
                   <Link to="/shoppingcart">
                       <div className='row'>
                           <div className='col s6'>
                            <p id='cantidadProductos'>
                                {context.cart.reduce((count, curItem) => { return count + curItem.quantity;}, 0)}
                            </p>
                           </div>
                           <div className='col s6'>
                            <i className="small material-icons left" id="iconCart">shopping_cart</i>
                           </div>
                       </div>
                   </Link>     
               </div>
           </div>           

           <nav>
                <div className="nav-wrapper">
                <a href="#" data-target="mobile-demo" className="sidenav-trigger"><i className="material-icons">menu</i></a>
                <ul className="left hide-on-med-and-down">
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

            <ul className="sidenav" id="mobile-demo">
                <li><Link to="/">Inicio</Link></li>
                <li><Link to="/suppliers">Empresas</Link></li>
                <li><Link to="/live">En Vivo</Link></li>
                <li><Link to="/faqs">Preguntas Frecuentes</Link></li>
                <li><Link to="/howtobuy">Como comprar?</Link></li>
                <li><Link to="/aboutus">Quienes Somos?</Link></li>
                <li><Link to="/contact">Contacto</Link></li>
            </ul>
    </div>
    </React.Fragment>
      )}
    </ShopContext.Consumer>
  );
};

export default NavBar;
