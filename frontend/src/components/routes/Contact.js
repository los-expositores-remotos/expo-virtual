
import React, {useState} from 'react';
import emailjs from 'emailjs-com';
import "../../styles/Contact.css"

const Contact = () => {
   const frmContact = { userName:'', userEmail:'', userTel:'', message:''};
   const [contact,setContact] = useState(frmContact);
   const [showMessage, setShowMessage] = useState(false);
   
   const handleChange = e => { 
		const {name,value} = e.target;
		setContact({...contact,[name]:value}); 
   };

   const handleSubmit = e =>{
	    e.preventDefault();
	   
		emailjs.send('default_service','template_5min7yg', contact, 'user_K6I3hCUFvXbvNzXl38ypS')
		.then((response) => {
				   console.log('SUCCESS!', response.status, response.text);
				   setContact(frmContact);
				   setShowMessage(true);
		}, (err) => {
				   console.log('FAILED...', err);
		});
   }


  return (
    <div className="container pt-2 text-center">
		<div className="alert alert-light" role="alert">
		</div>
		{ showMessage ? <div className="alert alert-success col-md-5 mx-auto" role="alert">Email enviado, pronto te responderemos. Gracias por ser parte de Expo Arte Virtual!!</div> : ``}
		<form onSubmit={handleSubmit}>
			  <div className="pt-3"><h3 className="font-weight-bold"> Dejanos tu mensaje !! </h3></div>
			  <div class="input-field col s12">
					<div id="header" className="form-group text-left"> 
						<i class="material-icons prefix">account_circle</i>
						<input required type="text" value={contact.userName} name="userName" onChange={handleChange} className="form-control" placeholder="Tu nombre" />
						<label for="icon_prefix">Nombre</label>					
					</div>
			  </div>
			  <div class="input-field col s12">
					<div className="form-group text-left">
						<i class="material-icons prefix">email</i>
						<input required type="text" value={contact.userEmail} name="userEmail" onChange={handleChange} className="form-control" placeholder="Tu Email" />
						<label for="icon_telephone">Email</label>
					</div>
			  </div>
			  <div class="input-field col s12">
					<div className="form-group text-left"> 
						<i class="material-icons prefix">phone</i>
						<input value={contact.userTel} required type="text" name="userTel" onChange={handleChange}  className="form-control" placeholder="Tu Telefono" />
						<label for="icon_telephone">Telefono</label>
					</div>
			  </div>
			  <div class="input-field col s12">
					<div className="form-group text-left">
						<i class="material-icons prefix">mode_edit</i>
						<textarea id="icon_prefix2" required name="message" onChange={handleChange} className="form-control" placeholder="Dejanos tu mensaje" value={contact.message}></textarea>
						<label>Mensaje</label>
					</div>
			  </div>
			  <div className="pt-3 col-md-5 mx-auto text-left">
					<button id="contactBoton" className="waves-effect waves-light red lighten-2 btn-large">Enviar</button>
			  </div>
		</form>	
	</div>
  );
}

export default Contact;