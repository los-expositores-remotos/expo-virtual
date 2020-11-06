package ar.edu.unq.modelo

import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId


class Producto{
    @BsonProperty("id")
    var id: ObjectId = ObjectId()
    lateinit var idProveedor: ObjectId
    var itemName: String = ""
    var description: String = ""
    var listTags: MutableList<String> = emptyList<String>().toMutableList()
    var listImages: MutableList<String> = emptyList<String>().toMutableList()
    var stock: Int = 0
    var itemPrice: Int = 0
    var promotionalPrice: Int = 0
    var vendidos = 0

    constructor(){}

    constructor(
        idProveedor: ObjectId,
        itemName: String,
        description: String,
        stock: Int,
        itemPrice: Int,
        promotionalPrice: Int
    ) {
        this.idProveedor = idProveedor
        this.itemName = itemName
        this.description = description
        this.stock = stock
        this.itemPrice = itemPrice
        this.promotionalPrice = promotionalPrice
    }

    fun addImage(imageUrl: String) {
        listImages.add(imageUrl)
    }

    fun addImage(imagesUrl: List<String>) {
        for (img in imagesUrl) {
            listImages.add(img)
        }
    }

    fun addTag(tag: String) {
        listTags.add(tag)
    }

    fun addTag(tags: List<String>) {
        for (tag in tags) {
            listTags.add(tag)
        }
    }

    fun cargarVenta(cantidadACargar: Int) {
        vendidos += cantidadACargar
    }

    fun removeStock(quantity: Int) {
        this.stock -= quantity
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Producto

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}