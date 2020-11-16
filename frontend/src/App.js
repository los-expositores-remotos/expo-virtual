import React from 'react';
import {BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import Home from './components/routes/Home.js'
import Suppliers from './components/routes/Suppliers.js'
import Register from './components/routes/Register.js'
import Login from './components/routes/Login.js'
import Live from './components/routes/Live.js'
import Faqs from './components/routes/Faqs.js'
import Howtobuy from './components/routes/Howtobuy.js'
import Aboutus from './components/routes/Aboutus.js'
import Contact from './components/routes/Contact.js'
import Admin from './components/routes/Admin.js';
import NavBar from './components/NavBar'
import Footer from './components/Footer'
import AddBanner from './components/AdminPanel/AddBanner'
import AddProveedor from './components/AdminPanel/AddProveedor'
import DeleteBanner from './components/AdminPanel/DeleteBanner'
import DeleteProducto from './components/AdminPanel/DeleteProducto'
import DeleteProveedor from './components/AdminPanel/DeleteProveedor'
import ScreenSelecEmpresaParaAgregarProduct from './components/AdminPanel/ScreenSelecEmpresaParaAgregarProduct'
import UpdateProducto from './components/AdminPanel/UpdateProducto'
import UpdateProveedor from './components/AdminPanel/UpdateProveedor'
import ResultSearch from './components/ResultSearch.js';
import ShoppingCart from './components/ShoppingCart.js';



//modificacion
const Routing = () => {
  return (
      <div>
        <Switch>
            <Route exact path="/register" component={Register}>
              <Register />
            </Route>
            <Route exact path="/login" component={Login}>
              <Login />
            </Route>
            <Route exact path="/" component={Home}>
              <Home />
            </Route>
            <Route path="/suppliers" component={Suppliers}>
              <Suppliers />
            </Route>
            <Route path="/live" component={Live}>
              <Live />
            </Route>
            <Route path="/faqs" component={Faqs}>
              <Faqs />
            </Route>
            <Route path="/howtobuy" component={Howtobuy}>
              <Howtobuy />
            </Route>
            <Route path="/aboutus" component={Aboutus}>
              <Aboutus />
            </Route>
            <Route path="/contact" component={Contact}>
              <Contact />
            </Route>
            <Route path="/admin" exact component={Admin}>
              <Admin />
            </Route>
            <Route path="/admin/agregarproveedor" component={AddProveedor}>
              <AddProveedor />
            </Route>
            <Route path="/admin/borrarproveedor" component={DeleteProveedor}>
              <DeleteProveedor />
            </Route>
            <Route path="/admin/modificarproveedor" component={UpdateProveedor}>
              <UpdateProveedor />
            </Route>
            <Route path="/admin/agregarproducto" component={ScreenSelecEmpresaParaAgregarProduct}>
              <ScreenSelecEmpresaParaAgregarProduct />
            </Route>
            <Route path="/admin/modificarproducto" component={UpdateProducto}>
              <UpdateProducto />
            </Route>
            <Route path="/admin/borrarproducto" component={DeleteProducto}>
              <DeleteProducto />
            </Route>
            <Route path="/admin/agregarbanner" component={AddBanner}>
              <AddBanner />
            </Route>
            <Route path="/admin/borrarbanner" component={DeleteBanner}>
              <DeleteBanner />
            </Route>
            <Route path="/resultsearch/:textsearch" component={ResultSearch}>
              <ResultSearch />
            </Route>
            <Route path="/shoppingcart" component={ShoppingCart}>
              <ShoppingCart />
            </Route>
        </Switch>
      </div>
   
  );
};


function App() {
  return (
    <Router>
        <NavBar />
          <Routing/>
        <Footer />
    </Router>
  );
}

export default App;
