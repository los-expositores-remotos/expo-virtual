package ar.edu.unq.modelo

import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

class Proveedor {

    @BsonProperty("id")
    var id: ObjectId = ObjectId.get()
    var companyName: String = ""
    var companyImage: String = ""
    var facebook: String = ""
    var instagram: String = ""
    var web: String = ""
    var productos: MutableList<Producto> = emptyList<Producto>().toMutableList()
    constructor()
    constructor(companyName: String) {
        this.companyName = companyName
    }

    constructor(companyName: String, companyImage: String, facebook: String, instagram: String, web: String) {
        this.companyName = companyName
        this.companyImage = companyImage
        this.facebook = facebook
        this.instagram = instagram
        this.web = web
    }

    fun addProduct(productoNuevo: Producto) {
        productoNuevo.idProveedor = this.id
        productos.add(productoNuevo)
    }

    fun removeProduct(productoNuevo: Producto) {
        productos.remove(productoNuevo)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Proveedor

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}