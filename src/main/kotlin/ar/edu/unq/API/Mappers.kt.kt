package ar.edu.unq.API

data class CompanyViewMapper(val id: String,
                             val companyName: String,
                             val companyImage: String,
                             val facebook: String,
                             val instagram: String,
                             val web: String,
                             val products: Collection<ProductsViewMapper>)

data class CompanyNameViewMapper(val companyName: String)

data class CompanyImageViewMapper(val companyImage: String)

data class BannerImageViewMapper(val id: String, val image: String)

data class ProductsViewMapper(val id: String,
                              val idProveedor: String,
                              val nombreDelArticulo: String,
                              val description: String,
                              val imagenes: List<String>,
                              val stock: Int,
                              val precio: Int,
                              val precioPromocional: Int)

data class OkResultMapper(val result: String)

data class SupplierRegisterMapper(val companyName: String?,
                                  val companyImage: String?,
                                  val facebook: String?,
                                  val instagram: String?,
                                  val web: String?)

data class BannerRegisterMapper(val image: String?)