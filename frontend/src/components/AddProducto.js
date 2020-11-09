import React from "react";
import {useEffect, useState} from "react";
import { useHistory } from "react-router-dom";
import M from 'materialize-css'
import '../styles/AddProveedor.css'

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.autocomplete');
  var instances = M.Autocomplete.init(elems, {});
});

const AddProduct = (props)  => {
  const history = useHistory();
  const company = props.company
  const [url, setUrl] = useState([]);
  const [itemName, setitemName] = useState(null)
  const [description, setdescription] = useState(null)
  const [images, setimages] = useState(null)
  const [stock, setstock] = useState(null)
  const [itemPrice, setitemPrice] = useState(null)
  const [promotionalPrice, setpromotionalPrice] = useState(null)


  useEffect(() => {
    if (url.length ==! 0) {
      postearAdd();
    }
  }, [url]);

  const agregarProveedor = () => {
    if(images && images.length >= 0){
      console.log(images)
      for (let index = 0; index < images.length; index++) {
        const image = images[index];
        console.log(image)
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
          setUrl(url.concat([data.url]));
        })
        .catch((err) => {
          console.log(err);
        });
      };
      console.log(url)
      
    }else{
      M.toast({ html: "cargar imagen", classes: "#c62828 red darken-3" });
    }
    };

  const postearAdd = () => {

    if( itemName &&
      description &&
      url &&
      stock &&
      itemPrice &&
      promotionalPrice){

      console.log(url)
    fetch("http://localhost:7000/products", {
      method: "POST",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify({
        "idProveedor": company.id,
        "itemName": itemName ,
        "description": description ,
        "images": url,
        "stock": stock ,
        "itemPrice": itemPrice,
        "promotionalPrice": promotionalPrice
      })
    })
      .then((res) => res.json())
      .then((data) => {
        console.log(data)
        if (data.error) {
          M.toast({ html: data.error, classes: "#c62828 red darken-3" });
        } else {
          M.toast({
            html: "Producto agregado exitosamente",
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
          <div class="input-field col s12">
              <input value={company.companyName} />
               <label class="active" for="Nombre_de_la_Empresa">Nombre de la Empresa</label>
          </div>
          <div class="input-field col s12">
              <input 
              id="Nombre_de_la_Empresa" onChange={(e) => setitemName(e.target.value)} type="text" class="validate" />
               <label class="active" for="Nombre_de_la_Empresa">Nombre de la Producto</label>
          </div>
              <div class="input-field col s6">
              <input id="Web" onChange={(e) => setitemPrice(e.target.value)} type="number" class="validate" />
               <label class="active" for="Web">Precio</label>
              </div>
        </div>
        <div class="row">
          <div class="input-field col s6">
              <input id="instagram" onChange={(e) => setpromotionalPrice(e.target.value)} type="number" class="validate" />
               <label class="active" for="instagram">Precio Promocional</label>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
              <input id="Facebook" onChange={(e) => setdescription(e.target.value)} type="text" class="validate" />
               <label class="active" for="Facebook">Descripcion</label>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
              <input id="email" onChange={(e) => setstock(e.target.value)} type="number" class="validate"/> 
               <label class="active" for="email">Stock</label>
          </div>
        </div>
        <form action="#">
          <div class="file-field input-field">
            <div class="btn" id='buttonUploadImages'>
              <span>Cargar Imagenes</span>
              <input type="file" onChange={(e) => setimages(e.target.files)} multiple/>
            </div>
            <div class="file-path-wrapper">
              <input class="file-path validate" type="text" />
            </div>
          </div>
        </form>
        <div class="row">
          <div class="col s12">
                <a onClick={() => {
                  agregarProveedor();
                  if (!itemName ||
                    !description ||
                    !images ||
                    !stock ||
                    !itemPrice ||
                    !promotionalPrice){
                    postearAdd();
                  }
                }} class="waves-effect waves-light red lighten-2 btn-large" id="butonSubmit">Agregar Producto</a>
          </div>
        </div>
      </form>
      </div>
  );
};

export default AddProduct;
