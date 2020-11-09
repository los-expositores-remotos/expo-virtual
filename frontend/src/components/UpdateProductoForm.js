import React from "react";
import {useEffect, useState} from "react";
import M from 'materialize-css'

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.autocomplete');
  var instances = M.Autocomplete.init(elems, {});
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
  const longitud = product.images.length
  const [subir, setSubir] = useState(false)
  const [postear, setpostear] = useState(false)

  useEffect(() => {
    console.log(url)
    console.log(url.length)
    console.log(longitud)
    if (postear) {
      postearUpdate();
    }
  },[postear]);

  const agregarProducto = () => {

    console.log(images)
    if(subir){
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
        "itemPrice": itemPrice,
        "promotionalPrice": promotionalPrice
      })
    })
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
  const listOfImages = () => {
      
    if(url){
      
      const list = url.map((elem)=> {
        return (
          <li>
          <div class="col s1" id='colCard'>
            <div class="card" id='cardDelete'>
              <div class="card-image">
                <img src={elem}/>
                <a onClick={()=> {
                  console.log("elimino la imagen")
                  }} class="btn-floating halfway-fab waves-effect waves-light red"><i class="material-icons">delete</i></a>
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
                
                setimages(e.target.files)
                setSubir(true)
                console.log(images)
                }} multiple/>
            </div>
            <div class="file-path-wrapper">
              <input class="file-path validate" type="text" value={typeof images === 'string' ? images : url }/>
            </div>
          </div>
        </form>
        <div class="row">
          <div class="col s12">
            <div>
              {listOfImages()}
            </div>
                 
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
