package dao

import modelo.Proveedor
import modelo.Producto

interface ProveedorDAO {
    fun crear(proveedor: Proveedor)
    fun actualizar(proveedor: Proveedor)
    fun recuperar(idDelProveedor: Int): Proveedor
    fun recuperarATodos() : List<Proveedor>

    /* Operaciones sobre Productos*/
    fun agregarProducto(proveedor: Proveedor, producto: Producto): Producto
    fun actualizar(producto: Producto)
    fun recuperarProducto(nombre: String): Producto
}