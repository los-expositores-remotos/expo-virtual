package ar.edu.unq.API

data class CompanyViewMapper(val nombreDeEmpresa: String,
                             val imagenDeLaEmpresa: String,
                             val facebook: String,
                             val instagram: String,
                             val web: String,
                             val products: Collection<ProductsViewMapper>)

data class CompanyNameViewMapper(val companyName: String)

data class CompanyImageViewMapper(val companyImage: String)


data class ProductsViewMapper(val nombreDelArticulo: String,
                              val description: String,
                              val imagenes: List<String>,
                              val stock: Int,
                              val precio: Int,
                              val precioPromocional: Int)
