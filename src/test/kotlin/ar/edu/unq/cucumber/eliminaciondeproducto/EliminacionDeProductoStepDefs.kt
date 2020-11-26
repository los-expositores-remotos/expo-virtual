package ar.edu.unq.cucumber.eliminaciondeproducto

import ar.edu.unq.AuxProdStepDefs
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import io.cucumber.java.After
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EliminacionDeProductoStepDefs: AuxProdStepDefs() {

    lateinit var proveedorRecuperado: Proveedor
    lateinit var productoRecuperado: Producto



    @Given("^un proveedor en la base datos$")
    fun unProveedorEnLaBaseDatos() {
        proveedorService.crearProveedor(proveedorA)
        proveedorRecuperado = proveedorService.recuperarProveedor(proveedorA.id.toString())
    }

    @When("^se le agrega un producto \"([^\"]*)\"$")
     fun seLeAgregaUnProducto(productoA: String) {
        val productoABis = Producto(proveedorA.id, productoA, "SARASA", 7, 1000000, 800000, 10, 10, 10, 10)
        proveedorRecuperado.addProduct(productoABis)
        proveedorService.actualizarProveedor(proveedorRecuperado)
    }

    @When("^se le agrega otro producto \"([^\"]*)\"$")
    fun seLeAgregaOtroProducto(productoB: String) {
        val productoBBis = Producto(proveedorA.id, productoB, "A electric guitar", 7, 1000, 800000, 10, 10, 10, 10)
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

    @Given("^dado un producto \"([^\"]*)\"$")
    fun dadoUnProducto(productoA: String) {
        proveedorService.crearProveedor(proveedorA)
        val proveedorRecuperado = this.proveedorService.recuperarProveedor(proveedorA.id.toString())
        val productoABis = Producto(proveedorA.id, productoA, "SARASA", 7, 1000000, 800000, 10, 10, 10, 10)
        proveedorRecuperado.addProduct(productoABis)
        proveedorService.actualizarProveedor(proveedorRecuperado)
        productoRecuperado = productoService.obtenerProducto(proveedorRecuperado.id.toString(), productoA)
    }

    @When("^elimino el \"([^\"]*)\"$")
    fun loElimino(productoA: String) {
        val proveedorRecuperado = this.proveedorService.recuperarProveedor(proveedorA.id.toString())
        val productoRecuperado = proveedorRecuperado.productos.find{it.itemName == productoA}
        productoService.borrarProducto(productoRecuperado!!.id.toString())
    }

    @Then("^no figura mas en la base de datos$")
    fun noFiguraMasEnLaBaseDeDatos() {
        val proveedorRecuperado = this.proveedorService.recuperarProveedor(proveedorA.id.toString())
        val productoBorrado = proveedorRecuperado.productos.map{it.itemName}.contains(productoA.itemName)
        assertFalse(productoBorrado)
        //assertTrue(productosRecuperados.contains(nombreProductoB))
    }

    @After
    fun doSomethingAfter() {
        proveedorService.deleteAll()
        productoService.deleteAll()
    }
}