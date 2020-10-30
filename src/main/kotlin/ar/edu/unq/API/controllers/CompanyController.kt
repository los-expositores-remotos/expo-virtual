package ar.edu.unq.API.controllers

import ar.edu.unq.API.BannerImageViewMapper
import ar.edu.unq.API.CompanyImageViewMapper
import ar.edu.unq.API.CompanyNameViewMapper
import ar.edu.unq.API.ProductsViewMapper
import io.javalin.http.Context
import modelo.Expo
import java.util.*


class CompanyController(val backend: Expo) {


    fun imagesCompanies(ctx: Context) {

        var imagesC = backend.companies.map { CompanyImageViewMapper(it.imagenDeLaEmpresa) }
        ctx.status(200)
        ctx.json(imagesC)
    }

    fun namesCompanies(ctx: Context) {

        var namesC = backend.companies.map { CompanyNameViewMapper(it.nombreDeEmpresa) }
        ctx.status(200)
        ctx.json(namesC)
    }

    fun producstBestSellers(ctx: Context) {
    /*traer los productos mas vendidos  EN ESTE CASO ME TRAE EL PRIMERO DE CADA EMPRESA
    * DEBE IMPLEMENTARSE DESDE EL BACKEND*/
        var bestSellersP = backend.companies.map{ ProductsViewMapper(it.productos.first().id.toString(),
                it.productos.first().idProveedor.toString(),
                it.productos.first().nombreDelArticulo,
                it.productos.first().description,
                it.productos.first().imagenes,
                it.productos.first().stock,
                it.productos.first().precio,
                it.productos.first().precioPromocional) }
        ctx.status(200)
        ctx.json(bestSellersP)
    }

    fun productsNewest(ctx: Context) {
        /*traer los productos mas nuevos  EN ESTE CASO ME TRAE EL ULTIMO DE CADA EMPRESA
        * DEBE IMPLEMENTARSE DESDE EL BACKEND*/
        var newestP = backend.companies.map{ ProductsViewMapper(it.productos.last().id.toString(),
                it.productos.last().idProveedor.toString(),
                it.productos.last().nombreDelArticulo,
                it.productos.last().description,
                it.productos.last().imagenes,
                it.productos.last().stock,
                it.productos.last().precio,
                it.productos.last().precioPromocional) }
        ctx.status(200)
        ctx.json(newestP)
    }

    fun productsWPromoPrice(ctx: Context) {
        /*traer los productos con precio promocional  EN ESTE CASO ME TRAE UNO RANDOM DE CADA EMPRESA
        * DEBE IMPLEMENTARSE DESDE EL BACKEND*/
        var newestP = backend.companies.map{ ProductsViewMapper(it.productos.random().id.toString(),
                it.productos.random().idProveedor.toString(),
                it.productos.random().nombreDelArticulo,
                it.productos.random().description,
                it.productos.random().imagenes,
                it.productos.random().stock,
                it.productos.random().precio,
                it.productos.random().precioPromocional) }
        ctx.status(200)
        ctx.json(newestP)
    }

    fun allProducts(ctx: Context) {

        var productsLists = backend.companies.map { it.productos }
        var allProducts = makeListFromListofList(productsLists)

        var allP = allProducts!!.map{ ProductsViewMapper(it.id.toString(),
                it.idProveedor.toString(),
                it.nombreDelArticulo,
                it.description,
                it.imagenes,
                it.stock,
                it.precio,
                it.precioPromocional) }
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