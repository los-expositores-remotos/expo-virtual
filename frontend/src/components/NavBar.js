import React, {useContext, useState } from "react";
import {Link, useHistory} from 'react-router-dom'
import logo from '../images/logo.png'
import "../styles/Navbar.css"
import M from 'materialize-css'
import ShopContext from './context/shop-context'
import {userContext} from "../App"


document.addEventListener('DOMContentLoaded', function() {
    var elems = document.querySelectorAll('.sidenav');
    M.Sidenav.init(elems, {});
});

const NavBar = () => {
    const [textsearch, setTextSearch ] = useState(null)
    const history = useHistory()
    const { state, dispatch } = useContext(userContext);
    const [mouse, setMouse] = useState(false)
    const [Inicio, setInicio] = useState(false)
    const [Empresas, setEmpresas] = useState(false)
    const [EnVivo, setEnVivo] = useState(false)
    const [Preguntas, setPreguntas] = useState(false)
    const [CómoComprar, setCómoComprar] = useState(false)
    const [QuiénesSomos, setQuiénesSomos] = useState(false)
    const [Contacto, setContacto] = useState(false)








    const handleSubmit = event => {
        event.preventDefault();
        if(textsearch){
            history.push(`/resultsearch/${textsearch}`)
        }else{
            history.push("/resultsearch/ ")
        }
        setTextSearch("")
      };



      const renderButton = () => {
        if (state) {
            return(
        <button 
            id ="botonSesion"
            className="btn #c62828 red darken-3" 
            onClick={() => {
                            localStorage.clear();
                            dispatch({ type: "CLEAR" });
                            M.toast({
                                html: "Sesión cerrada exitosamente",
                                classes: "#388e3c green darken-2",
                              });
                              history.push("/");
                            }}
            >
               Salir
        </button> )
        }else{
            return(
            <button 
                id ="botonSesion"
                className="btn #c62828 red darken-3" 
                onClick={() => {
                                history.push("/login");
                                }}
                >
                    Ingresar
             </button> )
        }
    }

    const renderPanelAdmin = () => {
        if(state === "admin"){
            return(
            <Link to="/admin">
                <i className="small material-icons left" id="iconSearch">settings</i>
            </Link>)    
        }
    }




      return (
        <ShopContext.Consumer>
        {context => (
          <React.Fragment>
    
       <div className="NavBar"> 
           <div className="row">
               <div className="col s2" >
                <img alt="logo" id='imgLogo' src={logo}/>
               </div>
               <div className="col s5">
                   <form className="form-inline" onSubmit={handleSubmit}>
                       <input className="form-control sm-2" onKeyPress={event => event.key === 'Enter'   } onChange={(e)=> setTextSearch(e.target.value)} value = {textsearch} id='inputSearch' type="search" placeholder="Buscar" aria-label="Search"/>
                   </form>
               </div>
               <div className="col s1">
                   <Link to={textsearch ? `/resultsearch/${textsearch}` : "/resultsearch/ " }>
                       <i className="small material-icons left" id="iconSearch" onClick={()=> setTextSearch("")}>search</i>
                   </Link>     
               </div>
               <div className="col s1">
                    {renderPanelAdmin()}
               </div>
               <div className="col s1">
                   <div id= "usuario" className={mouse ? "animate__animated animate__tada animate__infinite infinite" : " "} onMouseEnter={() => setMouse(true)} onMouseLeave={() =>setMouse(false)}>
                        {localStorage.getItem('nombre')} {localStorage.getItem('apellido')}   
                   </div>
               </div>
               <div className="col s1">
                   <Link to="/shoppingcart">
                      
                            <p id='cantidadProductos'>
                                {context.cart.reduce((count, curItem) => { return count + curItem.quantity;}, 0)}
                            </p>
           
                            <i className="small material-icons left" id="iconCart">shopping_cart</i>
              
                   </Link>     
               </div>
               <div id="colBotonSesion" className="col s1">
                    {renderButton()}
               </div>
           </div>           

           <nav>
                <div className="nav-wrapper">
                <a href="" data-target="mobile-demo" className="sidenav-trigger"><i className="material-icons">menu</i></a>
                <ul className="left hide-on-med-and-down">
                    <li><Link className={Inicio ? "animate__animated animate__heartBeat animate__repeat-1	1" : " "} onMouseEnter={() => setInicio(true)} onMouseLeave={() =>setInicio(false)} to="/">Inicio</Link></li>
                    <li><Link className={Empresas ? "animate__animated animate__heartBeat animate__repeat-1	1" : " "} onMouseEnter={() => setEmpresas(true)} onMouseLeave={() =>setEmpresas(false)} to="/suppliers">Empresas</Link></li>
                    <li><Link className={EnVivo ? "animate__animated animate__heartBeat animate__repeat-1	1" : " "} onMouseEnter={() => setEnVivo(true)} onMouseLeave={() =>setEnVivo(false)} to="/live">En vivo</Link></li>
                    <li><Link className={Preguntas ? "animate__animated animate__heartBeat animate__repeat-1	1" : " "} onMouseEnter={() => setPreguntas(true)} onMouseLeave={() =>setPreguntas(false)} to="/faqs">Preguntas frecuentes</Link></li>
                    <li><Link className={CómoComprar ? "animate__animated animate__heartBeat animate__repeat-1	1" : " "} onMouseEnter={() => setCómoComprar(true)} onMouseLeave={() =>setCómoComprar(false)} to="/howtobuy">¿Cómo comprar?</Link></li>
                    <li><Link className={QuiénesSomos ? "animate__animated animate__heartBeat animate__repeat-1	1" : " "} onMouseEnter={() => setQuiénesSomos(true)} onMouseLeave={() =>setQuiénesSomos(false)} to="/aboutus">¿Quiénes somos?</Link></li>
                    <li><Link className={Contacto ? "animate__animated animate__heartBeat animate__repeat-1	1" : " "} onMouseEnter={() => setContacto(true)} onMouseLeave={() =>setContacto(false)} to="/contact">Contacto</Link></li>
                </ul>
                </div>
            </nav>

            <ul className="sidenav" id="mobile-demo">
                <li ><Link to="/">Inicio</Link></li>
                <li ><Link to="/suppliers">Empresas</Link></li>
                <li ><Link to="/live">En vivo</Link></li>
                <li ><Link to="/faqs">Preguntas frecuentes</Link></li>
                <li ><Link to="/howtobuy">¿Cómo comprar?</Link></li>
                <li ><Link to="/aboutus">¿Quiénes Somos?</Link></li>
                <li ><Link to="/contact">Contacto</Link></li>
            </ul>
    </div>
    </React.Fragment>
      )}
    </ShopContext.Consumer>
  );
};

export default NavBar;
