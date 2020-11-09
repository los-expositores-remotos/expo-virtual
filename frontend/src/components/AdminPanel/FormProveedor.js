import React from "react";
import M from 'materialize-css'
import '../styles/FormProveedor.css'

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.autocomplete');
  var instances = M.Autocomplete.init(elems, {});
});

const FormProveedor = () => {
 
  
  return (
    
        <div className="row">
          <form className="col s12">
        <div className="row">
          <div className="input-field col s6">
              <input id="Nombre_de_la_Empresa" type="text" className="validate"/>
              <label for="Nombre_de_la_Empresa">Nombre de la Empresa</label>
          </div>
              <div className="input-field col s6">
              <input id="Web" type="text" className="validate"/>
              <label for="Web">Web</label>
              </div>
        </div>
        <div className="row">
          <div className="input-field col s12">
              <input id="instagram" type="text" className="validate"/>
              <label for="instagram">Instagram</label>
          </div>
        </div>
        <div className="row">
          <div className="input-field col s12">
              <input id="Facebook" type="text" className="validate"/>
              <label for="Facebook">Facebook</label>
          </div>
        </div>
        <div className="row">
          <div className="input-field col s12">
              <input id="email" type="email" className="validate"/>
              <label for="email">Email</label>
          </div>
        </div>
        <form action="#">
          <div className="file-field input-field">
            <div className="btn" id='buttonUploadImages'>
              <span>Cargar Imagen</span>
              <input type="file"/>
            </div>
            <div className="file-path-wrapper">
              <input className="file-path validate" type="text"/>
            </div>
          </div>
        </form>
        <div className="row">
          <div className="col s12">
            <a className="waves-effect waves-light red lighten-2 btn-large" id="butonSubmit">Agregar Proveedor</a>
          </div>
        </div>
      </form>
      </div>
  );
};

export default FormProveedor;
