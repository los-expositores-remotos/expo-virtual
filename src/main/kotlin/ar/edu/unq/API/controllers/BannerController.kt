package ar.edu.unq.API.controllers


import ar.edu.unq.API.BannerRegisterMapper
import ar.edu.unq.API.BannerViewMapper
import ar.edu.unq.API.ExistsException
import ar.edu.unq.API.OkResultMapper
import ar.edu.unq.modelo.Banner
import ar.edu.unq.services.ProveedorService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import java.io.BufferedWriter
import java.io.FileWriter


class BannerController(val backendProveedorService: ProveedorService) {

    private fun readFile(name: String): String {
        return object {}::class.java.classLoader.getResource(name).readText()
    }

    fun instanciarBannersDesdeJson() : MutableList<Banner>{
        val bannersString = readFile("banners.json")
        val bannerDataType = object : TypeToken<MutableList<Banner>>() {}.type
        val banners: MutableList<Banner> = Gson().fromJson(bannersString, bannerDataType)
        return banners
    }

    private fun writeFile(banner: Banner) {
        val banners: MutableList<Banner> = instanciarBannersDesdeJson()
        banners.add(banner)
        val jsonString = Gson().toJson(banners)  // json string
        val bufferToWrite = BufferedWriter(FileWriter("banners.json"))
        bufferToWrite.write(jsonString)
        bufferToWrite.close()
    }

    fun allBanners(ctx: Context) {
        val bannerlist: MutableList<BannerViewMapper> = mutableListOf()
        var banners: MutableList<Banner> = instanciarBannersDesdeJson()
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
    }

/*
        var imagesList = backend.banners.map { BannerImageViewMapper(it.id.toString(), it.image) }
        ctx.status(200)
        ctx.json(imagesList)
        */
    fun agregarBanner(ctx: Context) {

        try {
            val newBanner = ctx.bodyValidator<BannerRegisterMapper>()
                .check(
                    { it.image != null && it.category != null },
                    "Invalid body : image and categroy are required"
                )
                .get()
            val banner = Banner(
                newBanner.image!!, newBanner.category!!)
            println(banner)
            writeFile(banner)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: ExistsException) {
            throw BadRequestResponse(e.message.toString())
        }
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

    } */
    }

    fun getSchedule(ctx: Context){

    }

    fun addScheduleBanner(ctx: Context){

    }

    fun deleteScheduleBanner(ctx: Context){

    }

    fun getOnlineClassesBanner(ctx: Context){

    }

    fun addOnlineClassBanner(ctx: Context){

    }

    fun getOnlineClassBanner(ctx: Context){

    }

    fun deleteOnlineClassBanner(ctx: Context){

    }
}
