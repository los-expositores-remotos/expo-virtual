import React from 'react';
import {SocialIcon} from 'react-social-icons';

const Footer = () => {
  return (
      <div className="footer-copyright">
        <div className="container">
          <div className='row'>
            <div className='col'>
              <SocialIcon className="SocialIcon" network="facebook" bgColor="#000000" />
            </div>
            <div className='col'>
            <SocialIcon className="SocialIcon" network="instagram" bgColor="#000000" />
            </div>
            <div className='col'>
              <SocialIcon className="SocialIcon" network="email" bgColor="#000000" />
            </div>
            <div className='col'>
              <SocialIcon className="SocialIcon" network="whatsapp" bgColor="#000000" />
            </div>
          </div>
        </div>
        <div className="container">
          © 2020 Copyright Expo Virtual
        <a className="grey-text text-lighten-4 right" href="#!">More Links</a>
        </div>
      </div>
  );
};

export default Footer;
