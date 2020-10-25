package ar.edu.unq.services

import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.modelo.Producto
import org.bson.types.ObjectId

interface ProveedorService {
    fun crearProveedor(proveedor: Proveedor)
    fun recuperarProveedor(id: Int): Proveedor
    fun recuperarATodosLosProveedores(): Collection<Proveedor>

    fun agregarProducto(idProveedor: Int, producto: Producto): Producto
    fun recuperarProducto(id: Int, nombreProducto: String): Producto
    fun crearProveedor(LaCompany: String): Proveedor

    fun nuevoProducto(proveedor: String, producto: String): Producto
    fun obtenerProveedor(proveedor: String): Proveedor
}