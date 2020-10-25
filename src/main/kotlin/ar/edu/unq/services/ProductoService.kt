package ar.edu.unq.services

import ar.edu.unq.modelo.Producto

interface ProductoService {
    fun obtenerProducto(producto: String): Producto
    fun actualizarProducto(producto: Producto)
}