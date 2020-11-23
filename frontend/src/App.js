import React, {createContext, useReducer, useContext} from 'react';
import {BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import Home from './components/routes/Home.js'
import Suppliers from './components/routes/Suppliers.js'
import Live from './components/routes/Live.js'
import Faqs from './components/routes/Faqs.js'
import Howtobuy from './components/routes/Howtobuy.js'
import TestForm from './components//AdminPanel/index.js'
import Aboutus from './components/routes/Aboutus.js'
import Contact from './components/routes/Contact.js'
import PageNotFound from './components/routes/PageNotFound.js'
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
import GlobalState from './components/context/GlobalState'
import Singin from './components/routes/Singin' 
import SinginAdmin from './components/routes/SinginAdmin' 
import Singup from './components/routes/Singup'
import { reducer, initialState } from "./components/context/UserReducer";

export const userContext = createContext();

//modificacion
const Routing = () => {
  const { state, dispatch } = useContext(userContext);
  console.log(state)

  return (
      <div>
        <Switch>
            <Route exact path="/testform" component={TestForm}>
              <TestForm />
            </Route>
            <Route exact path="/" component={Home}>
              <Home />
            </Route>
            <Route exact path="/suppliers" component={Suppliers}>
              <Suppliers />
            </Route>
            <Route exact path="/live" component={Live}>
              <Live />
            </Route>
            <Route exact path="/faqs" component={Faqs}>
              <Faqs />
            </Route>
            <Route exact path="/howtobuy" component={Howtobuy}>
              <Howtobuy />
            </Route>
            <Route exact path="/aboutus" component={Aboutus}>
              <Aboutus />
            </Route>
            <Route exact path="/contact" component={Contact}>
              <Contact />
            </Route>
            <Route exact path="/admin" component={ state === "admin" ? Admin : PageNotFound}>
              { state === "admin" ?   <Admin /> : <PageNotFound/>}
            </Route>
            <Route exact path="/admin/agregarproveedor" component={ state === "admin" ?    AddProveedor : PageNotFound }>
            { state === "admin" ?   <AddProveedor /> : <PageNotFound/>}
            </Route>
            <Route exact path="/admin/borrarproveedor" component={ state === "admin" ?    DeleteProveedor : PageNotFound}>
               { state === "admin" ?   <DeleteProveedor /> : <PageNotFound/>}
            </Route>
            <Route exact path="/admin/modificarproveedor" component={ state === "admin" ?    UpdateProveedor : PageNotFound}>
               { state === "admin" ?   <UpdateProveedor /> : <PageNotFound/>}
            </Route>
            <Route exact path="/admin/agregarproducto" component={ state === "admin" ?    ScreenSelecEmpresaParaAgregarProduct : PageNotFound}>
               { state === "admin" ?   <ScreenSelecEmpresaParaAgregarProduct /> : <PageNotFound/>}
            </Route>
            <Route path="/admin/modificarproducto" component={ state === "admin" ?    UpdateProducto : PageNotFound}>
               { state === "admin" ?   <UpdateProducto /> : <PageNotFound/>}
            </Route>
            <Route path="/admin/borrarproducto" component={ state === "admin" ?    DeleteProducto : PageNotFound}>
               { state === "admin" ?   <DeleteProducto /> : <PageNotFound/>}
            </Route>
            <Route path="/admin/agregarbanner" component={ state === "admin" ?    AddBanner : PageNotFound}>
               { state === "admin" ?   <AddBanner /> : <PageNotFound/>}
            </Route>
            <Route path="/admin/borrarbanner" component={ state === "admin" ?    DeleteBanner : PageNotFound}>
               { state === "admin" ?   <DeleteBanner /> : <PageNotFound/>}
            </Route>
            <Route path="/resultsearch/:textsearch" component={ResultSearch}>
              <ResultSearch />
            </Route>
            <Route path="/shoppingcart" component={ShoppingCart}>
              <ShoppingCart />
            </Route>
            <Route path="/login" exact component={Singin}>
              <Singin />
            </Route>
            <Route path="/login/admin" component={SinginAdmin}>
              <SinginAdmin />
            </Route>
            <Route path="/register" component={Singup}>
              <Singup />
            </Route>
            <Route component={PageNotFound}/>
        </Switch>
      </div>
   
  );
};


function App() {
  const [state, dispatch] = useReducer(reducer, initialState);
  return (
    <GlobalState>
      <userContext.Provider value={{ state, dispatch }}>
        <Router>
            <NavBar />
              <Routing/>
            <Footer />
        </Router>
      </userContext.Provider>
    </GlobalState>
  );
}

export default App;
