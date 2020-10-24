import React from 'react';
import {Link} from 'react-router-dom'
import {SocialIcon} from 'react-social-icons';
import '../styles/Footer.css'

const Footer = () => {
  return (
    <footer class="page-footer">
    <div>
      <div class="row">
        <div class="col s3">
          <h5 class="white-text"  id="footerT">Expo Arte Virtual</h5>
          <p>3, 4 y 5 de octubre!</p>
        </div>
        <div class="col s3">
          <h5 class="white-text" id="footerT">Categorias</h5>
          <ul>
            <li><Link id="linkList"to="/contact">Contacto</Link></li>
            <li><Link id="linkList"to="/aboutus">Quienes Somos?</Link></li>
            <li><Link id="linkList"to="/suppliers">Empresas</Link></li>
            <li><Link id="linkList"to="/live">En Vivo</Link></li>
            <li><Link id="linkList"to="/faqs">Preguntas Frecuentes</Link></li>
            <li><Link id="linkList"to="/howtobuy">Como comprar?</Link></li>
          </ul>
        </div>
        <div class="col s3">
          <h5 class="white-text" id="footerT">Contactenos</h5>
          <ul>
            <li>
              <div>
              <div class="row">
                <div class="col" id="iconFooter">
                    <SocialIcon className="SocialIcon" network="whatsapp" bgColor="#ffffff" style={{ height: 25, width: 25 }}/>
                </div>
                <div class="col" id="wpFooter">
                    <p>+5491123869642</p>
                </div>
              </div>
              </div>
            </li>
            <li>
              <div class="row">
                  <div class="col" id="iconFooter"><i class="material-icons">email</i></div>
                  <div class="col" id="wpFooter"><p>eventos@guiadelarte.com.ar</p></div>
              </div>
              
              
            </li>
          </ul>
        </div>
        <div class="col s3">
          <h5 class="white-text" id="footerT">Sigamos conectados</h5>
        </div>
        <div class="col l4 offset-l2 s12">   
        </div>
      </div>
    </div>
    <div class="footer-copyright">
      <div class="container" id="contFooter">
        <div class="row">
          <div class="col s3"><SocialIcon className="SocialIcon" network="facebook" bgColor="#ffffff" /></div>
          <div class="col s3"><SocialIcon className="SocialIcon" network="instagram" bgColor="#ffffff" /></div>
          <div class="col s3"><SocialIcon className="SocialIcon" network="email" bgColor="#ffffff" /></div>
          <div class="col s3"><SocialIcon className="SocialIcon" network="whatsapp" bgColor="#ffffff" /></div>
        </div>
        © 2020 Copyright Expo Arte Virtual
      </div>
    </div>
  </footer>
  );
};

export default Footer;
