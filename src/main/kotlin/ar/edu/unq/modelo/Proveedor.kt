package ar.edu.unq.modelo

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import org.bson.codecs.pojo.annotations.BsonIgnore
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId


class Proveedor {

    //@BsonProperty("id")
    var id: ObjectId? = ObjectId.get()
    var companyName: String = ""
    var companyImage: String = ""
    var facebook: String = ""
    var instagram: String = ""
    var web: String = ""
//    @Transient
//    @JsonSerialize
//    @JsonDeserialize
    //@BsonIgnore
    //@Transient
    var productos: MutableList<Producto> = emptyList<Producto>().toMutableList()
    //val productos: MutableList<Producto> = emptyList<Producto>().toMutableList()
    constructor(){}
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

//    fun addProduct(productoNuevo: Producto) {
//        productos.add(productoNuevo)
//    }
//
//    fun removeProduct(productoNuevo: Producto) {
//        productos.remove(productoNuevo)
//    }
}