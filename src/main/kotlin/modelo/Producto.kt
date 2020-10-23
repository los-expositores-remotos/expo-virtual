package modelo

class Producto(val itemName: String, val description: String, val image: String, var stock: Int, var itemPrice: Int, var promotionalPrice: Int) {
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