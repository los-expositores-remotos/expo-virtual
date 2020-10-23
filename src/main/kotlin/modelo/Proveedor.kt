package modelo

class Proveedor(val companyName: String, val companyImage: String, val facebook: String, val instagram: String, val web: String) {
    lateinit var productos: MutableCollection<Producto>

    fun addProduct(productoNuevo: Producto) {
        productos.add(productoNuevo)
    }

    fun removeProduct(productoNuevo: Producto) {
        productos.remove(productoNuevo)
    }
}