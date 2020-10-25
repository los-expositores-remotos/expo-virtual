package ar.edu.unq.modelo

import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId


data class Producto(val idProveedor: ObjectId, val itemName: String, var description: String, val image: String, var stock: Int, var itemPrice: Int, var promotionalPrice: Int) {

    @BsonProperty("id")
    val id: ObjectId = ObjectId.get()

    fun getStock() {
        this.stock
    }

    fun removeStock(quantity: Int) {
        this.stock -= quantity
    }

    fun changeItemPrice(newPrice: Int) {
        this.itemPrice = newPrice
    }

    fun changePromotionalPrice(newPrice: Int) {
        this.itemPrice = newPrice
    }
}