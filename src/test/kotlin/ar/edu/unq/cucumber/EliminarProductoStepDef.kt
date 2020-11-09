package ar.edu.unq.cucumber

import ar.edu.unq.AuxProdStepDefs
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import cucumber.api.Scenario
import cucumber.api.java.After
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EliminarProductoStepDef: AuxProdStepDefs() {

    lateinit var proveedorRecuperado: Proveedor
    lateinit var productoRecuperado: Producto



    @Given("^un proveedor en la base datos$")
    fun unProveedorEnLaBaseDatos() {
        proveedorService.crearProveedor(proveedorA)
        proveedorRecuperado = proveedorService.recuperarProveedor(proveedorA.id.toString())
    }

    @When("^se le agrega un producto \"([^\"]*)\"$")
     fun seLeAgregaUnProducto(productoA: String) {
        val productoABis = Producto(proveedorA.id, productoA, "SARASA", 7, 1000000, 800000)
        proveedorRecuperado.addProduct(productoABis)
        proveedorService.actualizarProveedor(proveedorRecuperado)
    }

    @When("^se le agrega otro producto \"([^\"]*)\"$")
    fun seLeAgregaOtroProducto(productoB: String) {
        val productoBBis = Producto(proveedorA.id, productoB, "A electric guitar", 7, 1000, 800000)
        proveedorRecuperado.addProduct(productoBBis)
        proveedorService.actualizarProveedor(proveedorRecuperado)
    }

    @Then("^recupero el proveedor y sus productos son \"([^\"]*)\", \"([^\"]*)\"$")
    fun recuperoElProveedorYSusProductosSon (nombreProductoA: String?, nombreProductoB: String?) {
        val proveedorRecuperado = this.proveedorService.recuperarProveedor(proveedorA.id.toString())
        val productosRecuperados = proveedorRecuperado.productos.map{it.itemName}
        assertTrue(productosRecuperados.contains(nombreProductoA))
        assertTrue(productosRecuperados.contains(nombreProductoB))
    }

    @Given("^dado un producto$")
    fun dadoUnProducto() {
        productoRecuperado = productoService.obtenerProducto(proveedorRecuperado.id.toString(),  productoA.itemName)
    }

    @When("^lo elimino$")
    fun loElimino() {
        productoService.borrarProducto(productoA.id.toString())
    }

    @Then("^no figura mas en la base de datos$")
    fun noFiguraMasEnLaBaseDeDatos() {
        val productosRecuperados = this.productoService.recuperarATodosLosProductos()
        assertEquals(setOf(productoB.itemName), productosRecuperados.map{it.itemName}.toSet())
    }

    @After
    fun doSomethingAfter() {
        proveedorService.deleteAll()
        productoService.deleteAll()
    }
}