package ar.edu.unq.API

data class CompanyData(

    val nombreDeEmpresa: String,
    val imagenDeLaEmpresa: String,
    val facebook: String,
    val instagram: String,
    val web: String,
    val productos: List<ProductData>

)

data class ProductData(
    val nombreDelArticulo: String,
    val description: String,
    val imagenes: List<String>,
    val stock: Int,
    val precio: Int,
    val precioPromocional: Int
)
