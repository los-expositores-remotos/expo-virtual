package ar.edu.unq.cucumber.eliminaruncronogramadeactividades

import ar.edu.unq.dao.mongodb.MongoBannerDAOImpl
import ar.edu.unq.modelo.banner.Banner
import ar.edu.unq.modelo.banner.BannerCategory
import ar.edu.unq.services.BannerService
import ar.edu.unq.services.impl.BannerServiceImpl
import ar.edu.unq.services.runner.DataBaseType
import io.cucumber.java.After
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import kotlin.test.assertEquals

class EliminarUnCronogramaDeActividadesStepdefs {
    private val bannerService: BannerService = BannerServiceImpl(MongoBannerDAOImpl(), DataBaseType.TEST)
    private val banners: MutableSet<Banner> = emptySet<Banner>().toMutableSet()

    @Given("^Un cronograma de actividades existente$")
    fun unCronogramaDeActividadesExistente() {
        this.banners.add(Banner("www.images.com/image.png", BannerCategory.SCHEDULE))
        this.bannerService.crearBanner(this.banners.first())
        assertEquals(this.banners, this.bannerService.recuperarTodosLosBanners().toSet())
    }

    @When("^Elimino el cronograma$")
    fun eliminoElCronograma() {
        this.bannerService.borrarBanner(this.banners.first().id.toString())
    }

    @Then("^El cronograma ya no existe$")
    fun elCronogramaYaNoExiste() {
        assertEquals(emptySet(), this.bannerService.recuperarTodosLosBanners().toSet())
    }

    @After
    fun deleteAll(){
        this.bannerService.deleteAll()
    }
}