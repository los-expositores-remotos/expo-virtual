package ar.edu.unq.services

import ar.edu.unq.modelo.Producto
import org.bson.types.ObjectId

interface ProductoService {
    fun buscarProductos(texto: String): MutableList<Producto>
    fun nuevoProducto(producto: Producto)
    fun obtenerProducto(proveedorId: String, nombreItem: String): Producto
    fun actualizarProducto(producto: Producto)
    fun recuperarATodosLosProductos(): Collection<Producto>
    fun borrarProducto(id: String)
    fun recuperarProducto(id: String): Producto
    fun deleteAll()
   // fun buscarProducto(palabra : String): ArrayList<Producto> //metodo mistico de busqueda
}