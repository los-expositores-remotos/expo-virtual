package ar.edu.unq.services

import ar.edu.unq.dao.BannerDAO
import ar.edu.unq.dao.mongodb.MongoBannerDAOImpl
import ar.edu.unq.modelo.banner.Banner
import ar.edu.unq.modelo.banner.BannerCategory
import ar.edu.unq.services.impl.BannerServiceImpl
import ar.edu.unq.services.impl.exceptions.BannerExistenteException
import ar.edu.unq.services.impl.exceptions.BannerInexistenteException
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner
import ar.edu.unq.services.runner.TransactionType
import org.bson.types.ObjectId
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class BannerServiceImplTest {

    private lateinit var banners: MutableSet<Banner>
    private lateinit var bannersHome: Set<Banner>
    private lateinit var bannersSchedule: Set<Banner>
    private lateinit var bannersClass: Set<Banner>
    private lateinit var bannersCourrier: Set<Banner>
    private lateinit var bannersPaymentMethods: Set<Banner>
    private lateinit var banner: Banner
    private val bannerService: BannerService = BannerServiceImpl(MongoBannerDAOImpl(), DataBaseType.TEST)
    private val bannerDAO: BannerDAO = MongoBannerDAOImpl()

    @Before
    fun setUp(){
        this.banners = emptySet<Banner>().toMutableSet()
        this.generarBanners()
        this.banner = this.banners.first()
        this.bannersHome = this.banners.filter { it.category == BannerCategory.HOME }.toSet()
        this.bannersClass = this.banners.filter { it.category == BannerCategory.CLASS }.toSet()
        this.bannersSchedule = this.banners.filter { it.category == BannerCategory.SCHEDULE }.toSet()
        this.bannersCourrier = this.banners.filter { it.category == BannerCategory.COURRIER }.toSet()
        this.bannersPaymentMethods = this.banners.filter { it.category == BannerCategory.PAYMENTMETHODS }.toSet()
    }

    @Test
    fun testCreacionDeBanner(){
        val banner = Banner("www.images.com/imagen.png", BannerCategory.HOME)
        this.bannerService.crearBanner(banner)
        assertEquals(banner, this.bannerService.recuperarBanner(banner.id.toString()))
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

    @Test
    fun testBorrarBanner(){
        this.bannerService.borrarBanner(this.banner.id.toString())
        val bannersRecuperados = this.bannerService.recuperarTodosLosBanners(BannerCategory.PAYMENTMETHODS)
        assertFalse(bannersRecuperados.contains(this.banner))
    }

    @Test(expected = BannerInexistenteException::class)
    fun testBorrarBannerInexistente(){
        this.bannerService.borrarBanner(ObjectId().toString())
    }

    @After
    fun deleteAll(){
        this.bannerService.deleteAll()
    }

    private fun generarBanners() {
        val i = 2
        BannerCategory.values().forEach {category ->
            (1..i).forEach {
                this.banners.add(Banner("www.images.com/banner$category$it.png", category))
            }
            i.inc()
        }
        TransactionRunner.runTrx({ this.bannerDAO.save(this.banners.toList()) }, listOf(TransactionType.MONGO), DataBaseType.TEST)
    }
}