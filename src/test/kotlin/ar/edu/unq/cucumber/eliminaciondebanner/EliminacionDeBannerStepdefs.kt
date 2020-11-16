package ar.edu.unq.cucumber.eliminaciondebanner

import ar.edu.unq.dao.mongodb.MongoBannerDAOImpl
import ar.edu.unq.modelo.banner.Banner
import ar.edu.unq.modelo.banner.BannerCategory
import ar.edu.unq.services.impl.BannerServiceImpl
import ar.edu.unq.services.runner.DataBaseType
import cucumber.api.java.After
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class EliminacionDeBannerStepdefs {
    private lateinit var banner: Banner
    private val bannerService = BannerServiceImpl(MongoBannerDAOImpl(), DataBaseType.TEST)
    private val banners = emptySet<Banner>().toMutableSet()

    @Given("^Un banner y una categoria \"([^\"]*)\" y \"([^\"]*)\"$")
    fun unBannerYUnaCategoria(bannerImage: String?, bannerCategory: String?) {
        this.banners.add(Banner(bannerImage!!, BannerCategory.valueOf(bannerCategory!!)))
    }

    @When("^Creo los banners$")
    fun creoLosBanners() {
        this.banners.forEach { this.bannerService.crearBanner(it) }
    }

    @Then("^El banner \"([^\"]*)\" con categoria \"([^\"]*)\" existe$")
    fun elBannerConCategoriaExiste(bannerImage: String?, bannerCategory: String?) {
        val banner = this.banners.find { (it.image == bannerImage!!) and (it.category == BannerCategory.valueOf(bannerCategory!!)) }!!
        assertEquals(banner, this.bannerService.recuperarBanner(banner.id.toString()))
    }

    @Given("^Un banner existente$")
    fun unBannerExistente() {
        this.banner = Banner("www.images.com/bannerExistente.png", BannerCategory.SCHEDULE)
        this.bannerService.crearBanner(this.banner)
    }

    @When("^Lo elimino$")
    fun loElimino() {
        this.bannerService.borrarBanner(this.banner.id.toString())
    }

    @Then("^El banner ya no existe$")
    fun elBannerYaNoExiste() {
        assertFalse(this.bannerService.recuperarTodosLosBanners().contains(this.banner))
    }

    @After
    fun deleteAll(){
        this.bannerService.deleteAll()
    }
}