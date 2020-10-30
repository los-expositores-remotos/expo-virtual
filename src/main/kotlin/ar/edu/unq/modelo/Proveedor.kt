package ar.edu.unq.modelo

import kotlin.properties.Delegates



class Proveedor {
    val expo: Expo = Expo()
    //@BsonProperty("id")
//    var id: ObjectId = ObjectId.get()
    var id by Delegates.notNull<Int>()
    var companyName: String = ""
    var companyImage: String = ""
    var facebook: String = ""
    var instagram: String = ""
    var web: String = ""
    var productos: MutableList<Producto> = emptyList<Producto>().toMutableList()
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
    constructor(id: Int, companyName: String, companyImage: String, facebook: String, instagram: String, web: String, productos: MutableList<Producto>){
        this.id = id
        this.companyName = companyName
        this.companyImage = companyImage
        this.facebook = facebook
        this.instagram = instagram
        this.web = web
        this.productos = productos
    }

//    fun addProduct(productoNuevo: Producto) {
//        productos.add(productoNuevo)
//    }
//
//    fun removeProduct(productoNuevo: Producto) {
//        productos.remove(productoNuevo)
//    }
}