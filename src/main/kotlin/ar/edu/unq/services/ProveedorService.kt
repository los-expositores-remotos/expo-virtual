package ar.edu.unq.services

import ar.edu.unq.modelo.Proveedor
import org.bson.types.ObjectId

interface ProveedorService {
    fun buscarProveedores(texto: String): MutableList<Proveedor>
    fun crearProveedor(proveedor: Proveedor)
    fun recuperarProveedor(id: String): Proveedor
    fun recuperarATodosLosProveedores(): Collection<Proveedor>
    fun actualizarProveedor(proveedor: Proveedor)
    fun borrarProveedor(id: String)
    fun deleteAll()
}