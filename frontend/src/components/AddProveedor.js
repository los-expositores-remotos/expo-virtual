import React from "react";
import M from 'materialize-css'
import '../styles/AddProveedor.css'

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.autocomplete');
  var instances = M.Autocomplete.init(elems, {});
});

const AddProveedor = (props)  => {
  const company = props.company




  
  console.log(company)
  return (
        <div class="row">
          <form class="col s12">
        <div class="row">
          <div class="input-field col s6">
              <input id="Nombre_de_la_Empresa" type="text" class="validate" value={company? company.companyName : undefined }/>
               <label class="active" for="Nombre_de_la_Empresa">Nombre de la Empresa</label>
          </div>
              <div class="input-field col s6">
              <input id="Web" type="text" class="validate" value={company? company.web : undefined }/>
               <label class="active" for="Web">Web</label>
              </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
              <input id="instagram" type="text" class="validate" value={company? company.instagram : undefined }/>
               <label class="active" for="instagram">Instagram</label>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
              <input id="Facebook" type="text" class="validate" value={company? company.facebook : undefined }/>
               <label class="active" for="Facebook">Facebook</label>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
              <input id="email" type="email" class="validate" value={company? company.email : undefined }/>
               <label class="active" for="email">Email</label>
          </div>
        </div>
        <form action="#">
          <div class="file-field input-field">
            <div class="btn" id='buttonUploadImages'>
              <span>Cargar Imagen</span>
              <input type="file"/>
            </div>
            <div class="file-path-wrapper">
              <input class="file-path validate" type="text"/>
            </div>
          </div>
        </form>
        <div class="row">
          <div class="col s12">
            {
              company === undefined ?
                <a class="waves-effect waves-light red lighten-2 btn-large" id="butonSubmit">Agregar Proveedor</a>
              :
                <a class="waves-effect waves-light red lighten-2 btn-large" id="butonSubmit">Modificar Proveedor</a>
            }
          </div>
        </div>
      </form>
      </div>
  );
};

export default AddProveedor;
