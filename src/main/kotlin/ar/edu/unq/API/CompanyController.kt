package ar.edu.unq.API

//import ar.edu.unq.agregarVariosProveedores
import modelo.Expo
import io.javalin.http.Context

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
        var bestSellersP = backend.companies.map{ ProductsViewMapper(it.productos.first().nombreDelArticulo,
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
        var newestP = backend.companies.map{ ProductsViewMapper(it.productos.last().nombreDelArticulo,
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
        var newestP = backend.companies.map{ ProductsViewMapper(it.productos.random().nombreDelArticulo,
                it.productos.random().description,
                it.productos.random().imagenes,
                it.productos.random().stock,
                it.productos.random().precio,
                it.productos.random().precioPromocional) }
        ctx.status(200)
        ctx.json(newestP)
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

}