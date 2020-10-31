package ar.edu.unq.services

import ar.edu.unq.modelo.Producto

interface ProductoService {
    fun nuevoProducto(producto: Producto)
    fun obtenerProducto(proveedorId: String, nombreItem: String): Producto
    fun actualizarProducto(producto: Producto)
    fun recuperarATodosLosProductos(): Collection<Producto>
    fun borrarProducto(id: String)
    fun recuperarProducto(id: String): Producto
    fun deleteAll()
}