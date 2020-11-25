package ar.edu.unq.modelo


import org.bson.types.ObjectId


class Producto : ModelObjectWithBsonId{
    lateinit var idProveedor: ObjectId
    var itemName: String = ""
    var description: String = ""
    var listTags: MutableList<String> = emptyList<String>().toMutableList()
    var listImages: MutableList<String> = emptyList<String>().toMutableList()
    var stock: Int = 0
    var itemPrice: Int = 0
    var promotionalPrice: Int = 0
    var vendidos = 0
    var longitud: Int = 0
    var ancho: Int = 0
    var alto : Int = 0
    var pesoKg: Int = 0

    constructor()

    constructor(
        idProveedor: ObjectId,
        itemName: String,
        description: String,
        stock: Int,
        itemPrice: Int,
        promotionalPrice: Int,
        longitud: Int,
        ancho: Int,
        alto : Int,
        peso: Int
    ) {
        this.idProveedor = idProveedor
        this.itemName = itemName
        this.description = description
        this.stock = stock
        this.itemPrice = itemPrice
        this.promotionalPrice = promotionalPrice
        this.longitud = longitud
        this.ancho = ancho
        this.alto = alto
        this.pesoKg = peso
    }

    fun addImage(imageUrl: String) {
        listImages.add(imageUrl)
    }

    fun addImage(imagesUrl: List<String>) {
        listImages.addAll(imagesUrl)
    }

    fun addTag(tag: String) {
        listTags.add(tag)
    }

    fun addTag(tags: List<String>) {
        listTags.addAll(tags)
    }

    fun cargarVenta(cantidadACargar: Int) {
        stock -= cantidadACargar
        vendidos += cantidadACargar
    }
//
//    override fun castearAMiTipo(other: Any): Producto {
//        return other as Producto
//    }
}