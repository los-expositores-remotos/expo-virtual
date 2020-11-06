package ar.edu.unq.services

import ar.edu.unq.modelo.banner.Banner
import ar.edu.unq.modelo.banner.BannerCategory
import org.junit.After
import org.junit.Before
import org.junit.Test

class BannerServiceImplTest {

    @Before
    fun setUp(){

    }

    @Test
    fun testCreacionDeBanner(banner: Banner){
        val banner = Banner("www.images.com/imagen.png", BannerCategory.HOME)
        this.bannerService.crearBanner(banner)
    }

    @Test(expected = BannerExistenteException::class)
    fun testCreacionDeBannerYaExistente(banner: Banner){

    }

    @Test
    fun testRecuperacionDeBanner(id: String): Banner{

    }

    @Test(expected = BannerInexistenteException::class)
    fun testRecuperacionDeBannerInexistente(id: String): Banner{

    }

    @Test
    fun testRecuperarTodosLosBanners(): List<Banner>{

    }

    @Test
    fun testRecuperarBannersHome(): List<Banner>{

    }

    @Test
    fun testRecuperarBannersSchedule(): List<Banner>{

    }

    @Test
    fun testRecuperarBannersClass(): List<Banner>{

    }

    @Test
    fun testRecuperarBannersCourrier(): List<Banner>{

    }

    @Test
    fun testRecuperarBannersPaymentMethods(): List<Banner>{

    }

    @After
    fun deleteAll(){

    }
}