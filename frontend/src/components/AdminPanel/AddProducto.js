import React from "react";
import {useEffect, useState} from "react";
import { useHistory, Link } from "react-router-dom";
import M from 'materialize-css'
import '../../styles/AddProveedor.css'

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.autocomplete');
  M.Autocomplete.init(elems, {});
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
  const [longitud, setLongitud] = useState(null)
  const [alto, setAlto] = useState(null)
  const [ancho, setAncho] = useState(null)
  const [pesoGr, setPesoGr] = useState(null)
  const [postear, setPostear] = useState(false)


  useEffect(() => {
//    if (postear) {
//      postearAdd();
//    }
  }, []);

  const agregarProveedor = () => {
    let urlImag = []
    if(images && images.length >= 0){
      //console.log(images)

      for (let index = 0; index < images.length; index++) {
        const image = images[index];
        //console.log(image)
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
            let newURl = [data.url].concat(url)
              console.log(newURl)
              urlImag.push(data.url)
              setUrl(newURl);
            //console.log(data);
//          setUrl(url.concat([data.url]));
//          urlImag.push(data.url)
//          setPostear(true)
        })

        .catch((err) => {
          console.log(err);
        });
      };
      //console.log(url)
      
    }else{
      M.toast({ html: "cargar imagen", classes: "#c62828 red darken-3" });
    }
    console.log("urlImage: ")
    console.log(urlImag)
    return urlImag
    };

  const postearAdd = (listImage) => {
    console.log("URL: " + url)
    console.log(url)
    if( itemName &&
      description &&
      url &&
      stock &&
      itemPrice &&
      promotionalPrice){

      //console.log(url)
    fetch("http://localhost:7000/products", {
      method: "POST",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify({
        "idProveedor": company.id,
        "itemName": itemName ,
        "description": description ,
        "images": listImage,
        "stock": stock ,
        "vendidos": 0,
        "itemPrice": itemPrice,
        "promotionalPrice": promotionalPrice,
        "longitud": longitud,
        "ancho": ancho,
        "alto": alto,
        "pesoGr": pesoGr
      })
    })
      .then((res) => res.json())
      .then((data) => {
        //console.log(data)
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
        //console.log(err);
      });
    }else{
      M.toast({ html: "Llenar todos los campos", classes: "#c62828 red darken-3" });
    }
  };
 
  return (
            <div className="row">
          <form className="col s12">
        <div className="row">
          <div className="input-field col s12">
              <input value={company.companyName} required />
               <label className="active" for="Nombre_de_la_Empresa">Nombre de la Empresa</label>
          </div>
          <div className="input-field col s12">
              <input 
              id="Nombre_de_la_Empresa" onChange={(e) => setitemName(e.target.value)} type="text" className="validate" required />
               <label className="active" for="Nombre_de_la_Empresa">Nombre de la Producto</label>
          </div>
              <div className="input-field col s6">
              <input id="Web" onChange={(e) => setitemPrice(e.target.value)} type="number" className="validate" required />
               <label className="active" for="Web">Precio</label>
              </div>
        </div>
        <div className="row">
          <div className="input-field col s6">
              <input id="instagram" onChange={(e) => setpromotionalPrice(e.target.value)} type="number" className="validate" required />
               <label className="active" for="instagram">Precio Promocional</label>
          </div>
        </div>
        <div className="row">
          <div className="input-field col s12">
              <input id="Facebook" onChange={(e) => setdescription(e.target.value)} type="text" className="validate" required />
               <label className="active" for="Facebook">Descripcion</label>
          </div>

           <div className="row">
                    <div className="input-field col s2">
                        <input id="Longitud" type="number" onChange={(e) => setLongitud(e.target.value)} className="validate" value={longitud}/>
                         <label className="active" for="Longitud">Longitud</label>
                    </div>
                    <div className="input-field col s2">
                        <input id="Ancho" type="number" onChange={(e) => setAncho(e.target.value)} className="validate" value={ancho}/>
                         <label className="active" for="Ancho">Ancho</label>
                    </div>
                    <div className="input-field col s2">
                        <input id="Alto" type="number" onChange={(e) => setAlto(e.target.value)} className="validate" value={alto}/>
                         <label className="active" for="Alto">Alto</label>
                    </div>
                    <div className="input-field col s2">
                        <input id="PesoGr" type="number" onChange={(e) => setPesoGr(e.target.value)} className="validate" value={pesoGr}/>
                         <label className="active" for="PesoGr">Peso en gr</label>
                    </div>
                  </div>

        </div>
        <div className="row">
          <div className="input-field col s12">
              <input id="email" onChange={(e) => setstock(e.target.value)} type="number" className="validate" required/> 
               <label className="active" for="email">Stock</label>
          </div>
        </div>
        <form action="#">
        <select type = "hidden">
            {url.map(image => {
                return (<option>{image}</option>)
            })}
        </select>
          <div className="file-field input-field">
            <div className="btn" id='buttonUploadImages'>
              <span>Cargar Imagenes</span>
              <input type="file" onChange={(e) => setimages(e.target.files)} multiple required/>
            </div>
            <div className="file-path-wrapper">
              <input className="file-path validate" type="text" />
            </div>
          </div>
        </form>
        <div className="row">
          <div className="col s12">
                <a onClick={() => {
                  setPostear(true)

                    postearAdd(agregarProveedor());
                }} className="waves-effect waves-light red lighten-2 btn-large" id="butonSubmit">Agregar Producto</a>
          </div>
        </div>
      </form>
      </div>
  );
};

export default AddProduct;
