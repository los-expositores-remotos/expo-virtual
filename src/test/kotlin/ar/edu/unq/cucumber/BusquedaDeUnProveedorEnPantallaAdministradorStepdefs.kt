package ar.edu.unq.cucumber

import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.ProveedorService
import ar.edu.unq.services.impl.ProveedorServiceImpl
import ar.edu.unq.services.runner.DataBaseType
import cucumber.api.java.After
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.junit.Assert

class BusquedaDeUnProveedorEnPantallaAdministradorStepdefs {
    private val proveedorService: ProveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.TEST)
    private lateinit var textoBuscado: String
    private lateinit var resultado: MutableList<Proveedor>

    @Given("^Dos proveedores de nombre \"([^\"]*)\" y \"([^\"]*)\"$")
    fun dosProveedoresDeNombre(proveedorANombre: String?, proveedorBNombre: String?) {
        val proveedorA = Proveedor(proveedorANombre!!, "", "", "", "")
        val proveedorB = Proveedor(proveedorBNombre!!, "", "", "", "")
        proveedorService.crearProveedor(proveedorA)
        proveedorService.crearProveedor(proveedorB)
    }

    @Given("^Un texto \"([^\"]*)\"$")
    fun unTexto(textoBuscado: String?) {
        this.textoBuscado = textoBuscado!!
    }

    @When("^Busco proveedores con ese dato$")
    fun buscoProveedoresConEseDato() {
        this.resultado = proveedorService.buscarProveedores(this.textoBuscado)
    }

    @Then("^Devuelvo de los proveedores \"([^\"]*)\" y \"([^\"]*)\", los que coinciden con la busqueda$")
    fun devuelvoDeLosProveedoresYLosQueCoincidenConLaBusqueda(proveedorANombre: String?, proveedorBNombre: String?) {
        Assert.assertEquals(this.resultado.map{ it.companyName }.toSet(), setOf(proveedorANombre, proveedorBNombre))
    }

    @After
    fun clear() {
        proveedorService.deleteAll()
    }
}