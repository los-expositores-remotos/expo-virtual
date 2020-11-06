import React from "react";
import {useEffect, useState} from "react";
import { useHistory } from "react-router-dom";
import M from 'materialize-css'
import '../styles/AddProveedor.css'

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.autocomplete');
  var instances = M.Autocomplete.init(elems, {});
});

const AddProveedor = (props)  => {
  const history = useHistory();
  const company = props.company
  const [url, setUrl] = useState(null);
  const [companyName, setcompanyName] = useState(company === undefined ? "" : company.companyName )
  const [companyImage, setcompanyImage] = useState(company === undefined ? "" : company.companyImage)
  const [facebook, setfacebook] = useState(company === undefined ? "" : company.facebook)
  const [instagram, setinstagram] = useState(company === undefined ? "" : company.instagram)
  const [web, setweb] = useState(company === undefined ? "" : company.web)


  useEffect(() => {
    if (url) {
      postearAdd();
    }
  }, [url]);

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
        setUrl(data.url);
      })
      .catch((err) => {
        console.log(err);
      });
    }else{
      M.toast({ html: "cargar imagen", classes: "#c62828 red darken-3" });
    }
    };

  const postearAdd = () => {

    if(companyName && companyImage && facebook && instagram && web){
    fetch("http://localhost:7000/companies", {
      method: "POST",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify({
        "companyName": companyName ,
        "companyImage": url ,
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
        console.log(err);
      });
    }else{
      M.toast({ html: "Llenar todos los campos", classes: "#c62828 red darken-3" });
    }
  };
 
  return (
        <div class="row">
          <form class="col s12">
        <div class="row">
          <div class="input-field col s6">
              <input 
              id="Nombre_de_la_Empresa" onChange={(e) => setcompanyName(e.target.value)} type="text" class="validate" value={companyName}/>
               <label class="active" for="Nombre_de_la_Empresa">Nombre de la Empresa</label>
          </div>
              <div class="input-field col s6">
              <input id="Web" onChange={(e) => setweb(e.target.value)} type="text" class="validate" value={web }/>
               <label class="active" for="Web">Web</label>
              </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
              <input id="instagram" onChange={(e) => setinstagram(e.target.value)} type="text" class="validate" value={instagram }/>
               <label class="active" for="instagram">Instagram</label>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
              <input id="Facebook" onChange={(e) => setfacebook(e.target.value)} type="text" class="validate" value={facebook}/>
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
              <input type="file" onChange={(e) => setcompanyImage(e.target.files[0])}/>
            </div>
            <div class="file-path-wrapper">
              <input class="file-path validate" type="text" value={companyImage}/>
            </div>
          </div>
        </form>
        <div class="row">
          <div class="col s12">
                <a onClick={() => {
                  agregarProveedor();
                  if (!companyName ||
                    !companyImage ||
                    !facebook ||
                    !instagram ||
                    !web) {
                    postearAdd();
                  }
                }} class="waves-effect waves-light red lighten-2 btn-large" id="butonSubmit">Agregar Proveedor</a>
          </div>
        </div>
      </form>
      </div>
  );
};

export default AddProveedor;
