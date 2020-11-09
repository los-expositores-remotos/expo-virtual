import React from "react";
import {useEffect, useState} from "react";
import { useHistory } from "react-router-dom";
import M from 'materialize-css'

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.autocomplete');
  var instances = M.Autocomplete.init(elems, {});
});

const UpdateProductoForm = (props)  => {
  const history = useHistory();
  const product = props.product
  const [url, setUrl] = useState(null);
  const [itemName, setitemName] = useState(product.itemName )
  const [images, setimages] = useState(product.images[0])
  const [description, setdescription] = useState(product.description)
  const [stock, setstock] = useState(product.stock)
  const [itemPrice, setitemPrice] = useState(product.itemPrice)
  const [promotionalPrice, setpromotionalPrice] = useState(product.promotionalPrice)


  useEffect(() => {
    if (url) {
      postearUpdate();
    }
  },[url]);

  const agregarProducto = () => {
    if(SubirAlaNube()){
    const data = new FormData();
    data.append("file", images);
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
      setUrl(images)
    }
  };
    

  const SubirAlaNube = () => {
    return (! typeof images === 'string')
  }
  const postProductImage = () =>{
    if(SubirAlaNube()){
      return url
    }else{
      return images
    }
  }
  
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
        "images": [url] ,
        "stock": stock,
        "itemPrice": itemPrice,
        "promotionalPrice": promotionalPrice
      }
      )
    }
    ) 
      .then((res) => res.json())
      .then((data) => {
        if (data.error) {
          M.toast({ html: data.error, classes: "#c62828 red darken-3" });
        } else {
          M.toast({
            html: "Producto modificado exitosamente",
            classes: "#388e3c green darken-2",
          });
        }
      })
      .catch((err) => {
        console.log(err);
      });
    
  };

  console.log(product)
  return (
        <div class="row">
          <form class="col s12">
        <div class="row">
              <div class="input-field col s6">
              <input id="ItemName" onChange={(e) => setitemName(e.target.value)} type="text" class="validate" value={itemName}/>
               <label class="active" for="ItemName">Nombre del producto</label>
              </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
              <input id="description" onChange={(e) => setdescription(e.target.value)} type="text" class="validate" value={description}/>
               <label class="active" for="description">Descripción</label>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
              <input id="Stock" type="number" onChange={(e) => setstock(e.target.value)} class="validate" value={stock}/>
               <label class="active" for="Stock">Stock</label>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
              <input id="ItemPrice" type="number" onChange={(e) => setitemPrice(e.target.value)} class="validate" value={itemPrice}/> 
               <label class="active" for="ItemPrice">Precio</label>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
              <input id="PromotionalPrice" type="number" onChange={(e) => setpromotionalPrice(e.target.value)} class="validate" value={promotionalPrice}/> 
               <label class="active" for="PromotionalPrice">Precio promocional</label>
          </div>
        </div>
        <form action="#">
          <div class="file-field input-field">
            <div class="btn" id='buttonUploadImages'>
              <span>Cargar Imagen</span>
              <input type="file" onChange={(e) => {
                setimages(e.target.files[0])
                console.log(images)
                }}/>
            </div>
            <div class="file-path-wrapper">
              <input class="file-path validate" type="text" value={typeof images === 'string' ? images : url }/>
            </div>
          </div>
        </form>
        <div class="row">
          <div class="col s12">
                 
                <a  onClick={() => {
                  console.log(images)
                  console.log(url)
                  console.log(images === url)
              
                    agregarProducto();
                    if (!itemName ||
                      !images ||
                      !description ||
                      !stock||
                      !promotionalPrice||
                      !itemPrice) {
                      postearUpdate()
                  }
                  }
                } 
                class="waves-effect waves-light red lighten-2 btn-large" id="butonSubmit">Modificar Producto</a>
              
          </div>
        </div>
      </form>
      </div>
  );
};
export default UpdateProductoForm;
