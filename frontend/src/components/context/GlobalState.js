import React, { useState, useReducer } from 'react';

import ShopContext from './shop-context';
import { shopReducer, ADD_PRODUCT, REMOVE_PRODUCT, DELETE_PRODUCT } from './reducers';

const GlobalState = props => {
  const [products, setProducts] = useState([]) 
  
  
  const [cartState, dispatch] = useReducer(shopReducer, { cart: JSON.parse(localStorage.getItem('cart')) || [] });

  const addProductToCart = product => {
    setTimeout(() => {
      dispatch({ type: ADD_PRODUCT, product: product });
    }, 700);
  };

  const removeProductFromCart = productId => {
    setTimeout(() => {
      dispatch({ type: REMOVE_PRODUCT, productId: productId });
    }, 700);
  };
  const deleteProductFromCart = productId => {
    setTimeout(() => {
      dispatch({ type: DELETE_PRODUCT, productId: productId });
    }, 700);
  };

  return (
    <ShopContext.Provider
      value={{
        products: products,
        cart: cartState.cart,
        addProductToCart: addProductToCart,
        removeProductFromCart: removeProductFromCart,
        deleteProductFromCart: deleteProductFromCart
      }}
    >
      {props.children}
    </ShopContext.Provider>
  );
};

export default GlobalState;
