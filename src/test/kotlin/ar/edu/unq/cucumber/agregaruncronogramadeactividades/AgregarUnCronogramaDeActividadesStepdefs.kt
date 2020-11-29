package ar.edu.unq.cucumber.agregaruncronogramadeactividades

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


class AgregarUnCronogramaDeActividadesStepdefs {
    private val bannerService: BannerService = BannerServiceImpl(MongoBannerDAOImpl(), DataBaseType.TEST)
    private var linkImage: String? = null
    private lateinit var categoria: String
    private lateinit var schedule: Banner
    private lateinit var banner2: Banner
    private var exception: BadRequestResponse? = null


    @Given("^un link a una imagen \"([^\"]*)\"$")
    fun unLinkAUnaImagen(linkImagen: String?) {
        this.linkImage = if(linkImagen == ""){
            null
        }else{
            linkImagen
        }
    }

    @Given("^una categoria \"([^\"]*)\"$")
    fun unaCategoria(categoria: String?) {
        this.categoria = categoria!!
    }

    @When("^creo al schedule con esos datos$")
    fun creoAlScheduleConEsosDatos() {
        this.schedule = Banner(this.linkImage!!, BannerCategory.valueOf(this.categoria))
        this.bannerService.crearBanner(schedule)
    }

    @Then("^el schedule esta en la base de datos$")
    fun elScheduleEstaEnLaBaseDeDatos() {
        val scheduleRecuperado = this.bannerService.recuperarBanner(this.schedule.id.toString())
        assertEquals(this.schedule, scheduleRecuperado)
    }

    @And("^sus datos son \"([^\"]*)\" y \"([^\"]*)\"$")
    fun susDatosSonY(linkImagen: String?, categoria: String?) {
        val bannerRecuperado = this.bannerService.recuperarBanner(this.schedule.id.toString())
        assertEquals(linkImagen, bannerRecuperado.image)
        assertEquals(categoria, bannerRecuperado.category.toString())
    }

    @When("^trato de crear el schedule$")
    fun tratoDeCrearElSchedule() {
        //        this.banner2 = Banner(this.linkImage!!, BannerCategory.valueOf(this.categoria))
//        try {
//            this.bannerService.crearBanner(this.banner2)
//        }catch(e: BadRequestResponse){
//            this.exception = e
//        }//TODO:testear con api

    }

    @Then("^lanza una excepcion$")
    fun lanzaUnaExcepcion() {
        //assertNotNull(this.exception)//TODO:testear con api
    }

    @And("^no existe el schedule$")
    fun noExisteElSchedule() {
        val bannersRecuperados = this.bannerService.recuperarTodosLosBanners().toSet()
        assertEquals(emptySet(), bannersRecuperados)
    }

    @After
    fun deleteAll(){
        this.bannerService.deleteAll()
    }

}