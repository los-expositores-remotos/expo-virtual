import React from "react";
import {useState} from "react";
import { useHistory } from "react-router-dom";
import M from 'materialize-css'

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.autocomplete');
  M.Autocomplete.init(elems, {});
});

const UpdateProveedorForm = (props)  => {
  const history = useHistory();
  const company = props.company
  const [urlLogo, setUrlLogo] = useState(null);
  const [urlBanner, setUrlBanner] = useState(null);
  const [companyName, setcompanyName] = useState(company.companyName )
  const [companyImage, setcompanyImage] = useState(company.companyImage)
  const [companyBanner, setcompanyBanner] = useState(company.companyBanner)
  const [facebook, setfacebook] = useState(company.facebook)
  const [instagram, setinstagram] = useState(company.instagram)
  const [web, setweb] = useState(company.web)

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
        setUrlLogo(data.url);
      })
      .then(postear())
      .catch((err) => {
        console.log(err);
      });
    }else{
      setUrlLogo(companyImage)
      postear()
    }
  };
  const agregarBanner = () => {
    if(SubirAlaNubeBanner()){
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
      }).
      then(
        postear()
      )
      .catch((err) => {
        console.log(err);
      });
    }else{
      setUrlBanner(companyBanner)
      postear()
    }
  };

  const SubirAlaNube = () => {
    return (! typeof companyImage === 'string')
  }
  const SubirAlaNubeBanner = () => {
    return (! typeof companyBanner === 'string')
  }
  const postComapanyImage = () =>{
    if(SubirAlaNube()){
      return urlLogo
    }else{
      return companyImage
    }
  }
  const postCompanyBanner = () =>{
    if(SubirAlaNubeBanner()){
      return urlBanner
    }else{
      return companyBanner
    }
  }
  
  const postear = () => {
    if(urlLogo && urlBanner){
      console.log("se hizo el posteo")
      postearUpdate()
    }else(
      console.log("no se hizo el posteo por que urlLogo es "  + {urlLogo} +  " y urlBanner es " + {urlBanner})
    )
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
        "companyBanner": postCompanyBanner(),
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
              <input id="Web" onChange={(e) => setweb(e.target.value)} type="text" class="validate" value={web } required/>
               <label class="active" for="Web">Web</label>
              </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
              <input id="instagram" onChange={(e) => setinstagram(e.target.value)} type="text" class="validate" value={instagram } required/>
               <label class="active" for="instagram">Instagram</label>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
              <input id="Facebook" onChange={(e) => setfacebook(e.target.value)} type="text" class="validate" value={facebook} required/>
               <label class="active" for="Facebook">Facebook</label> 
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
              <input id="email" type="email" class="validate" value={company? company.email : undefined } required/> 
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
                }}
                required/>
            </div>
            <div class="file-path-wrapper">
              <input class="file-path validate" type="text" value={typeof companyImage === 'string' ? companyImage : urlLogo } required/>
            </div>
          </div>
        </form>
        <form action="#">
          <div class="file-field input-field">
            <div class="btn" id='buttonUploadImages'>
              <span>Cargar Banner</span>
              <input type="file" onChange={(e) => {
                setcompanyBanner(e.target.files[0])
                //console.log(companyImage)
                }}
                required/>
            </div>
            <div class="file-path-wrapper">
              <input class="file-path validate" type="text" value={typeof companyBanner === 'string' ? companyBanner : urlBanner }/>
            </div>
          </div>
        </form>
        <div class="row">
          <div class="col s12">
                 
                <button  onClick={() => {    
                    agregarProveedor();
                    agregarBanner()
                  }
                } 
                class="waves-effect waves-light red lighten-2 btn-large" id="butonSubmit">Modificar Proveedor</button>
          </div>
        </div>
      </form>
      </div>
  );
};
export default UpdateProveedorForm;