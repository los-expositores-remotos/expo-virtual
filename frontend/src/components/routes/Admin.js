import React from "react";
import {useEffect, useState} from "react";
import M from 'materialize-css'
import '../../styles/Admin.css'
import Howtobuy from "./Howtobuy";
import UpdateProveedor from '../UpdateProveedor'

document.addEventListener('DOMContentLoaded', function() {
    var elem = document.querySelector('.collapsible.expandable');
    var instances = M.Collapsible.init(elem, {});
  });

const Admin = () => {
     const [form , setForm] = useState(null)
    useEffect(() => {
        
    }, [form])

    const getForm = () =>{
        return form
    }
     
     return (
        <div class="row">
            <div class="col s4">
                <ul class="collapsible popout">
                    <li>
                        <div class="collapsible-header"><i class="material-icons">Proveedores</i>Proveedores</div>
                            <div class="collapsible-body">
                                <a onClick={()=> setForm(<Howtobuy/>)} class="waves-effect waves-light red lighten-2 btn-large">Agregar Proveedor</a>
                                <a onClick={()=> setForm(<UpdateProveedor/>)} class="waves-effect waves-light red lighten-2 btn-large">Modificar Proveedor</a>
                                <a class="waves-effect waves-light red lighten-2 btn-large">Eliminar Proveedor</a>
                            </div>
                    </li>
                    <li>
                        <div class="collapsible-header"><i class="material-icons">place</i>Productos</div>
                        <div class="collapsible-body">
                                <a class="waves-effect waves-light red lighten-2 btn-large">Agregar Proveedor</a>
                                <a class="waves-effect waves-light red lighten-2 btn-large">Modificar Proveedor</a>
                                <a class="waves-effect waves-light red lighten-2 btn-large">Eliminar Proveedor</a>
                            </div>
                    </li>
                    <li>
                        <div class="collapsible-header"><i class="material-icons">whatshot</i>Banners</div>
                        <div class="collapsible-body">
                                <a class="waves-effect waves-light red lighten-2 btn-large">Agregar Banner</a>
                                <a class="waves-effect waves-light red lighten-2 btn-large">Eliminar Proveedor</a>
                            </div>
                    </li>
                </ul>      
            </div>
            <div className='clo s8'>
                {
                getForm()
                }    
            </div> 
        </div>
  );
};

export default Admin;
