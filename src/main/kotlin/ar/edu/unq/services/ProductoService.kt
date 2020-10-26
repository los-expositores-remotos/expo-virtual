package ar.edu.unq.services

import ar.edu.unq.modelo.Producto
import org.bson.types.ObjectId

interface ProductoService {
    fun nuevoProducto(producto: Producto)
    fun obtenerProducto(proveedorId: String, nombreItem: String): Producto
    fun actualizarProducto(producto: Producto)
    fun deleteAll()
}