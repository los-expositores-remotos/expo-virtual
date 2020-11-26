package ar.edu.unq.API.controllers


import ar.edu.unq.API.BannerRegisterMapper
import ar.edu.unq.API.BannerViewMapper
import ar.edu.unq.API.OkResultMapper
import ar.edu.unq.modelo.banner.Banner
import ar.edu.unq.modelo.banner.BannerCategory
import ar.edu.unq.services.BannerService
import ar.edu.unq.services.ProveedorService
import ar.edu.unq.services.impl.exceptions.BannerExistenteException
import ar.edu.unq.services.impl.exceptions.BannerInexistenteException
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context

class BannerController(
        val backendBannerService: BannerService,
        val backendProveedorService: ProveedorService
) {

    val aux: AuxiliaryFunctions = AuxiliaryFunctions()
    var idBanners: Int = 500

    fun banners(ctx: Context){
        val bannerlist: MutableList<BannerViewMapper> = mutableListOf()
        val banners: List<Banner> = this.backendBannerService.recuperarTodosLosBanners()
        banners.forEach {
            bannerlist.add(
                    BannerViewMapper(
                            it.id.toString(),
                            it.image,
                            it.category.toString()
                    )
            )
        }
        ctx.status(200)
        ctx.json(bannerlist)
    }

    fun bannersByCategory(ctx: Context){
        val bannerlist: MutableList<BannerViewMapper> = mutableListOf()
        val bannerCategory = BannerCategory.valueOf(ctx.pathParam("bannerCategory"))
        val banners: List<Banner> = this.backendBannerService.recuperarTodosLosBanners(bannerCategory)
        banners.forEach {
            bannerlist.add(
                    BannerViewMapper(
                            it.id.toString(),
                            it.image,
                            it.category.toString()
                    )
            )
        }
        ctx.status(200)
        ctx.json(bannerlist)
    }

    fun addBanner(ctx: Context) {
        try {
            val newBanner = ctx.bodyValidator<BannerRegisterMapper>()
                    .check(
                            { it.image != null && it.category != null && BannerCategory.isValid(it.category)},
                            "Invalid body : all fields are required. All fields must be valids"
                    )
                    .get()
            val banner = Banner(newBanner.image!!, BannerCategory.valueOf(newBanner.category!!))
            this.backendBannerService.crearBanner(banner)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: BannerExistenteException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun deleteBanner(ctx: Context) {
        try {
            val id = ctx.pathParam("bannerId")
            this.backendBannerService.borrarBanner(id)
            ctx.status(204)
            ctx.json(OkResultMapper("ok"))
        } catch (e: BannerInexistenteException) {
            throw BadRequestResponse(e.message.toString())
        }
    }
}