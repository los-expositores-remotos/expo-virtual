import React, { useEffect, useState } from 'react';
import '../css/index.css';
import $ from 'jquery'
import { useHistory } from "react-router-dom";
import imagen from '../img/product.png'
import M from 'materialize-css'

document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.autocomplete');
  M.Autocomplete.init(elems, {});
});


const TestForm = () => {
  const history = useHistory();
   //REPLACE WITH YOUR PUBLIC KEY AVAILABLE IN: https://developers.mercadopago.com/panel/credentials
window.Mercadopago.setPublishableKey("TEST-147fd98d-a235-429b-aa09-a5b157a1fe61");
window.Mercadopago.getIdentificationTypes();

const [quantity, setQuantity] = useState(1);
const unitPrice = useState(10);
const [amount, setAmount] = useState(10);
const description = useState("Some book");
const [cardNumber, setCardNumber] = useState("");
const [paymentmethod, setpaymentmethod] = useState(null);
const [paymentmethodId, setpaymentmethodId] = useState(null);
const [paymentmethodThumbnail, setpaymentmethodThumbnail] = useState("");
const [cartTotal, setCartTotal] = useState("$ 10");
const [email, setEmail] = useState("");
const [docType, setDocType] = useState(null);

const [token, setToken] = useState(null);

function guessPaymentMethod(event) {
    cleanCardInfo();
    setCardNumber(event.target.value);

    console.log(cardNumber)
    if (cardNumber.length >= 6) {
        let bin = cardNumber.substring(0,6);
        window.Mercadopago.getPaymentMethod({
            "bin": bin
        }, setPaymentMethod);
    }
};

function setPaymentMethod(status, response) {
    if (status === 200) {
        setpaymentmethod(response[0]);
        setpaymentmethodId(response[0].id);
        setpaymentmethodThumbnail('url('+response[0].thumbnail+')');        
        if(response[0].additional_info_needed.includes("issuer_id")){
            getIssuers(response[0].id);

        } else {
            document.getElementById('issuerInput').classList.add("hidden");

            getInstallments(
              response[0].id,
                document.getElementById('amount').value
            );
        }

    } else {
        alert(`payment method info error: ${response}`);
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
        alert(`installments method info error: ${response}`);
    }
}  

//Update offered installments when issuer changes
function updateInstallmentsForIssuer(event) {
    window.Mercadopago.getInstallments({
        "payment_method_id": document.getElementById('paymentMethodId').value,
        "amount": parseFloat(document.getElementById('amount').value),
        "issuer_id": parseInt(document.getElementById('issuer').value)
    }, setInstallments);
}

//Proceed with payment
var doSubmit = false;
function getCardToken(event){
    event.preventDefault();
    if(!doSubmit){
        let $form = document.getElementById('paymentForm');
        window.Mercadopago.createToken($form, setCardTokenAndPay);

        return false;
    }
};

const myAwesomeFunction = {
  backgroundImage: paymentmethodThumbnail,
};



function setCardTokenAndPay(status, response) {
    if (status === 200 || status === 201) {
        // let form = document.getElementById('paymentForm');
        // let card = document.createElement('input');
        // card.setAttribute('name', 'token');
        // card.setAttribute('type', 'hidden');
        // card.setAttribute('value', response.id);
        setToken(response.id)
        console.log(response.id)

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

    document.getElementById('cardNumber').style.backgroundImage = '';
    document.getElementById('issuerInput').classList.add("hidden");
    document.getElementById('issuer').options.length = 0;
    document.getElementById('installments').options.length = 0;
}

//Handle transitions
function checkoutShoppingCart(){ 
  $('.shopping-cart').fadeOut(500);
  setTimeout(() => { $('.container_payment').show(500).fadeIn(); }, 500);
}

function goBackContainerPayment(){ 
  $('.container_payment').fadeOut(500);
  setTimeout(() => { $('.shopping-cart').show(500).fadeIn(); }, 500);
}

//Handle price update
function updatePrice(value){
  if(parseInt(value) >= 1){
    setQuantity(value);
    setAmount(parseInt(unitPrice) * parseInt(value));
    setCartTotal('$ ' + (parseInt(unitPrice) * parseInt(value)));
    console.log("Quionda"+(parseInt(unitPrice) * parseInt(value)))
  }else{
    setQuantity(1);
    setAmount(parseInt(unitPrice));
    setCartTotal('$ ' + (parseInt(unitPrice)));
  }
};
// const [quantity, setQuantity] = useState(1);
// const unitPrice = useState(10);
// const [amount, setAmount] = useState(10);
// const description = useState("Some book");
// const [cardNumber, setCardNumber] = useState("");
// const [paymentmethod, setpaymentmethod] = useState(null);
// const [paymentmethodId, setpaymentmethodId] = useState(null);
// const [paymentmethodThumbnail, setpaymentmethodThumbnail] = useState("");
// const [cartTotal, setCartTotal] = useState("$ 10");
// const [email, setEmail] = useState("");
// const [docType, setDocType] = useState(null);
const postearPago = (tokenString) => {
  if(true){//cartTotal && unitPrice && email && description && amount && quantity && token){
  fetch("http://localhost:7000/process_payment/", {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify({
      "token": tokenString,
      "cartTotal": cartTotal,
      "unitPrice": unitPrice,
      "email": email,
      "description": description,
      "amount": amount,
      "quantity": quantity
    })
  })
    .then((res) => res.json())
    .then((data) => {
      //console.log(data)
      if (data.error) {
        M.toast({ html: data.error, classes: "#c62828 red darken-3" });
      } else {
        M.toast({
          html: "Transacción iniciada correctamente",
          classes: "#388e3c green darken-2",
        });
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

  return (
<div>
      <section class="shopping-cart dark">
        <div class="container" id="container">
          <div class="block-heading">
            <h2>Shopping Cart</h2>
            <p>This is an example of a Mercado Pago integration</p>
          </div>
          <div class="content">
            <div class="row">
              <div class="col-md-12 col-lg-8">
                <div class="items">
                  <div class="product">
                    <div class="info">
                      <div class="product-details">
                        <div class="row justify-content-md-center">
                          <div class="col-md-3">
                            <img class="img-fluid mx-auto d-block image" src={imagen}/>
                          </div>
                          <div class="col-md-4 product-detail">
                            <h5>Product</h5>
                            <div class="product-info">
                              <p><b>Description: </b><span id="product-description">{description}</span><br/>
                              <b>Author: </b>Dale Carnegie<br/>
                              <b>Number of pages: </b>336<br/>
                              <b>Price:</b> $ <span id="unit-price" >{unitPrice}</span></p>
                            </div>
                          </div>
                          <div class="col-md-3 product-detail">
                            <label for="quantity"><h5>Quantity</h5></label>
                            <input type="number" id="quantity" value = {quantity} class="form-control" onChange={(e) => updatePrice(e.target.value)}/>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-md-12 col-lg-4">
                <div class="summary">
                  <h3>Cart</h3>
                  <div class="summary-item"><span class="text">Subtotal</span><span class="price" id="cart-total" >{cartTotal}</span></div>
                  <button class="btn btn-primary btn-lg btn-block" id="checkout-btn" onClick={checkoutShoppingCart}>Checkout</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
      <section class="payment-form dark">
        <div class="container_payment">
          <div class="block-heading">
            <h2>Card Payment</h2>
            <p>This is an example of a Mercado Pago integration</p>
          </div>
          <div class="form-payment">
            <div class="products">
              <h2 class="title">Summary</h2>
              <div class="item">
                <span class="price" id="summary-price" value={'$ ' + unitPrice}></span>
                <p class="item-name">Book x <span id="summary-quantity" value={quantity}></span></p>
              </div>
              <div class="total">Total<span class="price" id="summary-total" >{cartTotal}</span></div>
            </div>
            <div class="payment-details">
              <form action="#" id="paymentForm">
                  <h3 class="title">Buyer Details</h3>
                  <div class="row">
                    <div class="form-group col">
                      <label for="email">E-Mail</label>
                      <input id="email" name="email" type="text" class="form-control" onChange={(e) => setEmail(e.target.value)}/>
                    </div>
                  </div>
                  <div class="row">
                    <div class="form-group col-sm-5">
                      <label for="docType">Document Type</label>
                      <select id="docType" name="docType" data-checkout="docType" type="text" class="form-control"></select>
                    </div>
                    <div class="form-group col-sm-7">
                      <label for="docNumber">Document Number</label>
                      <input id="docNumber" name="docNumber" data-checkout="docNumber" type="text" class="form-control"/>
                    </div>
                  </div>
                  <br/>
                  <h3 class="title">Card Details</h3>
                  <div class="row">
                    <div class="form-group col-sm-8">
                      <label for="cardholderName">Card Holder</label>
                      <input id="cardholderName" data-checkout="cardholderName" type="text" class="form-control"/>
                    </div>
                    <div class="form-group col-sm-4">
                      <label for="">Expiration Date</label>
                      <div class="input-group expiration-date">
                        <input type="text" class="form-control" placeholder="MM" id="cardExpirationMonth" data-checkout="cardExpirationMonth"
                          onselectstart="return false" onpaste="return false" onCopy="return false" onCut="return false" onDrag="return false" onDrop="return false" autocomplete='off'/>
                        <span class="date-separator">/</span>
                        <input type="text" class="form-control" placeholder="YY" id="cardExpirationYear" data-checkout="cardExpirationYear"
                          onselectstart="return false" onpaste="return false" onCopy="return false" onCut="return false" onDrag="return false" onDrop="return false" autocomplete='off'/>
                      </div>
                    </div>
                    <div class="form-group col-sm-8">
                      <label for="cardNumber">Card Number</label>
                      <input type="text" class="form-control input-background" id="cardNumber" data-checkout="cardNumber"
                        onselectstart="return false" onpaste="return false" onCopy="return false" onCut="return false" onDrag="return false" onDrop="return false" style={myAwesomeFunction} autocomplete='off' onChange={guessPaymentMethod}/>
                    </div>
                    <div class="form-group col-sm-4">
                      <label for="securityCode">CVV</label>
                      <input id="securityCode" data-checkout="securityCode" type="text" class="form-control"
                        onselectstart="return false" onpaste="return false" onCopy="return false" onCut="return false" onDrag="return false" onDrop="return false" autocomplete='off'/>
                    </div>
                    <div id="issuerInput" class="form-group col-sm-12 hidden">
                      <label for="issuer">Issuer</label>
                      <select id="issuer" name="issuer" data-checkout="issuer" class="form-control" onChange={updateInstallmentsForIssuer}></select>
                    </div>
                    <div class="form-group col-sm-12">
                      <label for="installments">Installments</label>
                      <select type="text" id="installments" name="installments" class="form-control"></select>
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
                      {/* <button class="btn btn-primary btn-block" onSubmit={getCardToken}>Pay</button> */}
                      <br/>
                      <a id="go-back" onClick={goBackContainerPayment}>
                        <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 10 10" class="chevron-left">
                          <path fill="#009EE3" fill-rule="nonzero"id="chevron_left" d="M7.05 1.4L6.2.552 1.756 4.997l4.449 4.448.849-.848-3.6-3.6z"></path>
                        </svg>
                        Go back to Shopping Cart
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