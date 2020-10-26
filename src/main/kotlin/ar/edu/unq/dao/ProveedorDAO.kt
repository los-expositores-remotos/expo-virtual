package ar.edu.unq.dao

import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.runner.DataBaseType
import org.bson.conversions.Bson

interface ProveedorDAO {
    fun save(anObject: Proveedor)
    fun save(objects: List<Proveedor>)
    fun update(anObject: Proveedor, id: String?)
    fun get(id: String?): Proveedor?
    fun getAll() : List<Proveedor>
    fun deleteAll()
    fun saveInTrx(proveedor: Proveedor, dataBaseType: DataBaseType)
    fun saveInTrx(proveedores: List<Proveedor>, dataBaseType: DataBaseType)
    fun getAllInTrx(dataBaseType: DataBaseType): List<Proveedor>
    fun updateInTrx(proveedor: Proveedor, id: String?, dataBaseType: DataBaseType)
    fun getByInTrx(property:String, value: String?, dataBaseType: DataBaseType): Proveedor?
    fun <E> findEqInTrx(field:String, value:E, dataBaseType: DataBaseType): List<Proveedor>
    fun findInTrx(filter: Bson, dataBaseType: DataBaseType): List<Proveedor>
    fun getInTrx(id: String?, dataBaseType: DataBaseType): Proveedor?

    /* Operaciones sobre Productos*/
    // fun agregarProducto(proveedor: Proveedor, producto: Producto)
    //fun actualizar(producto: Producto)
    // fun recuperarProducto(nombre: String): Producto
}