import React from "react";
import {useEffect, useState} from "react";
import { useHistory, Link } from "react-router-dom";
import M from 'materialize-css'
import '../../styles/AddProveedor.css'

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.autocomplete');
  M.Autocomplete.init(elems, {});
});

const AddBanner = ()  => {
  const history = useHistory();
  const [url, seturl] = useState(null);
  const [image, setimage] = useState(null)
  const [category, setcategory] = useState(null)


  useEffect(() => {
    //console.log(url)
    if (url) {
      postearAdd();
    }
  },);

  const agregarBanner = () => {
    if(image){

      const data = new FormData();
      data.append("file", image);
      data.append("upload_preset", "insta-clon-GB");
      data.append("cloud_name", "instaclongbarreiro");
      fetch("https://api.cloudinary.com/v1_1/instaclongbarreiro/image/upload", {
        method: "POST",
      body: data,
    })
    .then((res) => res.json())
      .then((data) => {
        //console.log(data);
        const urlb = data.url
        seturl(urlb);
      })
      .catch((err) => {
        //console.log(err);
      });
    }else{
      M.toast({ html: "cargar imagen", classes: "#c62828 red darken-3" });
    }
    };

  const postearAdd = () => {
    if(image && category){
    fetch("http://localhost:7000/banners/", {
      method: "POST",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify({
        "image": url,
        "category": category
      })
    })
      .then((res) => res.json())
      .then((data) => {
        //console.log(data)
        if (data.error) {
          M.toast({ html: data.error, classes: "#c62828 red darken-3" });
        } else {
          M.toast({
            html: "Banner agregado exitosamente",
            classes: "#388e3c green darken-2",
          });
          history.push("/admin");
        }
      })
      .catch((err) => {
        console.log(err);
      });
    }else{
      M.toast({ html: "Llenar todos los campos", classes: "#c62828 red darken-3" });
    }
  };

  const loadCategories = () => {
      var categories = ["HOME", "SCHEDULE", "CLASS", "COURRIER", "PAYMENTMETHODS"];
      categories.sort();
      addOptions("Category", categories);
  }
     
  const addOptions = (idElement, array) => {
      var select = document.getElementsById(idElement)[0];
      array.forEach(element => {
        var option = document.createElement("option");
        option.text = array[element];
        select.add(option);
      });
  }
 
  return (
    <div className="row">
    <div className="col s4">
        <ul className="collapsible">
            <li>
                <div className="collapsible-header"><i className="material-icons">Proveedores</i>Proveedores</div>
                    <div className="collapsible-body">
                     <Link  className="waves-effect waves-light red lighten-2 btn-large" to="/admin/agregarproveedor">Agregar Proveedor</Link>   
                     <Link  className="waves-effect waves-light red lighten-2 btn-large" to="/admin/borrarproveedor">Eliminar Proveedor</Link>  
                     <Link  className="waves-effect waves-light red lighten-2 btn-large" to="/admin/modificarproveedor">Modificar Proveedor</Link>  
                    </div>
            </li>
            <li>
                <div className="collapsible-header"><i className="material-icons">place</i>Productos</div>
                <div className="collapsible-body">
                      <Link  className="waves-effect waves-light red lighten-2 btn-large" to="/admin/agregarproducto">Agregar Productos</Link> 
                      <Link  className="waves-effect waves-light red lighten-2 btn-large" to="/admin/modificarproducto">Modificar Productos</Link> 
                      <Link  className="waves-effect waves-light red lighten-2 btn-large" to="/admin/borrarproducto">Eliminar Productos</Link> 
                    </div>
            </li>
            <li>
                <div className="collapsible-header"><i className="material-icons">whatshot</i>Banners</div>
                <div className="collapsible-body">
                      <Link  className="waves-effect waves-light red lighten-2 btn-large" to="/admin/agregarbanner">Agregar Banner</Link>  
                      <Link  className="waves-effect waves-light red lighten-2 btn-large" to="/admin/borrarbanner" >Eliminar Banner</Link>
                    </div>
            </li>
        </ul>      
    </div>
    <div className='col s8'>
    <div class="row" onLoad={loadCategories}>
          <form class="col s12" id="bannerform">
          <div class="row">

              <div class="input-field col s12">
                <select id="Category" form="bannerform" type="text" onChange={(e) => setcategory(e.target.value)} class="validate" value={category}>
                    <option>Seleccione una categoría...</option>
                    <option value="HOME">Inicio</option>
                    <option value="SCHEDULE">Calendario de clases</option>
                    <option value="CLASS">Clase en vivo</option>
                    <option value="COURRIER">Correo</option>
                    <option value="PAYMENTMETHODS">Metodos de pago</option>
                </select>
                <label class="active" htmlFor="Category">Categoría</label>
              </div>
           </div>
           <form action="#">
              <div class="file-field input-field">
               <div class="btn" id='buttonUploadImages'>
                 <span>Cargar Imagen</span>
                 <input type="file" onChange={(e) => setimage(e.target.files[0])}/>
               </div>
               <div class="file-path-wrapper">
                 <input class="file-path validate" type="text" value={url}/>
               </div>
             </div>
           </form>
        <div class="row">
          <div class="col s12">
                <a onClick={() => {
                  agregarBanner();
                  if (image &&
                    category && url) {
                    postearAdd();
                  }
                }} class="waves-effect waves-light red lighten-2 btn-large" id="butonSubmit">Agregar Banner</a>
          </div>
        </div>
      
      </form>
      </div>
    </div> 
</div>

        
  );
};


export default AddBanner;
