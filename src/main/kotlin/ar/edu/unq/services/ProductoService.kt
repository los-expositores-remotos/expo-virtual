package ar.edu.unq.services

import ar.edu.unq.modelo.Producto

interface ProductoService {
    fun nuevoProducto(producto: Producto)
    fun obtenerProducto(productoId: String): Producto?
    fun actualizarProducto(producto: Producto)
}