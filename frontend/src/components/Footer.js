import React from 'react';
import {Link} from 'react-router-dom'
import {SocialIcon} from 'react-social-icons';
import '../styles/Footer.css'

const Footer = () => {
  return (
    <footer className="page-footer">
    <div>
      <div className="row">
        <div className="col s4">
          <h5 className="white-text"  id="footerT">Expo Arte Virtual</h5>
          <p>3, 4 y 5 de octubre!</p>
        </div>
        <div className="col s4">
          <h5 className="white-text" id="footerT">Categorias</h5>
          <ul>
            <li><Link id="linkList"to="/contact">Contacto</Link></li>
            <li><Link id="linkList"to="/aboutus">Quienes Somos?</Link></li>
            <li><Link id="linkList"to="/suppliers">Empresas</Link></li>
            <li><Link id="linkList"to="/live">En Vivo</Link></li>
            <li><Link id="linkList"to="/faqs">Preguntas Frecuentes</Link></li>
            <li><Link id="linkList"to="/howtobuy">Como comprar?</Link></li>
          </ul>
        </div>
        <div className="col s4">
          <h5 className="white-text" id="footerT">Contactenos</h5>
          <ul>
            <li>
              <div>
              <div className="row">
                <div className="col" id="iconFooter">
                    <SocialIcon className="SocialIcon" network="whatsapp" bgColor="#ffffff" style={{ height: 25, width: 25 }}/>
                </div>
                <div className="col" id="wpFooter">
                    <p>+5491123869642</p>
                </div>
              </div>
              </div>
            </li>
            <li>
              <div className="row">
                  <div className="col" id="iconFooter"><i className="material-icons">email</i></div>
                  <div className="col" id="wpFooter"><p>eventos@guiadelarte.com.ar</p></div>
              </div>
              
              
            </li>
          </ul>
        </div>
        <div className="col l4 offset-l2 s12">   
        </div>
      </div>
    </div>
    <div className="footer-copyright">
      <div className="container" id="contFooter">
        <div className="row">
          <div className="col s3"><a href='https://www.facebook.com/guiadelartedigital'><SocialIcon className="SocialIcon" network="facebook" bgColor="#ffffff" /></a></div>
          <div className="col s3"><a href='http://instagram.com/guiadelartedigital'><SocialIcon className="SocialIcon" network="instagram" bgColor="#ffffff" /></a></div>
          <div className="col s3"><Link id="linkList"to="/contact"><SocialIcon className="SocialIcon" network="email" bgColor="#ffffff" /></Link></div>
          <div className="col s3"><a href='https://wa.me/5491123869642'><SocialIcon className="SocialIcon" network="whatsapp" bgColor="#ffffff" /></a></div>
        </div>
        © 2020 Copyright Expo Arte Virtual
      </div>
    </div>
  </footer>
  );
};

export default Footer;
