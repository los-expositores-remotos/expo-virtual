package ar.edu.unq.modelo

class Proveedor : ModelObjectWithBsonId {
    var companyName: String = ""
    var companyImage: String = ""
    var facebook: String = ""
    var instagram: String = ""
    var web: String = ""
    var productos: MutableList<Producto> = emptyList<Producto>().toMutableList()
    constructor()

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

//    override fun castearAMiTipo(other: Any): Proveedor {
//        return  other as Proveedor
//    }
}