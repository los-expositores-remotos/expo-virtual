package ar.edu.unq.cucumber.altadeunaclaseenvivo

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

class AltaDeUnaClaseEnVivoStepdefs {
    private val bannerService: BannerService = BannerServiceImpl(MongoBannerDAOImpl(), DataBaseType.TEST)
    private var linkImage: String? = null
    private lateinit var categoria: String
    private lateinit var clase: Banner
    private lateinit var banner2: Banner
    private var exception: BadRequestResponse? = null

    @Given("^el link a una imagen \"([^\"]*)\"$")
    fun elLinkAUnaImagen(linkImagen: String?) {
        this.linkImage = if(linkImagen == ""){
            null
        }else{
            linkImagen
        }
    }

    @Given("^la categoria \"([^\"]*)\"$")
    fun laCategoria(categoria: String?) {
        this.categoria = categoria!!
    }

    @When("^creo la clase con esos datos$")
    fun creoLaClaseConEsosDatos() {
        this.clase = Banner(this.linkImage!!, BannerCategory.valueOf(this.categoria))
        this.bannerService.crearBanner(clase)
    }

    @Then("^la clase esta en la base de datos$")
    fun laClaseEstaEnLaBaseDeDatos() {
        val claseEnVivoRecuperada = this.bannerService.recuperarBanner(this.clase.id.toString())
        assertEquals(this.clase, claseEnVivoRecuperada)
    }

    @And("^los datos de la clase son \"([^\"]*)\" y \"([^\"]*)\"$")
    fun losDatosDeLaClaseSonY(linkImagen: String?, categoria: String?) {
        val claseEnVivoRecuperada = this.bannerService.recuperarBanner(this.clase.id.toString())
        assertEquals(linkImagen, claseEnVivoRecuperada.image)
        assertEquals(categoria, claseEnVivoRecuperada.category.toString())
    }

    @When("^trato de crear la clase$")
    fun tratoDeCrearLaClase() {
        //        this.banner2 = Banner(this.linkImage!!, BannerCategory.valueOf(this.categoria))
//        try {
//            this.bannerService.crearBanner(this.banner2)
//        }catch(e: BadRequestResponse){
//            this.exception = e
//        }//TODO:testear con api
    }

    @Then("^arroja una excepcion$")
    fun arrojaUnaExcepcion() {
        //assertNotNull(this.exception)//TODO:testear con api
    }

    @And("^no existe la clase$")
    fun noExisteLaClase() {
        val bannersRecuperados = this.bannerService.recuperarTodosLosBanners().toSet()
        assertEquals(emptySet(), bannersRecuperados)
    }

    @After
    fun deleteAll(){
        this.bannerService.deleteAll()
    }
}