package ar.edu.unq.API

data class UserViewMapper(val nombre :String?, val apellido : String?)

data class PaymentMapper(val token :String?, val unitPrice : Int?, val email : String?, val description: String?, val amount : Float?, val quantity : Int?, val paymentMethodId: String?, val installments: Int?, val issuerId: String?, val docType: String?, val docNumber: String?)

data class AdminLogin(val userName : String?, val password : String?)

data class UserRegisterMapper(val nombre:String?, val apellido : String?, val dni : Int?)

data class UserLogin (val dni : Int?)

data class ErrorViewMapper(val result: String?, val message: String?)

data class CompanyNameViewMapper(val companyName: String)

data class CompanyImageViewMapper(val companyImage: String)

data class BannerViewMapper(val id: String, val image: String, val category: String)

data class CompanyViewMapper(val id: String,
                             val companyName: String,
                             val companyImage: String,
                             val facebook: String,
                             val instagram: String,
                             val web: String,
                             val products: Collection<ProductViewMapper>)

data class ProductViewMapper(val id: String,
                             val idProveedor: String,
                             val itemName: String,
                             val description: String,
                             val images: List<String>,
                             val stock: Int,
                             val itemPrice: Int,
                             val promotionalPrice: Int,
                             val longitud: Int,
                             val ancho: Int,
                             val alto: Int,
                             val pesoKg: Int)

data class BannerRegisterMapper(val image: String?, val category: String?)

data class CompanyRegisterMapper(val companyName: String?,
                                 val companyImage: String?,
                                 val facebook: String?,
                                 val instagram: String?,
                                 val web: String?)

data class ProductRegisterMapper(val idProveedor: String?,
                                 val itemName: String?,
                                 val description: String?,
                                 val images: List<String>?,
                                 val stock: Int?,
                                 val itemPrice: Int?,
                                 val promotionalPrice: Int?,
                                 val longitud: Int?,
                                 val ancho: Int?,
                                 val alto: Int?,
                                 val pesoKg: Int?)

data class ProductListRegisterMapper(val products: Collection<ProductRegisterMapper>)

data class OkResultMapper(val result: String)