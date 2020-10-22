package services

import modelo.Proveedor
import modelo.Producto

interface ProveedorService {
    fun crearProveedor(proveedor: Proveedor): Int
    fun recuperarProveedor(id: Int): Proveedor
    fun recuperarATodosLosProveedores(): Collection<Proveedor>

    fun agregarProducto(idProveedor: Int, producto: Producto): Producto
    fun recuperarProducto(id: Int, nombreProducto: String): Producto
}