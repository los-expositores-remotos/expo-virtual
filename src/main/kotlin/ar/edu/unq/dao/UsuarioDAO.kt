package ar.edu.unq.dao

import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.modelo.Usuario
import ar.edu.unq.modelo.banner.Banner

interface UsuarioDAO {
    fun save(anObject: Usuario)
    fun save(objects: List<Usuario>)
    fun update(anObject: Usuario, id: String?)
    fun get(id: String?): Usuario?
    fun getAll() : List<Usuario>
    fun deleteAll()
    fun delete(id: String)
    fun <E> findEq(field:String, value:E ): List<Usuario>
}