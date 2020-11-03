package ar.edu.unq.API.controllers


import ar.edu.unq.API.*
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import ar.edu.unq.modelo.Banner
import ar.edu.unq.services.ProveedorService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.javalin.core.util.FileUtil.readFile

class BannerController(val backendProveedorService: ProveedorService) {

    private fun readFile(name: String): String {
        return object {}::class.java.classLoader.getResource(name).readText()
    }
    fun allBanners(ctx: Context) {
        println("entree")
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

    fun agregarBanner(ctx: Context) {
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
}
