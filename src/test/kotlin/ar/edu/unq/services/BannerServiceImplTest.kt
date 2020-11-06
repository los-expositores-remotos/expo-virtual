package ar.edu.unq.services

import ar.edu.unq.dao.mongodb.MongoBannerDAOImpl
import ar.edu.unq.modelo.banner.Banner
import ar.edu.unq.modelo.banner.BannerCategory
import ar.edu.unq.services.impl.BannerServiceImpl
import ar.edu.unq.services.impl.exceptions.BannerExistenteException
import ar.edu.unq.services.impl.exceptions.BannerInexistenteException
import ar.edu.unq.services.runner.DataBaseType
import org.bson.types.ObjectId
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class BannerServiceImplTest {

    private lateinit var banners: Set<Banner>
    private lateinit var bannersHome: Set<Banner>
    private lateinit var bannersSchedule: Set<Banner>
    private lateinit var bannersClass: Set<Banner>
    private lateinit var bannersCourrier: Set<Banner>
    private lateinit var bannersPaymentMethods: Set<Banner>
    private lateinit var banner: Banner
    private val bannerService: BannerService = BannerServiceImpl(MongoBannerDAOImpl(), DataBaseType.TEST)

    @Before
    fun setUp(){
        this.banner = Banner("www.images.com/bannerHome1.png", BannerCategory.HOME)
        this.banners = setOf(
            this.banner,
            Banner("www.images.com/bannerHome2.png", BannerCategory.HOME),
            Banner("www.images.com/bannerSchedule1.png", BannerCategory.SCHEDULE),
            Banner("www.images.com/bannerSchedule2.png", BannerCategory.SCHEDULE),
            Banner("www.images.com/bannerSchedule3.png", BannerCategory.SCHEDULE),
            Banner("www.images.com/bannerClass1.png", BannerCategory.CLASS),
            Banner("www.images.com/bannerClass2.png", BannerCategory.CLASS),
            Banner("www.images.com/bannerClass3.png", BannerCategory.CLASS),
            Banner("www.images.com/bannerClass4.png", BannerCategory.CLASS),
            Banner("www.images.com/bannerCourrier1.png", BannerCategory.COURRIER),
            Banner("www.images.com/bannerCourrier2.png", BannerCategory.COURRIER),
            Banner("www.images.com/bannerCourrier3.png", BannerCategory.COURRIER),
            Banner("www.images.com/bannerCourrier4.png", BannerCategory.COURRIER),
            Banner("www.images.com/bannerCourrier5.png", BannerCategory.COURRIER),
            Banner("www.images.com/bannerPaymenteMethods1.png", BannerCategory.PAYMENTMETHODS),
            Banner("www.images.com/bannerPaymenteMethods2.png", BannerCategory.PAYMENTMETHODS),
            Banner("www.images.com/bannerPaymenteMethods3.png", BannerCategory.PAYMENTMETHODS),
            Banner("www.images.com/bannerPaymenteMethods4.png", BannerCategory.PAYMENTMETHODS),
            Banner("www.images.com/bannerPaymenteMethods5.png", BannerCategory.PAYMENTMETHODS),
            Banner("www.images.com/bannerPaymenteMethods6.png", BannerCategory.PAYMENTMETHODS)
        )
        this.bannersHome = this.banners.filter { it.category == BannerCategory.HOME }.toSet()
        this.bannersClass = this.banners.filter { it.category == BannerCategory.CLASS }.toSet()
        this.bannersSchedule = this.banners.filter { it.category == BannerCategory.SCHEDULE }.toSet()
        this.bannersCourrier = this.banners.filter { it.category == BannerCategory.COURRIER }.toSet()
        this.bannersPaymentMethods = this.banners.filter { it.category == BannerCategory.PAYMENTMETHODS }.toSet()
        this.banners.forEach { this.bannerService.crearBanner(it) }
    }

    @Test
    fun testCreacionDeBanner(){
        val banner = Banner("www.images.com/imagen.png", BannerCategory.HOME)
        this.bannerService.crearBanner(banner)
    }

    @Test(expected = BannerExistenteException::class)
    fun testCreacionDeBannerYaExistente(){
        this.bannerService.crearBanner(this.banner)
    }

    @Test
    fun testRecuperacionDeBanner(){
        val bannerRecuperado = this.bannerService.recuperarBanner(this.banner.id.toString())
        assertEquals(this.banner, bannerRecuperado)
    }

    @Test(expected = BannerInexistenteException::class)
    fun testRecuperacionDeBannerInexistente(){
        this.bannerService.recuperarBanner(ObjectId().toString())
    }

    @Test
    fun testRecuperarTodosLosBanners(){
        val bannersRecuperados = this.bannerService.recuperarTodosLosBanners()
        assertEquals(this.banners, bannersRecuperados.toSet())
    }

    @Test
    fun testRecuperarBannersHome(){
        val bannersRecuperados = this.bannerService.recuperarTodosLosBanners(BannerCategory.HOME)
        assertEquals(this.bannersHome, bannersRecuperados.toSet())
    }

    @Test
    fun testRecuperarBannersSchedule(){
        val bannersRecuperados = this.bannerService.recuperarTodosLosBanners(BannerCategory.SCHEDULE)
        assertEquals(this.bannersSchedule, bannersRecuperados.toSet())
    }

    @Test
    fun testRecuperarBannersClass(){
        val bannersRecuperados = this.bannerService.recuperarTodosLosBanners(BannerCategory.CLASS)
        assertEquals(this.bannersClass, bannersRecuperados.toSet())
    }

    @Test
    fun testRecuperarBannersCourrier(){
        val bannersRecuperados = this.bannerService.recuperarTodosLosBanners(BannerCategory.COURRIER)
        assertEquals(this.bannersCourrier, bannersRecuperados.toSet())
    }

    @Test
    fun testRecuperarBannersPaymentMethods(){
        val bannersRecuperados = this.bannerService.recuperarTodosLosBanners(BannerCategory.PAYMENTMETHODS)
        assertEquals(this.bannersPaymentMethods, bannersRecuperados.toSet())
    }

    @After
    fun deleteAll(){
        this.bannerService.deleteAll()
    }
}