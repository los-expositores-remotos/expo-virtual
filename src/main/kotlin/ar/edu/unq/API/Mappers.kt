package ar.edu.unq.API

import kotlin.reflect.KMutableProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

inline fun <reified E:Any, T:Any> mapTo(objeto: T): E{
    val objetoNuevo = E::class.java.newInstance()
    val propiedades = objeto::class.java.kotlin.memberProperties.filterIsInstance<KMutableProperty<*>>()
    val propiedadesNuevas = objetoNuevo::class.java.kotlin.memberProperties.filterIsInstance<KMutableProperty<*>>()
    for(propiedadNueva in propiedadesNuevas){
        val propiedadesFiltrada = propiedades.filter { prop -> prop.name == propiedadNueva.name }
        if(propiedadesFiltrada.isNotEmpty()){
            val propiedad = propiedadesFiltrada.first()
            propiedadNueva.setter.call(objetoNuevo, propiedad.getter.call(objeto))
        }
    }
    return objetoNuevo
}


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