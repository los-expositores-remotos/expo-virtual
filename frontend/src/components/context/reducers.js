//AGREGO UN PRODUCTO
export const ADD_PRODUCT = 'ADD_PRODUCT';

//BORRO UN PRODUCTO
export const REMOVE_PRODUCT = 'REMOVE_PRODUCT';

//EMILINA EL PRODUCTO
export const DELETE_PRODUCT = 'DELETE_PRODUCT';

/***TOMA COMO PARAMETRO UN PRODUCTO Y UN ESTADO
1) GENERO MI CARRITO ACTUALIZADO 
2) BUSCO EL INDEX DE MI CARRITO
3)PREGUNTA SI EL INDEX NO EXITE {ACTUALIZO EL UPDATEDCART HACIENDO PUSH EN ESA LISTA}
  SI EXISTE GENERA UN UPDATEDITEM LE SUMA EL QUANTITY EN 1 Y LUEGO ACTUALIZA EL UPDATEDCART
4) RETORNA EL ESTADO PREVIO Y SETEA EL UPDATEDCART EN EL CART
***/
const addProductToCart = (product, state) => {
  const updatedCart = [...state.cart];
  const updatedItemIndex = updatedCart.findIndex(
    item => item.id === product.id
  );

  if (updatedItemIndex < 0) {
    updatedCart.push({ ...product, quantity: 1 });
  } else {
    const updatedItem = {
      ...updatedCart[updatedItemIndex]
    };
    updatedItem.quantity++;
    updatedCart[updatedItemIndex] = updatedItem;
  }
  localStorage.setItem('cart', JSON.stringify(updatedCart))
  return { ...state, cart: updatedCart };
};

/***TOMA COMO PARAMETRO UN ID DE PRODUCTO Y UN ESTADO
1) GENERO MI CARRITO ACTUALIZADO 
2) BUSCO EL INDEX DE MI CARRITO
3)GENERA UN UPDATEDITEM, LE RESTA EL QUANTITY EN 1 Y LUEGO ACTUALIZA EL UPDATEDCART
4) PREGUNTA SI LA QUANTITY ES 0 LO BORRA CON SPLICE
   SINO ACTUALIZA EL UPDATEDCART
5) RETORNA EL ESTADO PREVIO Y SETEA EL UPDATEDCART EN EL CART
***/
const removeProductFromCart = (productId, state) => {
  //console.log('Removing product with id: ' + productId);
  const updatedCart = [...state.cart];
  const updatedItemIndex = updatedCart.findIndex(item => item.id === productId);

  const updatedItem = {
    ...updatedCart[updatedItemIndex]
  };
  updatedItem.quantity--;
  if (! updatedItem.quantity <= 0) {
    updatedCart[updatedItemIndex] = updatedItem;
  }

  localStorage.setItem('cart', JSON.stringify(updatedCart))

  return { ...state, cart: updatedCart };
};

const deleteProductFromCart = (productId, state) => {
  console.log('Removing product with id: ' + productId);
  const updatedCart = [...state.cart];
  const updatedItemIndex = updatedCart.findIndex(item => item.id === productId);

    updatedCart.splice(updatedItemIndex, 1);
    localStorage.setItem('cart', JSON.stringify(updatedCart))
  return { ...state, cart: updatedCart };
};

/**
 * SETEA EL REDUCER
 * @param {*} state 
 * @param {*} action 
 */
export const shopReducer = (state, action) => {
  switch (action.type) {
    case ADD_PRODUCT:
      return addProductToCart(action.product, state);
    case REMOVE_PRODUCT:
      return removeProductFromCart(action.productId, state);
    case DELETE_PRODUCT:
      return deleteProductFromCart(action.productId, state);
    default:
      return state;
  }
};
