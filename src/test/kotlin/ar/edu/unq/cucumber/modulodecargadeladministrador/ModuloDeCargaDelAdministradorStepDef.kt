package ar.edu.unq.cucumber.modulodecargadeladministrador

import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.ProveedorService
import ar.edu.unq.services.impl.ProveedorServiceImpl
import ar.edu.unq.services.runner.DataBaseType
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import kotlin.test.assertEquals

class ModuloDeCargaDelAdministradorStepDef {

    private val proveedorService: ProveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.TEST)

    @Given("^Una base de datos de proveedores vacia$")
    fun unaBaseDeDatosDeProveedoresVacia() {
        this.proveedorService.deleteAll()
    }

    @When("^Cargo a los proveedores")
    fun cargoUnProveedor(proveedores: List<String>) {
        proveedores.forEach {
            this.proveedorService.crearProveedor(Proveedor(it, "www.images.com/${it}.png", "www.facebook.com/$it", "ww.instagram.com/$it", "www.${it}.com"))
        }
    }

    @Then("^Los siguientes proveedores estan en la base de datos$")
    fun losProveedoresYEstanEnLaBaseDeDatos(nombresProveedor: List<String>) {
        val proveedoresRecuperados = this.proveedorService.recuperarATodosLosProveedores()
        assertEquals(nombresProveedor.toSet(), proveedoresRecuperados.map{ it.companyName }.toSet())
    }

    @And("^La cantidad de proveedores en la base de datos es (\\d+)$")
    fun laCantidadDeProveedoresEnLaBaseDeDatosEs(cantidadDeProveedores: Int) {
        val proveedores = this.proveedorService.recuperarATodosLosProveedores()
        assertEquals(cantidadDeProveedores, proveedores.count())
    }

    @io.cucumber.java.After
    fun clear() {
        proveedorService.deleteAll()
    }
}