package ar.edu.unq.modelo

import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId


class Producto{
    //@BsonProperty("id")
    var id: ObjectId = ObjectId()
    lateinit var idProveedor: ObjectId
    lateinit var itemName: String
    lateinit var description: String
    var listImages: MutableList<String> = emptyList<String>().toMutableList()
    var stock: Int = 0
    var itemPrice: Int = 0
    var promotionalPrice: Int = 0

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

    fun removeStock(quantity: Int) {
        this.stock -= quantity
    }
}