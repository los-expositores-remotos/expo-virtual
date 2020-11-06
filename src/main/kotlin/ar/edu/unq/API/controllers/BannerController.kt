package ar.edu.unq.API.controllers


import ar.edu.unq.API.BannerRegisterMapper
import ar.edu.unq.API.BannerViewMapper
import ar.edu.unq.API.OkResultMapper
import ar.edu.unq.modelo.banner.Banner
import ar.edu.unq.modelo.banner.BannerCategory
import ar.edu.unq.services.ProductoService
import ar.edu.unq.services.ProveedorService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter


class BannerController(val backendProveedorService: ProveedorService, backendProductoService : ProductoService) {

    val aux: AuxiliaryFunctions = AuxiliaryFunctions(backendProveedorService, backendProductoService)

    var idBanners: Int = 500

    fun allBanners(ctx: Context) {
        val bannerlist: MutableList<BannerViewMapper> = mutableListOf()
        var banners: List<Banner> = instanciarBannersDesdeJson().filter { it.category == BannerCategory.HOME }
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
/*
        var imagesList = backend.banners.map { BannerImageViewMapper(it.id.toString(), it.image) }
        ctx.status(200)
        ctx.json(imagesList)
        */

    fun addBanner(ctx: Context) {
        try {
            val newBanner = ctx.bodyValidator<BannerRegisterMapper>()
                    .check(
                            { it.image != null },
                            "Invalid body : image is required"
                    )
                    .get()
            val banner = Banner(newBanner.image!!, BannerCategory.HOME)
            println(banner)
            val bannersWUp = agregoBannerABanners(banner, instanciarBannersDesdeJson())
            writeFileWUpdateBanners(bannersWUp)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: KotlinNullPointerException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun deleteBanner(ctx: Context) {

        try {
            val id = ctx.pathParam("bannerId")
            val bannersWDel = eleminoBannerDeBanners(id, instanciarBannersDesdeJson())
            writeFileWUpdateBanners(bannersWDel)
            ctx.status(204)
            ctx.json(OkResultMapper("ok"))
        } catch (e: KotlinNullPointerException) {
            throw BadRequestResponse(e.message.toString())
        }/*
        ////        IMPLE PARA PROD
        try {
            val id = ctx.pathParam("bannerId")
            backend.removeBanner(id)
            ctx.status(204)
        } catch (e: ExistsException) {
            throw BadRequestResponse(e.message.toString())
        }
        /////
      */
    }

    fun getSchedule(ctx: Context) {
        try {
            val bannerView: BannerViewMapper
            var banner: Banner = instanciarBannersDesdeJson().find { it.category == BannerCategory.SCHEDULE }!!
            println(banner)
            bannerView = BannerViewMapper(
                    banner.id.toString(),
                    banner.image,
                    banner.category.toString()
            )
            ctx.status(200)
            ctx.json(bannerView)

            /*
        ////        IMPLE PARA PROD
        val scheduleBanner = backendBannerService.getBannerByCategory("schedule")
        val scheduleResult = aux.bannerClassListToBannerViewList(scheduleBanner as MutableCollection<Banner>)
        ctx.status(200)
        ctx.json(scheduleResult)*/
            /////
        } catch (e: KotlinNullPointerException) {
            throw BadRequestResponse(e.message.toString())
        }
    }
    fun addScheduleBanner(ctx: Context) {
        try {
            val newBanner = ctx.bodyValidator<BannerRegisterMapper>()
                    .check(
                            { it.image != null },
                            "Invalid body : image is required"
                    )
                    .get()
            val bannerSchedule = Banner(newBanner.image!!, BannerCategory.SCHEDULE)
            println(bannerSchedule)
            val bannersWUp = agregoBannerABanners(bannerSchedule, instanciarBannersDesdeJson())
            writeFileWUpdateBanners(bannersWUp)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: KotlinNullPointerException) {
            throw BadRequestResponse(e.message.toString())
        }    /*
        ////        IMPLE PARA PROD
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
        /////
    }

    fun getOnlineClassesBanner(ctx: Context) {
        val bannerViewlist: MutableList<BannerViewMapper> = mutableListOf()
        var banners: List<Banner> = instanciarBannersDesdeJson().filter { it.category == BannerCategory.CLASS }
        println(banners)
        banners.forEach {
            bannerViewlist.add(
                    BannerViewMapper(
                            it.id.toString(),
                            it.image,
                            it.category.toString()
                    )
            )
        }
        ctx.status(200)
        ctx.json(bannerViewlist)/*
     ////        IMPLE PARA PROD
    val onlineClasesBanner = backendBannerService.getBannerByCategory("classes")
    var allOnlineClassesBanner = aux.bannerClassListToBannerViewList(onlineClasesBanner as MutableCollection<Banner>)
    ctx.status(200)
    ctx.json(
             mapOf(
                     "Online Classes" to allOnlineClassesBanner))
    ///// */
    }

    fun addOnlineClassBanner(ctx: Context) {
        try {
            val newBanner = ctx.bodyValidator<BannerRegisterMapper>()
                    .check(
                            { it.image != null },
                            "Invalid body : image is required"
                    )
                    .get()
            val bannerClass = Banner(newBanner.image!!, BannerCategory.CLASS)
            println(bannerClass)
            val bannersWUp = agregoBannerABanners(bannerClass, instanciarBannersDesdeJson())
            writeFileWUpdateBanners(bannersWUp)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: KotlinNullPointerException) {
            throw BadRequestResponse(e.message.toString())
        }
        /*
             ////        IMPLE PARA PROD
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
 }
     /////
 */
    }

    fun getOnlineClassBanner(ctx: Context) {
        val bannerView: BannerViewMapper
        try {
            val id = ctx.pathParam("classeId")
            var banner: Banner = instanciarBannersDesdeJson().find { it.id.equals(id) }!!
            println(banner)
            bannerView = BannerViewMapper(
                    banner.id.toString(),
                    banner.image,
                    banner.category.toString()
            )
            ctx.status(200)
            ctx.json(bannerView)/*

 val onlineClassId: String = ctx.pathParam("classeId")
 val onlineClassBanner = backendBannerService.getBannerById("onlineClassId")
 var resultOnlineClassBanner = aux.bannerClassToBannerView(onlineClassBanner as Banner)
 ctx.status(200)
 ctx.json(
         resultOnlineClassBanner)
 */
        } catch (e: KotlinNullPointerException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun getCourrierBanner(ctx: Context){
        try {
            val bannerView: BannerViewMapper
            var banner: Banner = instanciarBannersDesdeJson().find { it.category == BannerCategory.COURRIER }!!
            println(banner)
            bannerView = BannerViewMapper(
                    banner.id.toString(),
                    banner.image,
                    banner.category.toString()
            )
            ctx.status(200)
            ctx.json(bannerView)

            /*
        ////        IMPLE PARA PROD
        val scheduleBanner = backendBannerService.getBannerByCategory("courrier")
        val scheduleResult = aux.bannerClassListToBannerViewList(scheduleBanner as MutableCollection<Banner>)
        ctx.status(200)
        ctx.json(scheduleResult)*/
            /////
        } catch (e: KotlinNullPointerException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun getPaymentMethodsBanner(ctx: Context){
        val bannerViewlist: MutableList<BannerViewMapper> = mutableListOf()
        var banners: List<Banner> = instanciarBannersDesdeJson().filter { it.category == BannerCategory.PAYMENTMETHODS }
        println(banners)
        banners.forEach {
            bannerViewlist.add(
                    BannerViewMapper(
                            it.id.toString(),
                            it.image,
                            it.category.toString()
                    )
            )
        }
        ctx.status(200)
        ctx.json(bannerViewlist)/*
     ////        IMPLE PARA PROD
    val onlineClasesBanner = backendBannerService.getBannerByCategory("paymentMethods")
    var allOnlineClassesBanner = aux.bannerClassListToBannerViewList(onlineClasesBanner as MutableCollection<Banner>)
    ctx.status(200)
    ctx.json(
             mapOf(
                     "Payment methods" to allOnlineClassesBanner))
    ///// */
    }

    //////////////////////
    //FUNCIONES AUXILIARES
    //////////////////////

    private fun readFile(): String {
        return object {}::class.java.classLoader.getResource("banners.json")!!.readText()
    }

    fun getIdBanner(): Int {
        val lastId = idBanners
/*        val banners: MutableList<Banner> = this.instanciarBannersDesdeJson()
        val lastIndex: Int = banners.lastIndex
        val lastId: Int = banners[lastIndex].id!!
*/
        return lastId + 1
    }

    fun instanciarBannersDesdeJson(): MutableList<Banner> {
        val bannersString = readFile()
        val bannerDataType = object : TypeToken<MutableList<Banner>>() {}.type
        val banners: MutableList<Banner> = Gson().fromJson(bannersString, bannerDataType)
        return banners
    }

    fun agregoBannerABanners(banner: Banner, banners: MutableList<Banner> ): MutableList<Banner> {
        val bannersWAdd: MutableList<Banner> = banners
        bannersWAdd.add(banner)
        return bannersWAdd
    }

    fun eleminoBannerDeBanners(bannerId: String, banners: MutableList<Banner> ): MutableList<Banner> {
        val bannersWDel: MutableList<Banner> = banners
        bannersWDel.removeIf { it.equals(bannerId) }
        return bannersWDel
    }

    private fun writeFileWUpdateBanners(banners: MutableList<Banner>) {
        val jsonString = Gson().toJson(banners)  // json string
        val file = File("./src/main/resources/banners.json")

        if (!file.exists()) {
            file.createNewFile()
        }

        val bufferToWrite = BufferedWriter(FileWriter("./src/main/resources/banners.json"))
        bufferToWrite.write(jsonString)
        bufferToWrite.close()
    }
}
