package ar.edu.unq.modelo

import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId


class Producto(val itemName: String, var description: String, val image: String, var stock: Int, var itemPrice: Int, var promotionalPrice: Int) {

    @BsonProperty("id")
    val id: ObjectId = ObjectId.get()

    fun removeStock(quantity: Int) {
        this.stock -= quantity
    }
}