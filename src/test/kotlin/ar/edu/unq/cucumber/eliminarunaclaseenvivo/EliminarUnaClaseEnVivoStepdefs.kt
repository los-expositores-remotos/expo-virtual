package ar.edu.unq.cucumber.eliminarunaclaseenvivo

import ar.edu.unq.dao.mongodb.MongoBannerDAOImpl
import ar.edu.unq.modelo.banner.Banner
import ar.edu.unq.modelo.banner.BannerCategory
import ar.edu.unq.services.impl.BannerServiceImpl
import ar.edu.unq.services.runner.DataBaseType
import cucumber.api.java.After
import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class EliminarUnaClaseEnVivoStepdefs {

    private lateinit var banner: Banner
    private val bannerService = BannerServiceImpl(MongoBannerDAOImpl(), DataBaseType.TEST)
    private val banners = emptySet<Banner>().toMutableSet()

    @Given("^Una clase y una categoria \"([^\"]*)\" y \"([^\"]*)\"$")
    fun unaClaseYUnaCategoriaY(bannerImage: String?, bannerCategory: String?) {
        this.banner = Banner(bannerImage!!, BannerCategory.valueOf(bannerCategory!!))
        this.banners.add(this.banner)
    }

    @When("^Creo la clase$")
    fun creoLaClase() {
        this.banners.forEach { this.bannerService.crearBanner(it) }
    }

    @Then("^La clase \"([^\"]*)\" con categoria \"([^\"]*)\" existe$")
    @Throws(Throwable::class)
    fun laClaseConCategoriaExiste(bannerImage: String?, bannerCategory: String?) {
        val banner = this.banners.find { (it.image == bannerImage!!) and (it.category == BannerCategory.valueOf(bannerCategory!!)) }!!
        assertEquals(banner, this.bannerService.recuperarBanner(banner.id.toString()))
    }

    @And("^Lo deleteo$")
    fun loDeleteo() {
        this.bannerService.borrarBanner(this.banner.id.toString())
    }

    @And("^La clase ya no existe$")
    fun laClaseYaNoExiste() {
        assertFalse(this.bannerService.recuperarTodosLosBanners().contains(this.banner))
    }

    @After
    fun deleteAll(){
        this.bannerService.deleteAll()
    }
}