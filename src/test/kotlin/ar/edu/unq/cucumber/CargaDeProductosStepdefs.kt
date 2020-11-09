package ar.edu.unq.cucumber

import ar.edu.unq.AuxProdStepDefs
import cucumber.api.Scenario
import cucumber.api.java.After
import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import kotlin.test.assertEquals


class CargaDeProductosStepdefs: AuxProdStepDefs() {

    @Given("^un nombre de un producto \"([^\"]*)\"$")
    fun unNombreDeUnProducto(nombreProducto: String?) {
        this.productoA.itemName = nombreProducto!!
    }

    @Given("^una descripción \"([^\"]*)\"$")
    fun unaDescripcion(descripcion: String?) {
        this.productoA.description = descripcion!!
    }

    @Given("^un stock (\\d+)$")
    fun unStock(stock: Int) {
        this.productoA.stock = stock
    }

    @Given("^un precio normal (\\d+)$")
    fun unPrecioNormal(precioNormal: Int) {
        this.productoA.itemPrice = precioNormal
    }

    @Given("^un precio promocional (\\d+)$")
    fun unPrecioPromocional(precioPromocional: Int) {
        this.productoA.promotionalPrice = precioPromocional!!
    }

    @When("^creo un producto con estos datos$")
    fun creoUnProductoConEstosDatos() {
        this.proveedorService.crearProveedor(proveedorA)
        this.productoService.nuevoProducto(this.productoA)

    }

    @Then("^el producto cargado \"([^\"]*)\" se encuentra en la base de datos$")
    fun elProductoSeEncuentraEnLaBaseDeDatos(nombreProducto: String?) {
        val productoRecuperado = productoService.obtenerProducto(proveedorA.id.toString()!!, productoA.itemName!!)
        assertEquals(nombreProducto, productoRecuperado.itemName)
    }

    @And("^Sus datos son \"([^\"]*)\", \"([^\"]*)\", (\\d+), (\\d+), (\\d+)$")
    fun susDatosSon(nombreProducto: String?, descripcion: String?, stock: Int, precioNormal: Int, precioPromocional: Int) {
        val productoRecuperado = this.productoService.recuperarProducto(this.productoA.id.toString())
        assertEquals(nombreProducto, productoRecuperado.itemName)
        assertEquals(descripcion, productoRecuperado.description)
        assertEquals(stock, productoRecuperado.stock)
        assertEquals(precioNormal, productoRecuperado.itemPrice)
        assertEquals(precioPromocional, productoRecuperado.promotionalPrice)
    }

    @After
    fun doSomethingAfter(scenario: Scenario?) {
        proveedorService.deleteAll()
        productoService.deleteAll()
    }
}