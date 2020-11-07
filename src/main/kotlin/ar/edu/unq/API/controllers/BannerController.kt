package ar.edu.unq.API.controllers


import ar.edu.unq.API.BannerRegisterMapper
import ar.edu.unq.API.BannerViewMapper
import ar.edu.unq.API.OkResultMapper
import ar.edu.unq.modelo.banner.Banner
import ar.edu.unq.modelo.banner.BannerCategory
import ar.edu.unq.services.BannerService
import ar.edu.unq.services.ProductoService
import ar.edu.unq.services.ProveedorService
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context


class BannerController(
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
            println(banner)
            this.backendBannerService.crearBanner(banner)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: KotlinNullPointerException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun homeBanners(ctx: Context) {
        this.banners(ctx, BannerCategory.HOME)
    }

/*
        var imagesList = backend.banners.map { BannerImageViewMapper(it.id.toString(), it.image) }
        ctx.status(200)
        ctx.json(imagesList)
        */

    fun addHomeBanner(ctx: Context) {
        this.addBanner(ctx, BannerCategory.HOME)
    }

    fun deleteBanner(ctx: Context) {
        try {
            val id = ctx.pathParam("bannerId")
            this.backendBannerService.borrarBanner(id)
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

    fun scheduleBanners(ctx: Context) {
        this.banners(ctx, BannerCategory.SCHEDULE)
    }
            /*
        ////        IMPLE PARA PROD
        val scheduleBanner = backendBannerService.getBannerByCategory("schedule")
        val scheduleResult = aux.bannerClassListToBannerViewList(scheduleBanner as MutableCollection<Banner>)
        ctx.status(200)
        ctx.json(scheduleResult)*/
            /////

    fun addScheduleBanner(ctx: Context) {
        this.addBanner(ctx, BannerCategory.SCHEDULE)
    }
    /*
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

    fun classBanners(ctx: Context) {
        this.banners(ctx, BannerCategory.CLASS)
    }
        /*
     ////        IMPLE PARA PROD
    val onlineClasesBanner = backendBannerService.getBannerByCategory("classes")
    var allOnlineClassesBanner = aux.bannerClassListToBannerViewList(onlineClasesBanner as MutableCollection<Banner>)
    ctx.status(200)
    ctx.json(
             mapOf(
                     "Online Classes" to allOnlineClassesBanner))
    ///// */

    fun addClassBanner(ctx: Context) {
        this.addBanner(ctx, BannerCategory.CLASS)
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

    fun courrierBanners(ctx: Context) {
        this.banners(ctx, BannerCategory.COURRIER)
    }
            /*
        ////        IMPLE PARA PROD
        val scheduleBanner = backendBannerService.getBannerByCategory("courrier")
        val scheduleResult = aux.bannerClassListToBannerViewList(scheduleBanner as MutableCollection<Banner>)
        ctx.status(200)
        ctx.json(scheduleResult)*/
            /////


    fun paymentMethodsBanners(ctx: Context) {
        this.banners(ctx, BannerCategory.PAYMENTMETHODS)
    }
        /*
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

//    private fun readFile(): String {
//        return object {}::class.java.classLoader.getResource("banners.json")!!.readText()
//    }
//
//    fun getIdBanner(): Int {
//        val lastId = idBanners
///*        val banners: MutableList<Banner> = this.instanciarBannersDesdeJson()
//        val lastIndex: Int = banners.lastIndex
//        val lastId: Int = banners[lastIndex].id!!
//*/
//        return lastId + 1
//    }
//
//    fun instanciarBannersDesdeJson(): MutableList<Banner> {
//        val bannersString = readFile()
//        val bannerDataType = object : TypeToken<MutableList<Banner>>() {}.type
//        val banners: MutableList<Banner> = Gson().fromJson(bannersString, bannerDataType)
//        return banners
//    }
//
//    fun agregoBannerABanners(banner: Banner, banners: MutableList<Banner> ): MutableList<Banner> {
//        val bannersWAdd: MutableList<Banner> = banners
//        bannersWAdd.add(banner)
//        return bannersWAdd
//    }
//
//    fun eleminoBannerDeBanners(bannerId: String, banners: MutableList<Banner> ): MutableList<Banner> {
//        val bannersWDel: MutableList<Banner> = banners
//        bannersWDel.removeIf { it.equals(bannerId) }
//        return bannersWDel
//    }
//
//    private fun writeFileWUpdateBanners(banners: MutableList<Banner>) {
//        val jsonString = Gson().toJson(banners)  // json string
//        val file = File("./src/main/resources/banners.json")
//
//        if (!file.exists()) {
//            file.createNewFile()
//        }
//
//        val bufferToWrite = BufferedWriter(FileWriter("./src/main/resources/banners.json"))
//        bufferToWrite.write(jsonString)
//        bufferToWrite.close()
//    }

