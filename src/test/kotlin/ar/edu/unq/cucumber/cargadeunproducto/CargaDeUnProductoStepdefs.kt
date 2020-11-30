package ar.edu.unq.cucumber.cargadeunproducto

import ar.edu.unq.AuxProdStepDefs
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import kotlin.test.assertEquals


class CargaDeUnProductoStepdefs: AuxProdStepDefs() {

    @Before
    fun doSomethingBefore() {
        proveedorService.deleteAll()
        productoService.deleteAll()
    }

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
        this.productoA.promotionalPrice = precioPromocional
    }

    @Given("^una longitud (\\d+)$")
    fun unaLongitud(long: Int){
        this.productoA.longitud = long
    }

    @Given("^un ancho (\\d+)$")
    fun unAncho(ancho: Int){
        this.productoA.ancho = ancho
    }

    @Given("^un alto (\\d+)$")
    fun unAlto(alto: Int){
        this.productoA.alto = alto
    }

    @Given("^un peso en kilos (\\d+)$")
    fun unPeso(peso: Int){
        this.productoA.pesoGr = peso
    }

    @When("^creo un producto con estos datos$")
    fun creoUnProductoConEstosDatos() {
        this.proveedorService.crearProveedor(this.proveedorA)
        this.productoService.nuevoProducto(this.productoA)
    }

    @Then("^el producto cargado \"([^\"]*)\" se encuentra en la base de datos$")
    fun elProductoSeEncuentraEnLaBaseDeDatos(nombreProducto: String?) {
        val productoRecuperado = productoService.obtenerProducto(this.proveedorA.id.toString(), this.productoA.itemName)
        assertEquals(nombreProducto, productoRecuperado.itemName)
    }

    @And("^Sus datos son \"([^\"]*)\", \"([^\"]*)\", (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+)$")
    fun susDatosSon(nombreProducto: String?, descripcion: String?, stock: Int, precioNormal: Int, precioPromocional: Int, longitud: Int, ancho: Int, alto: Int, peso: Int) {
        val productoRecuperado = productoService.obtenerProducto(this.proveedorA.id.toString(), this.productoA.itemName)

        assertEquals(nombreProducto, productoRecuperado.itemName)
        assertEquals(descripcion, productoRecuperado.description)
        assertEquals(stock, productoRecuperado.stock)
        assertEquals(precioNormal, productoRecuperado.itemPrice)
        assertEquals(precioPromocional, productoRecuperado.promotionalPrice)
        assertEquals(longitud, productoRecuperado.longitud)
        assertEquals(alto, productoRecuperado.alto)
        assertEquals(ancho,productoRecuperado.ancho )
        assertEquals(peso, productoRecuperado.pesoGr)

    }

    @After
    fun doSomethingAfter() {
        proveedorService.deleteAll()
        productoService.deleteAll()
    }
}