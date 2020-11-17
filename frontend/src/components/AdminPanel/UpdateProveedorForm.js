import React from "react";
import {useEffect, useState} from "react";
import { useHistory ,Link } from "react-router-dom";
import M from 'materialize-css'

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.autocomplete');
  var instances = M.Autocomplete.init(elems, {});
});

const UpdateProveedorForm = (props)  => {
  const history = useHistory();
  const company = props.company
  const [url, setUrl] = useState(null);
  const [companyName, setcompanyName] = useState(company.companyName )
  const [companyImage, setcompanyImage] = useState(company.companyImage)
  const [facebook, setfacebook] = useState(company.facebook)
  const [instagram, setinstagram] = useState(company.instagram)
  const [web, setweb] = useState(company.web)

  useEffect(() => {
    if (url) {
      postearUpdate();
    }
  },[url]);

  const agregarProveedor = () => {
    if(SubirAlaNube()){
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
        //console.log(data);
        setUrl(data.url);
      })
      .catch((err) => {
        console.log(err);
      });
    }else{
      setUrl(companyImage)
    }
  };

  const SubirAlaNube = () => {
    return (! typeof companyImage === 'string')
  }
  const postComapanyImage = () =>{
    if(SubirAlaNube()){
      return url
    }else{
      return companyImage
    }
  }
  
  const postearUpdate = () => {
    

      fetch(`http://localhost:7000/companies/${company.id}`, {
        method: "PUT",
        headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify({
        "companyName": companyName ,
        "companyImage": postComapanyImage(),
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
            html: "Proveedor modificado exitosamente",
            classes: "#388e3c green darken-2",
          });
          history.push("/admin");
        }
      })
      .catch((err) => {
        console.log(err);
      });
    
  };

  console.log(company)
  return (
      

        <div class="row">
          <form class="col s12">
        <div class="row">
          <div class="input-field col s6">
              <input 
              id="Nombre_de_la_Empresa" onChange={(e) => { 
                setcompanyName(e.target.value)
                console.log(companyName)
                }} type="text" class="validate" value={companyName}/>
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
              <input type="file" onChange={(e) => {
                setcompanyImage(e.target.files[0])
                //console.log(companyImage)
                }}/>
            </div>
            <div class="file-path-wrapper">
              <input class="file-path validate" type="text" value={typeof companyImage === 'string' ? companyImage : url }/>
            </div>
          </div>
        </form>
        <div class="row">
          <div class="col s12">
                 
                <a  onClick={() => {
                  //console.log(companyImage)
                  //console.log(url)
                  //console.log(companyImage === url)        
                    agregarProveedor();
                    if (!companyName ||
                      !companyImage ||
                      !facebook ||
                      !instagram ||
                      !web) {
                      postearUpdate()
                  }
                  }
                } 
                class="waves-effect waves-light red lighten-2 btn-large" id="butonSubmit">Modificar Proveedor</a>
              
          </div>
        </div>
      </form>
      </div>
  );
};
export default UpdateProveedorForm;