package ar.edu.unq.cucumber.cargadeunproducto

import ar.edu.unq.AuxProdStepDefs
import cucumber.api.java.After
import cucumber.api.java.Before
import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
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
        println("Longitud: " + long)
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
        this.productoA.pesoKg = peso
    }

    @When("^creo un producto con estos datos$")
    fun creoUnProductoConEstosDatos() {
        this.proveedorService.crearProveedor(this.proveedorA)
        this.productoService.nuevoProducto(this.productoA)
        val productoRecuperado = productoService.obtenerProducto(this.proveedorA.id.toString(), this.productoA.itemName)
        println("longitudRecup: " + productoRecuperado.longitud)
        println(productoRecuperado.promotionalPrice)
        println(productoRecuperado.pesoKg)
    }

    @Then("^el producto cargado \"([^\"]*)\" se encuentra en la base de datos$")
    fun elProductoSeEncuentraEnLaBaseDeDatos(nombreProducto: String?) {
        val productoRecuperado = productoService.obtenerProducto(this.proveedorA.id.toString(), this.productoA.itemName)
        println("longitudRec: " + productoRecuperado.longitud)
        assertEquals(nombreProducto, productoRecuperado.itemName)
    }

    @And("^Sus datos son \"([^\"]*)\", \"([^\"]*)\", (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+)$")
    fun susDatosSon(nombreProducto: String?, descripcion: String?, stock: Int, precioNormal: Int, precioPromocional: Int, longitud: Int, alto: Int, ancho: Int, peso: Int) {
        val productoRecuperado = productoService.obtenerProducto(this.proveedorA.id.toString(), this.productoA.itemName)

        println("longitudRecuperada: " + productoRecuperado.longitud)
        println("alturaRecuperada: " + productoRecuperado.alto)
        println("nombre: " + productoRecuperado.itemName)

        assertEquals(nombreProducto, productoRecuperado.itemName)
        assertEquals(descripcion, productoRecuperado.description)
        assertEquals(stock, productoRecuperado.stock)
        assertEquals(precioNormal, productoRecuperado.itemPrice)
        assertEquals(precioPromocional, productoRecuperado.promotionalPrice)
        assertEquals(longitud, productoRecuperado.longitud)
        assertEquals(alto, productoRecuperado.alto)
        assertEquals(ancho,productoRecuperado.ancho )
        assertEquals(peso, productoRecuperado.pesoKg)

    }

    @After
    fun doSomethingAfter() {
        proveedorService.deleteAll()
        productoService.deleteAll()
    }
}