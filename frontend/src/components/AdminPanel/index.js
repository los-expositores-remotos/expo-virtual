import React from 'react';
import {useEffect, useState, useContext} from "react";
import '../../client/css/index.css';
import $ from 'jquery'
import { useHistory } from "react-router-dom";
import M from 'materialize-css'
// import MercadoPagoProducto from '../../components/MercadoPagoProducto';
import ShopContext from '../../components/context/shop-context'
import {precioTotal, sendMethodCostTopLevel, sendMethodNameTopLevel} from '../../components/ShoppingCart'

// document.addEventListener('DOMContentLoaded', function() {
//   var elems = document.querySelectorAll('.autocomplete');
//   M.Autocomplete.init(elems, {});
// });


const TestForm = () => {
  const history = useHistory();
//    //REPLACE WITH YOUR PUBLIC KEY AVAILABLE IN: https://developers.mercadopago.com/panel/credentials
window.Mercadopago.setPublishableKey("TEST-5429f1b7-5db8-46fd-bf95-26ae8fcd39fe");
// window.Mercadopago.getIdentificationTypes();
const context = useContext(ShopContext);
const [quantity, setQuantity] = useState(1);
const [unitPrice] = useState(10);
const [amount] = useState(precioTotal(context.cart) + sendMethodCostTopLevel);
const [description] = useState("Some book");
const [cardNumber, setCardNumber] = useState("");
const [paymentmethod, setpaymentmethod] = useState(null);
const [paymentmethodId, setpaymentmethodId] = useState(null);
let installments = null;
const [paymentmethodThumbnail, setpaymentmethodThumbnail] = useState("");
const [paymentmethodThumbnailStyle, setpaymentmethodThumbnailStyle] = useState({backgroundImage:null,});
const [cartTotal, setCartTotal] = useState("$ 10");
const [email, setEmail] = useState("");
const [docTypes, setDocTypes] = useState([])

// const [token, setToken] = useState(null);
// const [docTypes, setDocTypes] = useState(null);

// useEffect(() => {
//   if(!docTypes){
//     fetch("https://api.mercadopago.com/v1/identification_types?public_key=TEST-147fd98d-a235-429b-aa09-a5b157a1fe61", {
//       headers: {
//         "Content-type": "application/json",
//       }
//       }).then((res) => {
//         console.log(res)
//         if (res.ok) {
//           return res.json()
//         }
//       }).then((response)=>{
//         var hola = document.createElement("option")
//       hola.text = "hola"
//       document.getElementById("docType").add(hola)
//       var hola = document.createElement("option")
//       hola.text = "hola1"
//       // document.getElementById("docType").add(hola)
//         // console.log("gggfgfgfg"+response)
//         setDocTypes(response)
//         console.log(docTypes)
//       }, [docTypes])
//   }
// })

function guessPaymentMethod(event) {
    cleanCardInfo();
    setCardNumber(event.target.value);

    console.log(event.target.value)
    if (event.target.value.length >= 6) {
        let bin = event.target.value.substring(0,6);
      
        window.Mercadopago.getPaymentMethod({
            "bin": bin
        }, setPaymentMethod);
      }
        
};

function setPaymentMethod(status, response) {
    if (status === 200) {
      console.log(paymentmethodThumbnailStyle)  
        setpaymentmethod(response[0]);
        setpaymentmethodId(response[0].id);
        setpaymentmethodThumbnail('url(' + response[0].thumbnail + ')');
        setpaymentmethodThumbnailStyle({backgroundImage:'url(' + response[0].thumbnail + ')',});
        console.log(paymentmethodThumbnailStyle)        
        if(response[0].additional_info_needed.includes("issuer_id")){
            getIssuers(response[0].id);

        } else {
            document.getElementById('issuerInput').classList.add("hidden");

            getInstallments(
              response[0].id,
                document.getElementById('amount').value
            );
        }

    }
}

function getIssuers(paymentMethodId) {
    window.Mercadopago.getIssuers(
        paymentMethodId, 
        setIssuers
    );
}

function setIssuers(status, response) {
    if (status === 200) {
        let issuerSelect = document.getElementById('issuer');

        response.forEach( issuer => {
            let opt = document.createElement('option');
            opt.text = issuer.name;
            opt.value = issuer.id;
            issuerSelect.appendChild(opt);
        });
        
        if(issuerSelect.options.length <= 1){
            document.getElementById('issuerInput').classList.add("hidden");
        } else {
            document.getElementById('issuerInput').classList.remove("hidden");
        }
        
        getInstallments(
            document.getElementById('paymentMethodId').value,
            document.getElementById('amount').value,
            issuerSelect.value
        );

    } else {
        alert(`issuers method info error: ${response}`);
    }
}

function getInstallments(paymentMethodId, amount, issuerId){
    window.Mercadopago.getInstallments({
        "payment_method_id": paymentMethodId,
        "amount": parseFloat(amount),
        "issuer_id": issuerId ? parseInt(issuerId) : undefined
    }, setInstallments);
}

function setInstallments(status, response){
    if (status === 200) {
        document.getElementById('installments').options.length = 0;
        response[0].payer_costs.forEach( payerCost => {
            let opt = document.createElement('option');
            opt.text = payerCost.recommended_message;
            opt.value = payerCost.installments;
            document.getElementById('installments').appendChild(opt);
        });
    } else {
        // alert(`installments method info error: ${response}`);
    }
}  

// //Update offered installments when issuer changes
function updateInstallmentsForIssuer(event) {
    window.Mercadopago.getInstallments({
        "payment_method_id": document.getElementById('paymentMethodId').value,
        "amount": parseFloat(document.getElementById('amount').value),
        "issuer_id": parseInt(document.getElementById('issuer').value)
    }, setInstallments);
}

// //Proceed with payment
var doSubmit = false;
function getCardToken(event){
    event.preventDefault();
    if(!doSubmit){
        let $form = document.getElementById('paymentForm');
        window.Mercadopago.createToken($form, setCardTokenAndPay);

        return false;
    }
};

const updatePaymentMethodThumbnail = {
  backgroundImage: paymentmethodThumbnail,
};



function setCardTokenAndPay(status, response) {
    if (status === 200 || status === 201) {
        // let form = document.getElementById('paymentForm');
        // let card = document.createElement('input');
        // card.setAttribute('name', 'token');
        // card.setAttribute('type', 'hidden');
        // card.setAttribute('value', response.id);
        // setToken(response.id)
        // form.appendChild(card);
        doSubmit=true;
        // form.submit(); //Submit form data to your backend
        postearPago(response.id)
    } else {
        alert("Verify filled data!\n"+JSON.stringify(response, null, 4));
    }
};

/***
 * UX functions 
 */


function cleanCardInfo() {

  setpaymentmethodThumbnailStyle({backgroundImage:"",})
    document.getElementById('issuerInput').classList.add("hidden");
    document.getElementById('issuer').options.length = 0;
    document.getElementById('installments').options.length = 0;
}

// //Handle transitions
function checkoutShoppingCart(){ 
  $('.shopping-cart').fadeOut(500);
  setTimeout(() => { $('.container_payment').show(500).fadeIn(); }, 500);
}

function goBackContainerPayment(){ 
  $('.container_payment').fadeOut(500);
  setTimeout(() => { $('.shopping-cart').show(500).fadeIn(); }, 500);
}

const postearPago = (tokenString) => {
  console.log(installments)
  if(cartTotal && unitPrice && email && description && amount && quantity){//cartTotal && unitPrice && email && description && amount && quantity && token){
  fetch("http://localhost:7000/process_payment/", {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify({
      "token": tokenString,
      "unitPrice": unitPrice,
      "email": email,
      "description": description,
      "amount": precioTotal(context.cart) + sendMethodCostTopLevel,
      "quantity": quantity,
      "paymentMethodId": paymentmethodId,
      "installments": document.getElementById("installments").value,
      "issuerId": document.getElementById("issuer").value,
      "docType": document.getElementById("docType").value,
      "docNumber": document.getElementById("docNumber").value
    })
  })
    .then((res) =>{
      window.Mercadopago.clearSession()
      if (!res.ok) {
        console.log("ohboyy")
        console.log(res)        
        M.toast({ html: "Error inesperado", classes: "#c62828 red darken-3" });
      } else {
        console.log("realizandoPAGO")
        let estadoPago = res
        console.log(estadoPago.json)
        M.toast({ html: "Transacción iniciada correctamente", classes: "#388e3c green darken-2" });
        M.toast({ html: "Transaccion "+ estadoPago, classes: "#c62828 red darken-3" });
        actualizarBaseDeDatos()
        vacioCarrito()
        history.push("/");
      }
    })
    .catch((err) => {
      console.log(err);
    });
  }else{
    M.toast({ html: "Llenar todos los campos", classes: "#c62828 red darken-3" });
  }
};


const actualizarBaseDeDatos = () => {
  fetch("http://localhost:7000/productSales", {
    method: "PUT",
    headers: {
      "Content-type": "application/json",
      "Authorization": localStorage.getItem("tokenValido")
    },
    body: JSON.stringify({
      "sales": listaProductosEnPares()
    })
  })
  .then((res) =>{
    console.log(res)
  })
  .catch((error) => {
    console.log(error);
  })
};

const listaProductosEnPares = () => {
  let arrayResultado = []
  context.cart.forEach(
    product => {
      console.log("Pase por (" + product.id + ", " + product.quantity + ")")
      arrayResultado.push({"idProducto": product.id, "cantidadVendida": product.quantity})
    }
  )
  console.log(arrayResultado)
  return arrayResultado
}

const vacioCarrito = () => {
  context.cart.forEach(
    product => {
      context.deleteProductFromCart(product.id)
    }
  )
}
  
const selectDocTypes = () =>{
   if(docTypes.length === 0){
    fetch("https://api.mercadopago.com/v1/identification_types?public_key=TEST-147fd98d-a235-429b-aa09-a5b157a1fe61", {
    headers: {
      "Content-type": "application/json",
    }
    }).then((res) => {
      if (res.ok) {
        return res.json()
      }
    }).then((response)=>{
      setDocTypes(response)
    })
  }
  return(<select id="docType" name="docType" data-checkout="docType" class="form-control select-visible">
    {docTypes.map((opt => {
      return(<option value={opt.id}>{opt.name}</option>)
    }))}
  </select>)
  }

  // function select(){
  //   return(
  //     // <select  id="Category" form="bannerform" type="text" class="validate">
  //     //   {list.map(opt => {
        
          
          
  //       // })}
  //     // </select>
  //   )




  return (
 
    <div>
      {checkoutShoppingCart()}
      <section class="payment-form dark">
      
      <div class="container_payment">
          <div class="block-heading">
             <h2>Pago con tarjeta</h2>
           </div>
           <div class="form-payment">
             <div class="products">
               <h2 class="title">Resumen</h2>
               <div class="item">
                 <span class="price" id="summary-price" value={'$ ' + unitPrice}></span>
                 {context.cart.map(product => {
                  return(
                    <div className="row">
                      <div className="col s6">
                        <p class="item-name">{product.itemName} <span id="summary-quantity" value={quantity}>x {product.quantity}</span></p>
                      </div>
                      <div className="col s6">
                        <p class="item-name item-total-price">$ {product.itemPrice * product.quantity}</p>
                      </div>
                    </div>
                  )
                })}
                <div className="row">
                  <div className="col s6">
                  <p class="item-name">{sendMethodNameTopLevel} <span id="summary-quantity"></span></p>
                  </div>
                  <div className="col s6">
                    <p class="item-name item-total-price">$ {sendMethodCostTopLevel}</p>
                  </div>
                </div>
              </div>
              <div class="total">Total<span class="price" id="summary-total" >$ {precioTotal(context.cart) + sendMethodCostTopLevel}</span></div>
            </div>
            <div class="payment-details">
               <form action="#" id="paymentForm">
                   <h3 class="title">Datos del comprador</h3>
                   <div class="row">
                     <div class="form-group col-sm-5">
                       <label for="email">E-Mail</label>
                       <input id="email" name="email" type="text" class="form-control" onChange={(e) => setEmail(e.target.value)}/>
                     </div>
                   </div>
                   <div class="row">
                     <div class="form-group col-sm-5">
                       <label for="docType">Tipo de documento</label>
                       {selectDocTypes()}
                     </div>
                     <div class="form-group col-sm-7">
                       <label for="docNumber">Numero de documento</label>
                       <input id="docNumber" name="docNumber" data-checkout="docNumber" type="text" class="form-control"/>
                     </div>
                   </div>
                   <br/>
                   <h3 class="title">Datos de la tarjeta</h3>
                   <div class="row">
                     <div class="form-group col-sm-8">
                       <label for="cardholderName">Titular de la tarjeta</label>
                       <input id="cardholderName" data-checkout="cardholderName" type="text" class="form-control"/>
                     </div>
                     <div class="col s12">
                       <label for="">Fecha de vencimiento</label>
                       <div class="input-group expiration-date">
                         <input type="text" class="form-control" placeholder="MM" id="cardExpirationMonth" data-checkout="cardExpirationMonth"
                          onselectstart="return false" onpaste="return false" onCopy="return false" onCut="return false" onDrag="return false" onDrop="return false" autocomplete='off'/>
                        <span class="date-separator">/</span>
                        <input type="text" class="form-control" placeholder="YY" id="cardExpirationYear" data-checkout="cardExpirationYear"
                          onselectstart="return false" onpaste="return false" onCopy="return false" onCut="return false" onDrag="return false" onDrop="return false" autocomplete='off'/>
                      </div>
                    </div>
                    <div class="form-group col-sm-8">
                      <label for="cardNumber">Número de tarjeta</label>
                      <input type="text" class="form-control input-background" id="cardNumber" data-checkout="cardNumber"
                        onselectstart="return false" onpaste="return false" onCopy="return false" onCut="return false" onDrag="return false" onDrop="return false" style={paymentmethodThumbnailStyle} autocomplete='off' onChange={guessPaymentMethod}/>
                    </div>
                    <div class="form-group col-sm-4">
                      <label for="securityCode">Código de seguridad</label>
                      <input id="securityCode" data-checkout="securityCode" type="text" class="form-control"
                        onselectstart="return false" onpaste="return false" onCopy="return false" onCut="return false" onDrag="return false" onDrop="return false" autocomplete='off'/>
                    </div>
                    <div id="issuerInput" class="form-group col-sm-12 hidden">
                      <label for="issuer">Entidad emisora</label>
                      <select id="issuer" name="issuer" data-checkout="issuer" class="form-control select-visible" onChange={updateInstallmentsForIssuer}></select>
                    </div>
                    <div class="form-group col-sm-12">
                      <label for="installments">Cuotas</label>
                      <select type="text" id="installments" name="installments" class="form-control select-visible"></select>
                    </div>
                    <div class="form-group col-sm-12">
                      <input type="hidden" name="transactionAmount" id="amount" value={amount}/>
                      <input type="hidden" name="paymentMethodId" id="paymentMethodId" value={paymentmethodId} />
                      <input type="hidden" name="description" id="description" value={description} />
                      <br/>
                      <div className="row">
           <div className="col s12">
                 <a onClick={getCardToken} className="waves-effect waves-light red lighten-2 btn-large" id="butonSubmit">Pagar</a>
           </div>
         </div>
                       <br/>
                       
                       <a id="go-back" href="/shoppingcart">
                         <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 10 10" class="chevron-left">
                           <path fill="#009EE3" fill-rule="nonzero"id="chevron_left" d="M7.05 1.4L6.2.552 1.756 4.997l4.449 4.448.849-.848-3.6-3.6z"></path>
                         </svg>
                         Volver al carrito
                       </a>
                    </div>
                  </div>
                </form>
              </div>
          </div>
        </div>
        
        </section>
        </div>
  );
}
export default TestForm;