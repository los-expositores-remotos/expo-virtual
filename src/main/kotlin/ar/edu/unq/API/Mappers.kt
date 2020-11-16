package ar.edu.unq.API

import kotlin.reflect.KMutableProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

data class CompanyNameViewMapper(val companyName: String){
    constructor() : this("")
}
data class UserViewMapper(val nombre :String?, val apellido : String?, val dni : Int?)

data class AdminLogin(val userName : String?, val password : String?)

data class UserRegisterMapper(val nombre:String?, val apellido : String?, val dni : Int?)

data class UserLogin (val dni : Int?)

data class ErrorViewMapper(val result: String?, val message: String?)

data class CompanyImageViewMapper(val companyImage: String){
    constructor() : this("")
}

data class BannerViewMapper(val id: String, val image: String, val category: String){
    constructor() : this("", "", "")
}

data class CompanyViewMapper(val id: String,
                             val companyName: String,
                             val companyImage: String,
                             val facebook: String,
                             val instagram: String,
                             val web: String,
                             val products: Collection<ProductViewMapper>){
    constructor() : this("", "", "", "", "", "", emptyList())
}

data class ProductViewMapper(val id: String,
                             val idProveedor: String,
                             val itemName: String,
                             val description: String,
                             val images: List<String>,
                             val stock: Int,
                             val itemPrice: Int,
                             val promotionalPrice: Int){
    constructor() : this("", "", "", "", emptyList(), 0, 0, 0)
}

data class BannerRegisterMapper(val image: String?, val category: String?){
    constructor() : this("", "")
}

data class CompanyRegisterMapper(val companyName: String?,
                                 val companyImage: String?,
                                 val facebook: String?,
                                 val instagram: String?,
                                 val web: String?){
    constructor() : this("", "", "", "", "")
}

data class ProductRegisterMapper(val idProveedor: String?,
                                 val itemName: String?,
                                 val description: String?,
                                 val images: List<String>?,
                                 val stock: Int?,
                                 val itemPrice: Int?,
                                 val promotionalPrice: Int?){
    constructor() : this("", "", "", emptyList(),  0, 0, 0)
}

data class OkResultMapper(val result: String){
    constructor() : this("")
}

data class ProductListRegisterMapper(val products: Collection<ProductRegisterMapper>)

