package ar.edu.unq.dao

import ar.edu.unq.modelo.Admin

interface AdminDAO {
    fun save(anObject: Admin)
    fun save(objects: List<Admin>)
    fun update(anObject: Admin, id: String?)
    fun get(id: String?): Admin?
    fun getAll() : List<Admin>
    fun deleteAll()
    fun delete(id: String)
    fun <E> findEq(field:String, value:E ): List<Admin>
}