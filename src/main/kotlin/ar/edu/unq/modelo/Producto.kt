package ar.edu.unq.modelo

import kotlin.properties.Delegates



class Producto{

    //@BsonProperty("id")
  //  var id: ObjectId = ObjectId()
    var id by Delegates.notNull<Int>()
    //  lateinit var idProveedor: ObjectId
    var idProveedor by Delegates.notNull<Int>()
    lateinit var itemName: String
    lateinit var description: String
    var images: List<String> = mutableListOf()
    var stock: Int = 0
    var itemPrice: Int = 0
    var promotionalPrice: Int = 0

    constructor(){}

/*    constructor(
        idProveedor: ObjectId,
        itemName: String,
        description: String,
        image: List<String>,
        stock: Int,
        itemPrice: Int,
        promotionalPrice: Int
    ) {
        this.idProveedor = idProveedor
        this.itemName = itemName
        this.description = description
        this.images = image
        this.stock = stock
        this.itemPrice = itemPrice
        this.promotionalPrice = promotionalPrice
    }*/
    constructor(
            id: Int,
            idProveedor: Int,
            itemName: String,
            description: String,
            image: List<String>,
            stock: Int,
            itemPrice: Int,
            promotionalPrice: Int
    ) {
        this.id = id
        this.idProveedor = idProveedor
        this.itemName = itemName
        this.description = description
        this.images = image
        this.stock = stock
        this.itemPrice = itemPrice
        this.promotionalPrice = promotionalPrice
    }
    fun removeStock(quantity: Int) {
        this.stock -= quantity
    }
}