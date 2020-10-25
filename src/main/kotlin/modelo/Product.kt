package modelo

data class Product(
        val nombreDelArticulo: String,
        val description: String,
        val imagenes: List<String>,
        val stock: Int,
        val precio: Int,
        val precioPromocional: Int
)