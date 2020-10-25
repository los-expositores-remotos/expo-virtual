package ar.edu.unq.services

import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.modelo.Producto
import org.bson.types.ObjectId

interface ProveedorService {
    fun crearProveedor(proveedor: Proveedor)
    fun recuperarProveedor(id: Int): Proveedor?
    fun recuperarATodosLosProveedores(): Collection<Proveedor>
}