package ar.edu.unq.dao

import ar.edu.unq.modelo.banner.Banner

interface BannerDAO {
    fun save(anObject: Banner)
    fun get(id: String?): Banner?
    fun getAll(): List<Banner>
    fun <E> findEq(field:String, value:E ): List<Banner>
    fun deleteAll()
    fun delete(id: String)
}
