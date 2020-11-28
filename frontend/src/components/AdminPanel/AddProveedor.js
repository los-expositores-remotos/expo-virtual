import React from "react";
import {useEffect, useState} from "react";
import { useHistory, Link } from "react-router-dom";
import M from 'materialize-css'
import '../../styles/AddProveedor.css'


const AddProveedor = (props)  => {
  const history = useHistory();
  const company = props.company
  const [urlBanner, setUrlBanner] = useState(null);
  const [urlLogo, setUrlLogo] = useState(null);
  const [companyName, setcompanyName] = useState(null )
  const [companyImage, setcompanyImage] = useState(null)
  const [companyBanner, setcompanyBanner] = useState(null)
  const [facebook, setfacebook] = useState(null)
  const [instagram, setinstagram] = useState(null)
  const [web, setweb] = useState(null)

  
  document.addEventListener('DOMContentLoaded', function() {
    var elems = document.querySelectorAll('.autocomplete');
    M.Autocomplete.init(elems, {});
  });

  useEffect(() => {
    if (urlBanner && urlLogo) {
      postearAdd();
    }
  });

  const agregarProveedor = () => {
    if(companyImage){
      const data = new FormData();
      data.append("file", companyImage);
      data.append("upload_preset", "insta-clon-GB");
      data.append("cloud_name", "instaclongbarreiro");
      fetch("https://api.cloudinary.com/v1_1/instaclongbarreiro/image/upload", {
        method: "POST",
      body: data,
    })
    .then((res) => res.json())
      .then((data) => {
        console.log(data);
        setUrlLogo(data.url);
        postear()
      })
      .catch((err) => {
        console.log(err);
      });
    }else{
      M.toast({ html: "cargar imagen", classes: "#c62828 red darken-3" });
    }
    };
  const subirBanner = () => {
    if(companyBanner){
      const data = new FormData();
      data.append("file", companyBanner);
      data.append("upload_preset", "insta-clon-GB");
      data.append("cloud_name", "instaclongbarreiro");
      fetch("https://api.cloudinary.com/v1_1/instaclongbarreiro/image/upload", {
        method: "POST",
      body: data,
    })
    .then((res) => res.json())
      .then((data) => {
        //console.log(data);
        setUrlBanner(data.url);
        postear()
      })
      .catch((err) => {
        console.log(err);
      });
    }else{
      M.toast({ html: "cargar imagen", classes: "#c62828 red darken-3" });
    }
    };

    const postear = () => {
      if(urlLogo && urlBanner){
        console.log("se hizo el posteo")
        postearAdd()
      }else(
        console.log("no se hizo el posteo por que urlLogo es "  + {urlLogo} +  " y urlBanner es " + {urlBanner})
      )
    }
  const postearAdd = () => {

    if(companyName && companyImage && facebook && instagram && web && companyBanner){
    fetch("http://localhost:7000/companies", {
      method: "POST",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify({
        "companyName": companyName ,
        "companyImage": urlLogo ,
        "companyBanner": urlBanner ,
        "facebook": facebook ,
        "instagram": instagram ,
        "web": web
      })
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.error) {
          M.toast({ html: data.error, classes: "#c62828 red darken-3" });
        } else {
          M.toast({
            html: "Proveedor agregado exitosamente",
            classes: "#388e3c green darken-2",
          });
          history.push("/admin");
        }
      })
      .catch((err) => {
        //console.log(err);
      });
    }else{
      M.toast({ html: "Llenar todos los campos", classes: "#c62828 red darken-3" });
    }
  };
 
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
    <div className="row">
          <form className="col s12">
        <div className="row">
          <div className="input-field col s6">
              <input 
              id="Nombre_de_la_Empresa" onChange={(e) => setcompanyName(e.target.value)} type="text" className="validate" value={companyName} required/>
               <label className="active" for="Nombre_de_la_Empresa">Nombre de la Empresa</label>
          </div>
              <div className="input-field col s6">
              <input id="Web" onChange={(e) => setweb(e.target.value)} type="text" className="validate" value={web } required/>
               <label className="active" for="Web">Web</label>
              </div>
        </div>
        <div className="row">
          <div className="input-field col s12">
              <input id="instagram" onChange={(e) => setinstagram(e.target.value)} type="text" className="validate" value={instagram } required/>
               <label className="active" for="instagram">Instagram</label>
          </div>
        </div>
        <div className="row">
          <div className="input-field col s12">
              <input id="Facebook" onChange={(e) => setfacebook(e.target.value)} type="text" className="validate" value={facebook} required/>
               <label className="active" for="Facebook">Facebook</label>
          </div>
        </div>
        <div className="row">
          <div className="input-field col s12">
              <input id="email" type="email" className="validate" value={company? company.email : undefined } required/> 
               <label className="active" for="email">Email</label>
          </div>
        </div>
        <form action="#">
          <div className="file-field input-field">
            <div className="btn" id='buttonUploadImages'>
              <span>Cargar Imagen</span>
              <input type="file" onChange={(e) => setcompanyImage(e.target.files[0])} required/>
            </div>
            <div className="file-path-wrapper">
              <input className="file-path validate" type="text" value={companyImage}/>
            </div>
          </div>
        </form>
        <form action="#">
          <div className="file-field input-field">
            <div className="btn" id='buttonUploadImages'>
              <span>Cargar Banner</span>
              <input type="file" onChange={(e) => setcompanyBanner(e.target.files[0])} required/>
            </div>
            <div className="file-path-wrapper">
              <input className="file-path validate" type="text" value={companyBanner}/>
            </div>
          </div>
        </form>
        <div className="row">
          <div className="col s12">
                <a onClick={() => {
                  agregarProveedor();
                  subirBanner()
                }} className="waves-effect waves-light red lighten-2 btn-large" id="butonSubmit">Agregar Proveedor</a>
          </div>
        </div>
      </form>
      </div>
    </div> 
</div>

       
  );
};

export default AddProveedor;
