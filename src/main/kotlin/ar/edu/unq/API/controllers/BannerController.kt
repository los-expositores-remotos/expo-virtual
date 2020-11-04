package ar.edu.unq.API.controllers


import ar.edu.unq.API.*
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import ar.edu.unq.modelo.Banner
import ar.edu.unq.modelo.Producto
import ar.edu.unq.services.ProductoService
import ar.edu.unq.services.ProveedorService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.javalin.core.util.FileUtil.readFile

class BannerController(val backendProveedorService: ProveedorService, backendProductoService : ProductoService) {

    val aux: AuxiliaryFunctions = AuxiliaryFunctions(backendProveedorService, backendProductoService)

    private fun readFile(name: String): String {
        return object {}::class.java.classLoader.getResource(name).readText()
    }
    fun allBanners(ctx: Context) {
        val bannerlist: MutableList<BannerViewMapper> = mutableListOf()
        val bannersString = readFile("banners.json")
        val bannerDataType = object : TypeToken<MutableList<Banner>>() {}.type
        val banners : MutableList<Banner> = Gson().fromJson(bannersString, bannerDataType)
        println(banners)
        banners.forEach {
            bannerlist.add(
                BannerViewMapper(
                    it.image
                )
            )
        }
        println(bannerlist)
        ctx.status(200)
        ctx.json(bannerlist)
/*
        var imagesList = backend.banners.map { BannerImageViewMapper(it.id.toString(), it.image) }
        ctx.status(200)
        ctx.json(imagesList)
        */
    }

    fun addBanner(ctx: Context) {
    /*
        try {
            val newBanner = ctx.bodyValidator<BannerRegisterMapper>()
                .check(
                    { it.image != null },
                    "Invalid body : image is required"
                )
                .get()
            val banner = Banner(
                backend.setBannerId(), newBanner.image!!)
            println(banner)
            backend.addBanner(banner)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: ExistsException) {
            throw BadRequestResponse(e.message.toString())
        }
*/
    }

    fun deleteBanner(ctx: Context) {
    /*
        try {
            val id = ctx.pathParam("bannerId")
            backend.removeBanner(id)
            ctx.status(204)
        } catch (e: ExistsException) {
            throw BadRequestResponse(e.message.toString())
        }
*/
    }

    fun getSchedule(ctx: Context){/*

        val scheduleBanner = backendBannerService.getBannerByCategory("schedule")
        val scheduleResult = aux.bannerClassListToBannerViewList(scheduleBanner as MutableCollection<Banner>)
        ctx.status(200)
        ctx.json(scheduleResult)*/
    }

    fun addScheduleBanner(ctx: Context){/*
        try {
            val newBanner = ctx.bodyValidator<BannerRegisterMapper>()
                    .check(
                            { it.image != null },
                            "Invalid body : image is required"
                    )
                    .get()
            val bannerSchedule = Banner(newBanner.image!!, "schedule")

            backendBannerService.addBanner(bannerSchedule)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: ExistsException) {
            throw BadRequestResponse(e.message.toString())
        }*/
    }

    fun deleteScheduleBanner(ctx: Context){/*
        try {
            val id = ctx.pathParam("bannerId")
            backendBannerService.removeBanner(id)
            ctx.status(204)
        } catch (e: ExistsException) {
            throw BadRequestResponse(e.message.toString())
        }*/
    }

    fun getOnlineClassesBanner(ctx: Context){
        /*
        val onlineClasesBanner = backendBannerService.getBannerByCategory("classes")
        var allOnlineClassesBanner = aux.bannerClassListToBannerViewList(onlineClasesBanner as MutableCollection<Banner>)
        ctx.status(200)
        ctx.json(
                mapOf(
                        "Online Classes" to allOnlineClassesBanner))
                        */
    }

    fun addOnlineClassBanner(ctx: Context){
        /*
        try {
            val newClassBanner = ctx.bodyValidator<BannerRegisterMapper>()
                    .check(
                            { it.image != null },
                            "Invalid body : image is required"
                    )
                    .get()
            val bannerClass = Banner(newClassBanner.image!!, "class")

            backendBannerService.addBanner(bannerClass)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: ExistsException) {
            throw BadRequestResponse(e.message.toString())
        }*/
    }

    fun getOnlineClassBanner(ctx: Context){
        /*
        val onlineClassId: String = ctx.pathParam("classeId")
        val onlineClassBanner = backendBannerService.getBannerById("onlineClassId")
        var resultOnlineClassBanner = aux.bannerClassToBannerView(onlineClassBanner as Banner)
        ctx.status(200)
        ctx.json(
                resultOnlineClassBanner)
        */
    }

    fun deleteOnlineClassBanner(ctx: Context){

    /*
        try {
            val id = ctx.pathParam("classeId")
            backendBannerService.removeBanner(id)
            ctx.status(204)
        } catch (e: ExistsException) {
            throw BadRequestResponse(e.message.toString())

     */
    }
}
