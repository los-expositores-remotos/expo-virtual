package ar.edu.unq.cucumber.cargadebanner

import ar.edu.unq.dao.mongodb.MongoBannerDAOImpl
import ar.edu.unq.modelo.banner.Banner
import ar.edu.unq.modelo.banner.BannerCategory
import ar.edu.unq.services.BannerService
import ar.edu.unq.services.impl.BannerServiceImpl
import ar.edu.unq.services.runner.DataBaseType
import io.cucumber.java.After
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.javalin.http.BadRequestResponse
import kotlin.test.assertEquals

class CargaDeBannerStepdefs {
    private val bannerService: BannerService = BannerServiceImpl(MongoBannerDAOImpl(), DataBaseType.TEST)
    private var linkImage: String? = null
    private lateinit var categoria: String
    private lateinit var banner1: Banner
    private lateinit var banner2: Banner
    private var exception: BadRequestResponse? = null

    @Given("^Un link a una imagen \"([^\"]*)\"$")
    fun unLinkAUnaImagen(linkImage: String?) {
        this.linkImage = if(linkImage == ""){
                            null
                        }else{
                            linkImage
                        }
    }

    @Given("^Una categoria \"([^\"]*)\"$")
    fun unaCategoria(categoria: String?) {
        this.categoria = categoria!!
    }

    @When("^Creo al banner con esos datos$")
    fun creoAlBannerConEsosDatos() {
        this.banner1 = Banner(this.linkImage!!, BannerCategory.valueOf(this.categoria))
        this.bannerService.crearBanner(banner1)
    }

    @Then("^El banner esta en la base de datos$")
    fun elBannerEstaEnLaBaseDeDatos() {
        val bannerRecuperado = this.bannerService.recuperarBanner(this.banner1.id.toString())
        assertEquals(this.banner1, bannerRecuperado)
    }

    @And("^Sus datos son \"([^\"]*)\" y \"([^\"]*)\"$")
    fun susDatosSonY(linkImage: String?, categoria: String?) {
        val bannerRecuperado = this.bannerService.recuperarBanner(this.banner1.id.toString())
        assertEquals(linkImage, bannerRecuperado.image)
        assertEquals(categoria, bannerRecuperado.category.toString())
    }

    @When("^Trato de crear el banner$")
    fun tratoDeCrearElBanner() {
//        this.banner2 = Banner(this.linkImage!!, BannerCategory.valueOf(this.categoria))
//        try {
//            this.bannerService.crearBanner(this.banner2)
//        }catch(e: BadRequestResponse){
//            this.exception = e
//        }//TODO:testear con api
    }

    @Then("^Lanza una excepcion$")
    fun lanzaUnaExcepcion() {
        //assertNotNull(this.exception)//TODO:testear con api
    }

    @And("^No existe el banner$")
    fun noExisteElBanner() {
        val bannersRecuperados = this.bannerService.recuperarTodosLosBanners().toSet()
        assertEquals(emptySet(), bannersRecuperados)
    }

    @After
    fun deleteAll(){
        this.bannerService.deleteAll()
    }
}