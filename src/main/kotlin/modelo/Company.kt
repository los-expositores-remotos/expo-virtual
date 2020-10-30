package modelo

data class Company(

    val id: Int,
    val nombreDeEmpresa: String,
    val imagenDeLaEmpresa: String,
    val facebook: String,
    val instagram: String,
    val web: String,
    val productos: MutableList<Product> = mutableListOf()
)