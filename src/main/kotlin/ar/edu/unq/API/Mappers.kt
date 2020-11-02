package ar.edu.unq.API

data class CompanyViewMapper(val id: String,
                             val companyName: String,
                             val companyImage: String,
                             val facebook: String,
                             val instagram: String,
                             val web: String,
                             val products: Collection<ProductViewMapper>)

data class CompanyNameViewMapper(val companyName: String)

data class CompanyImageViewMapper(val companyImage: String)

data class BannerImageViewMapper(val id: String, val image: String)

data class ProductViewMapper(val id: String,
                             val idProveedor: String,
                             val itemName: String,
                             val description: String,
                             val images: List<String>,
                             val stock: Int,
                             val itemPrice: Int,
                             val promotionalPrice: Int)

data class OkResultMapper(val result: String)

data class SupplierRegisterMapper(val companyName: String?,
                                  val companyImage: String?,
                                  val facebook: String?,
                                  val instagram: String?,
                                  val web: String?)

data class BannerRegisterMapper(val image: String?)

data class BannerViewMapper(val image: String)

data class ProductRegisterMapper(val idProveedor: String?,
                                 val itemName: String?,
                                 val description: String?,
                                 val images: List<String>?,
                                 val stock: Int?,
                                 val itemPrice: Int?,
                                 val promotionalPrice: Int?)