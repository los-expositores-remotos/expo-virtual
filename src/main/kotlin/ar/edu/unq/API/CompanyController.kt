package ar.edu.unq.API

import ar.edu.unq.modelo.Producto
import ar.edu.unq.services.ProveedorService
import io.javalin.http.Context
import org.bson.types.ObjectId
import java.util.*


class CompanyController(val backendProveedorService: ProveedorService) {

/*  ATRIBUTOS EN PROVEEDORES PARA CONSULTAR
    var id: ObjectId = ObjectId.get()
    var companyName: String = ""
    var companyImage: String = ""
    var facebook: String = ""
    var instagram: String = ""
    var web: String = ""
    var productos: MutableList<Producto> = emptyList<Producto>().toMutableList()
*/

/*  ATRIBUTOS EN PRODUCTOS PARA CONSULTAR
    var id: ObjectId = ObjectId()
    lateinit var idProveedor: ObjectId
    lateinit var itemName: String
    lateinit var description: String
    lateinit var image: String              //necesitamos imagenes list<>
    var stock: Int = 0
    var itemPrice: Int = 0
    var promotionalPrice: Int = 0
*/


    fun allCompanies(ctx: Context) {

    var companiesList = backendProveedorService.recuperarATodosLosProveedores()
    ctx.status(200)
    ctx.json(companiesList)
    }

    fun imagesCompanies(ctx: Context) {

        var imagesC = backendProveedorService.recuperarATodosLosProveedores().map { CompanyImageViewMapper(it.companyImage) }
        ctx.status(200)
        ctx.json(imagesC)
    }

    fun namesCompanies(ctx: Context) {

        var namesC = backendProveedorService.recuperarATodosLosProveedores().map { CompanyNameViewMapper(it.companyName) }
        ctx.status(200)
        ctx.json(namesC)
    }

    fun producstBestSellers(ctx: Context) {
        /*traer los productos mas vendidos  EN ESTE CASO ME TRAE EL PRIMERO DE CADA EMPRESA
    * DEBE IMPLEMENTARSE DESDE EL BACKEND*/
        var bestSellersP = backendProveedorService.recuperarATodosLosProveedores().map{ ProductsViewMapper(it.productos.first().id, it.productos.first().idProveedor, it.productos.first().itemName,
            it.productos.first().description,
            mutableListOf(it.productos.first().image), //debe cambiar !!!!!
            it.productos.first().stock,
            it.productos.first().itemPrice,
            it.productos.first().promotionalPrice) }
        ctx.status(200)
        ctx.json(bestSellersP)
    }

    fun productsNewest(ctx: Context) {
        /*traer los productos mas nuevos  EN ESTE CASO ME TRAE EL ULTIMO DE CADA EMPRESA
        * DEBE IMPLEMENTARSE DESDE EL BACKEND*/
        var newestP = backendProveedorService.recuperarATodosLosProveedores().map{ ProductsViewMapper(it.productos.last().id, it.productos.last().idProveedor, it.productos.last().itemName,
            it.productos.last().description,
            mutableListOf(it.productos.last().image), //debe cambiar !!!!!
            it.productos.last().stock,
            it.productos.last().itemPrice,
            it.productos.last().promotionalPrice) }
        ctx.status(200)
        ctx.json(newestP)
    }

    fun productsWPromoPrice(ctx: Context) {
        /*traer los productos con precio promocional  EN ESTE CASO ME TRAE UNO RANDOM DE CADA EMPRESA
        * DEBE IMPLEMENTARSE DESDE EL BACKEND*/
        var newestP = backendProveedorService.recuperarATodosLosProveedores().map{ ProductsViewMapper(it.productos.random().id, it.productos.random().idProveedor, it.productos.random().itemName,
            it.productos.random().description,
            mutableListOf(it.productos.random().image), //debe cambiar !!!!!
            it.productos.random().stock,
            it.productos.random().itemPrice,
            it.productos.random().promotionalPrice) }
        ctx.status(200)
        ctx.json(newestP)
    }

    fun allProducts(ctx: Context) {

        var productsLists = backendProveedorService.recuperarATodosLosProveedores().map { it.productos }
        var allProducts = makeListFromListofList(productsLists)

        var allP = allProducts!!.map{ ProductsViewMapper(it.id, it.idProveedor, it.itemName,
            it.description,
            mutableListOf(it.image), //debe cambiar !!!!!
            it.stock,
            it.itemPrice,
            it.promotionalPrice) }
        ctx.status(200)
        ctx.json(allP)
    }

    //funciones auxiliares
    fun <E> makeListFromListofList(iter: List<List <E>>): List<E>? {
        val list: MutableList<E> = ArrayList()
        for (item in iter) {
            item.forEach { list.add(it) }
        }
        return list
    }
}



//data class BannersRelatedViewMapper(val banners: Collection<BannerRelatedData>)
//    open fun toBannerData(lista: MutableCollection<Content>): List<BannerRelatedData> {
//        return lista.map { BannerRelatedData(it.id, it.description, it.title, evalBool(it.state), it.poster) }
//}
//data class BannerRelatedData (val id: String, val description: String, val title: String, val state: Boolean, val poster: String)


// orderByLowerPrice
//orderByHigherPrice
//orderByOldest
//orderByNewest
//orderByBestSellers
//orderByAlphabeticDesc
//orderByAlphabeticAsc