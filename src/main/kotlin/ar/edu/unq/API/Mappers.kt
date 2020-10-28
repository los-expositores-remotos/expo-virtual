package ar.edu.unq.API

import ar.edu.unq.modelo.Producto
import org.bson.types.ObjectId
/*
var id: ObjectId = ObjectId.get()   /lo manejamos como objeto?
var companyName: String = ""
var companyImage: String = ""
var facebook: String = ""
var instagram: String = ""
var web: String = ""
var productos: MutableList<Producto> = emptyList<Producto>().toMutableList() /queda en castellano?
*/
/*
var id: ObjectId = ObjectId()
lateinit var idProveedor: ObjectId
lateinit var itemName: String
lateinit var description: String
lateinit var image: String
var stock: Int = 0
var itemPrice: Int = 0
var promotionalPrice: Int = 0
*/

data class CompanyViewMapper(val id: ObjectId,
                             val companyName: String,
                             val companyImage: String,
                             val facebook: String,
                             val instagram: String,
                             val web: String,
                             val products: Collection<ProductsViewMapper>)

data class CompanyNameViewMapper(val companyName: String)

data class CompanyImageViewMapper(val companyImage: String)


data class ProductsViewMapper(val id: ObjectId,
                              val idProveedor: ObjectId,
                              val itemName: String,
                              val description: String,
                              val images: List<String>,
                              val stock: Int,
                              val itemPrice: Int,
                              val promotionalPrice: Int)
