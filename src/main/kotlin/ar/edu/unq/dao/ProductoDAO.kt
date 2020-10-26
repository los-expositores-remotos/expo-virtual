package ar.edu.unq.dao

import ar.edu.unq.modelo.Producto
import ar.edu.unq.services.runner.DataBaseType

interface ProductoDAO {
    fun save(anObject: Producto)
    fun save(objects: List<Producto>)
    fun update(anObject: Producto, id: String?)
    fun get(id: String?): Producto?
    fun get(idProveedor: String, nombreProducto: String): Producto?
    fun getAll() : List<Producto>
    fun deleteAll()
    fun saveOrUpdate(productos: List<Producto>, dataBaseType: DataBaseType)
}