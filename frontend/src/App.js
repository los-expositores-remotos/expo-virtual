import React from 'react';
import {BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import Home from './components/routes/Home.js'
import Suppliers from './components/routes/Suppliers.js'
import Live from './components/routes/Live.js'
import Faqs from './components/routes/Faqs.js'
import Howtobuy from './components/routes/Howtobuy.js'
import Aboutus from './components/routes/Aboutus.js'
import Contact from './components/routes/Contact.js'
import Admin from './components/routes/Admin.js';
import NavBar from './components/NavBar'
import Footer from './components/Footer'
//modificacion
const Routing = () => {
  return (
      <div>
        <Switch>
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
            <Route path="/admin" component={Admin}>
              <Admin />
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
