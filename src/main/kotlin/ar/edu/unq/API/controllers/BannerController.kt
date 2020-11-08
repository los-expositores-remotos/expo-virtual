package ar.edu.unq.API.controllers


import ar.edu.unq.API.BannerRegisterMapper
import ar.edu.unq.API.BannerViewMapper
import ar.edu.unq.API.OkResultMapper
import ar.edu.unq.modelo.banner.Banner
import ar.edu.unq.modelo.banner.BannerCategory
import ar.edu.unq.services.BannerService
import ar.edu.unq.services.ProductoService
import ar.edu.unq.services.ProveedorService
import ar.edu.unq.services.impl.exceptions.BannerExistenteException
import ar.edu.unq.services.impl.exceptions.BannerInexistenteException
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context


class BannerController
    (
    val backendBannerService: BannerService,
    val backendProveedorService: ProveedorService,
    backendProductoService: ProductoService
) {

    val aux: AuxiliaryFunctions = AuxiliaryFunctions(backendProveedorService, backendProductoService)

    var idBanners: Int = 500

    private fun banners(ctx: Context, bannerCategory: BannerCategory){
        val bannerlist: MutableList<BannerViewMapper> = mutableListOf()
        var banners: List<Banner> = this.backendBannerService.recuperarTodosLosBanners(bannerCategory)
        println(banners)
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

    private fun addBanner(ctx: Context, bannerCategory: BannerCategory) {
        try {
            val newBanner = ctx.bodyValidator<BannerRegisterMapper>()
                .check(
                    { it.image != null },
                    "Invalid body : image is required"
                )
                .get()
            val banner = Banner(newBanner.image!!, bannerCategory)
            this.backendBannerService.crearBanner(banner)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: BannerExistenteException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun homeBanners(ctx: Context) {
        this.banners(ctx, BannerCategory.HOME)
    }

    fun addHomeBanner(ctx: Context) {
        this.addBanner(ctx, BannerCategory.HOME)
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

    fun scheduleBanners(ctx: Context) {
        this.banners(ctx, BannerCategory.SCHEDULE)
    }

    fun addScheduleBanner(ctx: Context) {
        this.addBanner(ctx, BannerCategory.SCHEDULE)
    }

    fun classBanners(ctx: Context) {
        this.banners(ctx, BannerCategory.CLASS)
    }

    fun addClassBanner(ctx: Context) {
        this.addBanner(ctx, BannerCategory.CLASS)
    }

    fun getClassBanner(ctx: Context) {
        val bannerView: BannerViewMapper
        try {
            val id = ctx.pathParam("classeId")
            var banner: Banner = this.backendBannerService.recuperarBanner(id)
            println(banner)
            bannerView = BannerViewMapper(
                    banner.id.toString(),
                    banner.image,
                    banner.category.toString()
            )
            ctx.status(200)
            ctx.json(bannerView)
        } catch (e: BannerInexistenteException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun courrierBanners(ctx: Context) {
        this.banners(ctx, BannerCategory.COURRIER)
    }

    fun paymentMethodsBanners(ctx: Context) {
        this.banners(ctx, BannerCategory.PAYMENTMETHODS)
    }
}
