import React from "react";
import {useEffect, useState} from "react";
import { useHistory } from "react-router-dom";
import M from 'materialize-css'
import '../styles/AddProveedor.css'

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.autocomplete');
  var instances = M.Autocomplete.init(elems, {});
});

const AddBanner = ()  => {
  const history = useHistory();
  const [url, seturl] = useState(null);
  const [image, setimage] = useState(null)
  const [category, setcategory] = useState(null)


  useEffect(() => {
    console.log(url)
    if (url) {
      postearAdd();
    }
  }, [url]);

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
        console.log(data);
        const urlb = data.url
        seturl(urlb);
        console.log("dataurl"+":"+urlb+"url"+":"+url)
      })
      .catch((err) => {
        console.log(err);
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
        console.log(data)
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
        <div class="row" onLoad={loadCategories}>
          <form class="col s12" id="bannerform">
          <div class="row">
              <div class="input-field col s12">
                <select id="Category" form="bannerform" type="text" onChange={(e) => setcategory(e.target.value)} class="validate" value={category}>
                    <option>Seleccione una categoría...</option>
                    <option value="HOME">HOME</option>
                    <option value="SCHEDULE">SCHEDULE</option>
                    <option value="CLASS">CLASS</option>
                    <option value="COURRIER">COURRIER</option>
                    <option value="PAYMENTMETHODS">PAYMENTMETHODS</option>
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
  );
};


export default AddBanner;
