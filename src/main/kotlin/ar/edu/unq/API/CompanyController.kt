package ar.edu.unq.API

import ar.edu.unq.agregarVariosProveedores
import modelo.Expo
import io.javalin.http.Context

class CompanyController(val backend: Expo) {


    fun imagesCompanies(ctx: Context) {

        var imagesC = backend.companies.map { it.imagenDeLaEmpresa }
        ctx.status(200)
        ctx.json(imagesC)
    }

    fun namesCompanies(ctx: Context) {

        var namesC = backend.companies.map { it.nombreDeEmpresa }
        ctx.status(200)
        ctx.json(namesC)
    }

    fun producstBestSellers(ctx: Context) {
  /*
        var bestSellersP = backend.//traer los productos mas vendidos
        ctx.status(200)
        ctx.json(bestSellersP)
  */  }

    fun productsNewest(ctx: Context) {
        /*
         var bestSellersP = backend.//traer los productos mas vendidos
         ctx.status(200)
         ctx.json(bestSellersP)
   */  }

    fun productsWPromoPrice(ctx: Context) {
        /*
         var bestSellersP = backend.//traer los productos mas vendidos
         ctx.status(200)
         ctx.json(bestSellersP)
*/  }



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