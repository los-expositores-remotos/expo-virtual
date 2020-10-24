package modelo

import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

class Proveedor {
    @BsonProperty("id")
    val id: ObjectId = ObjectId.get()
    lateinit var companyName: String
    lateinit var companyImage: String
    lateinit var facebook: String
    lateinit var instagram: String
    lateinit var web: String
    //val productos: MutableList<Producto> = emptyList<Producto>().toMutableList()
    protected constructor(){}
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