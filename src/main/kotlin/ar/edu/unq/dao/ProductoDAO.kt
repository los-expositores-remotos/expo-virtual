package ar.edu.unq.dao

import ar.edu.unq.modelo.Producto

interface ProductoDAO {
    fun save(anObject: Producto)
    fun save(objects: List<Producto>)
    fun update(anObject: Producto, id: String?)
    fun get(id: String?): Producto?
    fun get(idProveedor: String, nombreProducto: String): Producto?
    fun getAll() : List<Producto>
    fun deleteAll()//TODO:sacar
//    fun saveOrUpdate(productos: List<Producto>, dataBaseType: DataBaseType)
    fun delete(id: String)
}