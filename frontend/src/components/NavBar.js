import React from "react";
import {Link} from 'react-router-dom'

const NavBar = () => {
  return (
    <nav>
        <div className="nav-wrapper">
            <a href="#!" class="brand-logo">
                 Logo
            </a>
            <ul className="right hide-on-med-and-down">
                <li>
                    <Link to="#">
                        <i className="material-icons left">search</i>Search
                    </Link>
                </li>
                <li>
                    <Link to="/">Inicio</Link>
                </li>
                <li>
                    <Link to="/suppliers">Empresas</Link>
                </li>
                <li>
                    <Link to="/live">En Vivo</Link>
                </li>
                <li>
                    <Link to="/faqs">Preguntas Frecuentes</Link>
                </li>
                <li>
                    <Link to="/howtobuy">Como comprar?</Link>
                </li>
                <li>
                    <Link to="/aboutus">Quienes Somos?</Link>
                </li>
                <li>
                    <Link to="/contact">Contacto</Link>
                </li>
            </ul>
      </div>
    </nav>
  );
};

export default NavBar;
