import React from "react";
import {useEffect, useState} from "react";
import {useHistory} from "react-router-dom";
import M from 'materialize-css'
import '../../styles/DeleteBanner.css'

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.autocomplete');
  M.Autocomplete.init(elems, {});
});

const UpdateProductoForm = (props)  => {
  const product = props.product
  const [url, setUrl] = useState(product.images);
  const [itemName, setitemName] = useState(product.itemName )
  const [images, setimages] = useState(product.images)
  const [description, setdescription] = useState(product.description)
  const [stock, setstock] = useState(product.stock)
  const [itemPrice, setitemPrice] = useState(product.itemPrice)
  const [promotionalPrice, setpromotionalPrice] = useState(product.promotionalPrice)
  const [longitud,setlongitud] = useState(product.longitud)
  const [ancho,setancho] = useState(product.ancho)
  const [alto,setalto] = useState(product.alto)
  const [pesoGr,setpesoGr] = useState(product.pesoGr)
  const [subir, setSubir] = useState(false)
  const [postear, setpostear] = useState(false)
  const history = useHistory()

  useEffect(() => {
    if(postear){
      postearUpdate();
    }
    },[url]);

  const agregarProducto = () => {
    if(subir){
      console.log("ENTRE AL SUBIR")
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
              console.log(data.url);
              console.log(url);
              let newURl = [data.url].concat(url)
              console.log(newURl)
              setUrl(newURl);
              console.log(url);
              setpostear(true)
            })
            .catch((err) => {
              console.log(err);
            });
      }  
    }
    
  };
 
  const postearUpdate = () => {
      fetch(`http://localhost:7000/products/${product.id}`, {
        method: "PUT",
        headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify({
        "idProveedor": product.idProveedor,
        "itemName": itemName,
        "description": description,
        "images": url ,
        "stock": stock,
        "vendidos": product.vendidos,
        "itemPrice": itemPrice,
        "promotionalPrice": promotionalPrice,
        "longitud": longitud ,
        "ancho": ancho ,
        "alto": alto ,
        "pesoGr": pesoGr
      })
    })
      .then((res) => {
        console.log(res)
        if(res.ok){
          M.toast({
            html: "Producto modificado exitosamente",
            classes: "#388e3c green darken-2",
          });
          history.push("/admin");
        }else{
          M.toast({ html: res.statusText, classes: "#c62828 red darken-3" });
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const eliminarImagen = (item) => {
    let newUrls = url
    var i = newUrls.indexOf( item );
    newUrls.splice( i, 1 );
    setUrl(newUrls)
    setpostear(true)  
  }
  const nosePuedeEliminarImagen = () =>{
    return(
      M.toast({
        html: "No se puede borrar esta imagen, debe haber por lo menos una imagen por producto",
        classes: "#388e3c red darken-2",
      })
    )
  }

  const listOfImages = () => {
      
    if(url){
      const list = url.map((elem)=> {
        return (
          <li>
          <div className="col s1" id='colCard'>
            <div className="card" id='cardDeleteUP'>
              <div className="card-image">
                <img src={elem}/>
                <a onClick={()=> {
                  url.length === 1 ?
                  nosePuedeEliminarImagen()
                  :
                  eliminarImagen(elem)

                }
                } className="btn-floating halfway-fab waves-effect waves-light red"><i className="material-icons">delete</i></a>
              </div>
            </div>
            </div>
            </li>
          )
      })
      return(    
        <ul>    
          <div className='row'>
              {list}
          </div>
        </ul>
      )
      }

  }

  //console.log(product)
  return (
            <div className='col s8'>
            <div className="row">
          <form className="col s12">
        <div className="row">
              <div className="input-field col s6">
              <input id="ItemName" onChange={(e) => setitemName(e.target.value)} type="text" className="validate" value={itemName}/>
               <label className="active" for="ItemName">Nombre del producto</label>
              </div>
        </div>
        <div className="row">
          <div className="input-field col s12">
              <input id="description" onChange={(e) => setdescription(e.target.value)} type="text" className="validate" value={description}/>
               <label className="active" for="description">Descripción</label>
          </div>
        </div>
        <div className="row">
          <div className="input-field col s12">
              <input id="Stock" type="number" onChange={(e) => setstock(e.target.value)} className="validate" value={stock}/>
               <label className="active" for="Stock">Stock</label>
          </div>
        </div>
        <div className="row">
          <div className="input-field col s12">
              <input id="ItemPrice" type="number" onChange={(e) => setitemPrice(e.target.value)} className="validate" value={itemPrice}/> 
               <label className="active" for="ItemPrice">Precio</label>
          </div>
        </div>
        <div className="row">
          <div className="input-field col s12">
              <input id="PromotionalPrice" type="number" onChange={(e) => setpromotionalPrice(e.target.value)} className="validate" value={promotionalPrice}/> 
               <label className="active" for="PromotionalPrice">Precio promocional</label>
          </div>
        </div>
        <div className="row">
          <div className="input-field col s2">
              <input id="PromotionalPrice" type="number" onChange={(e) => setlongitud(e.target.value)} className="validate" value={longitud}/> 
               <label className="active" for="PromotionalPrice">Longitud (cm)</label>
          </div>
          <div className="input-field col s2">
              <input id="PromotionalPrice" type="number" onChange={(e) => setancho(e.target.value)} className="validate" value={ancho}/> 
               <label className="active" for="PromotionalPrice">Ancho (cm)</label>
          </div>
          <div className="input-field col s2">
              <input id="PromotionalPrice" type="number" onChange={(e) => setalto(e.target.value)} className="validate" value={alto}/> 
               <label className="active" for="PromotionalPrice">Alto (cm)</label>
          </div>
          <div className="input-field col s2">
              <input id="PromotionalPrice" type="number" onChange={(e) => setpesoGr(e.target.value)} className="validate" value={pesoGr}/> 
               <label className="active" for="PromotionalPrice">Peso (gramos)</label>
          </div>
        </div>
        <form action="#">
          <div className="file-field input-field">
            <div className="btn" id='buttonUploadImages'>
              <span>Cargar Imagen</span>
              <input type="file" onChange={(e) => {
                
                setimages(e.target.files)
                setSubir(true)
                console.log(images)
                }} multiple/>
            </div>
            <div className="file-path-wrapper">
              <input className="file-path validate" type="text" value={typeof images === 'string' ? images : url }/>
            </div>
          </div>
        </form>
        <div className="row">
          <div className="col s12">
            <div>
              {listOfImages()}
            </div>
                 
                <a  onClick={() => {
                  if(subir){
                    console.log("ENTRE AL SUBIR VERDADERO")
                    agregarProducto();
                    setpostear(true)  
                  }else{
                    console.log("ENTRE AL SUBIR FALSO")
                    postearUpdate()
                  }
                    //  postearUpdate()
                  }
                  }
                className="waves-effect waves-light red lighten-2 btn-large" id="butonSubmit">Modificar Producto</a>
              
          </div>
        </div>
      </form>
      </div>
            </div>
  );
};
export default UpdateProductoForm;
