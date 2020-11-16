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
  const longitud = product.images.length
  const [subir, setSubir] = useState(false)
  const [postear, setpostear] = useState(false)
  const history = useHistory()

  useEffect(() => {
if (postear) {
      postearUpdate();
    }
  });

  const agregarProducto = () => {
    if(subir){
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
              //console.log(data);
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
          history.push("/admin");
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
                  eliminarImagen(elem)
                  }} className="btn-floating halfway-fab waves-effect waves-light red"><i className="material-icons">delete</i></a>
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
                  console.log(images)
                  console.log(url)
                  console.log(images === url)
                    setpostear(true)
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
                className="waves-effect waves-light red lighten-2 btn-large" id="butonSubmit">Modificar Producto</a>
              
          </div>
        </div>
      </form>
      </div>
            </div>
  );
};
export default UpdateProductoForm;
