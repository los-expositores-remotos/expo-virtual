package ar.edu.unq.cucumber

import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.ProveedorService
import ar.edu.unq.services.impl.ProveedorServiceImpl
import ar.edu.unq.services.runner.DataBaseType
import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import kotlin.test.assertEquals

class ModuloDeCargaDelAdministradorStepDef {

    private val proveedorService: ProveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.TEST)

    @Given("^Una base de datos de proveedores vacia$")
    fun unaBaseDeDatosDeProveedoresVacia() {
        this.proveedorService.deleteAll()
    }

    @When("^Cargo un proveedor \"([^\"]*)\"$")
    fun cargoUnProveedor(nombreProveedor: String?) {
        this.proveedorService.crearProveedor(Proveedor(nombreProveedor!!))
    }

    @Then("^Los proveedores \"([^\"]*)\", \"([^\"]*)\" y \"([^\"]*)\" estan en la base de datos$")
    fun losProveedoresYEstanEnLaBaseDeDatos(proveedor1: String?, proveedor2: String?, proveedor3: String?) {
        val proveedores = this.proveedorService.recuperarATodosLosProveedores()
        val nombresProveedor = setOf(proveedor1!!, proveedor2!!, proveedor3!!)
        assertEquals(nombresProveedor, proveedores.map{ it.companyName }.toSet())
    }

    @And("^La cantidad de proveedores en la base de datos es (\\d+)$")
    fun laCantidadDeProveedoresEnLaBaseDeDatosEs(cantidadDeProveedores: Int) {
        val proveedores = this.proveedorService.recuperarATodosLosProveedores()
        assertEquals(cantidadDeProveedores, proveedores.count())
    }
}