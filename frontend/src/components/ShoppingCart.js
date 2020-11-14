import React, { useState, useEffect } from 'react'
import '../styles/ShoppingCart.css'
import TbodyItemCart from './TbodyItemCart'


const ShoppingCart = () => {
    const [products, setProducts] = useState([])

    useEffect(() => {
        setProducts(JSON.parse(localStorage.getItem("products")) || [])
    }, [])

 
    const cantOfProducts = () => {
        let myProducts = JSON.parse(localStorage.getItem("products")) || []
        let cant = 0
        myProducts.forEach(product => {
            cant = cant + parseInt(product.cant)
        });
        return cant
    }

    const listOfProducts = () => {
        let myProducts = JSON.parse(localStorage.getItem("products")) || []
    const list = myProducts.map((product)=>{
        return(<TbodyItemCart product={product}/>)
        })

    return (
        <table>
            <thead>
                <tr>
                    <th>Imagen</th>
                    <th>Nombre</th>
                    <th>Cantidad</th>
                    <th>Precio</th>
                </tr>
            </thead>
            {list}
        </table>
        )
    }

    return (
    <div>
        <p>tengo {cantOfProducts()} productos en el carrito</p>
        <div> 
            {listOfProducts()}
        </div>
    </div>    
    )
}

export default ShoppingCart;