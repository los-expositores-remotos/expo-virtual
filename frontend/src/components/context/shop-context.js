import React from 'react';

/** CREO EL CONTEXTO */
export default React.createContext({
  products: [],
  cart: [],
  addProductToCart: product => {},
  removeProductFromCart: productId => {},
  deleteProductFromCart: productId => {}
});
