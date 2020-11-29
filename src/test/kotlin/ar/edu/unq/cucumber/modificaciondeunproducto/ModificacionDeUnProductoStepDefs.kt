package ar.edu.unq.cucumber.modificaciondeunproducto

import ar.edu.unq.dao.mongodb.MongoProductoDAOImpl
import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.ProductoService
import ar.edu.unq.services.ProveedorService
import ar.edu.unq.services.impl.ProductoServiceImpl
import ar.edu.unq.services.impl.ProveedorServiceImpl
import ar.edu.unq.services.runner.DataBaseType
import io.cucumber.java.After
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import kotlin.test.assertEquals


class ModificacionDeUnProductoStepDefs{

    val proveedorA = Proveedor("AA", "foto", "banner", "https://www.facebook.com/GibsonES/", "https://www.instagram.com/gibsonguitar/?hl=es-la", "https://www.gibson.com/")
    val proveedorService: ProveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.TEST)
    val productoService: ProductoService = ProductoServiceImpl(MongoProveedorDAOImpl(), MongoProductoDAOImpl(), DataBaseType.TEST)
    val productoA = Producto(proveedorA.id, "Les Paul", "SARASA", 7, 1000000, 800000, 10, 10, 10, 10)

    @Given("^Un nombre de un producto \"([^\"]*)\"$")
    fun unNombreDeUnProducto(nombreProducto: String?) {
        this.productoA.itemName = nombreProducto!!
    }

    @Given("^Una descripción \"([^\"]*)\"$")
    fun unaDescripcion(descripcion: String?) {
        this.productoA.description = descripcion!!
    }

    @Given("^Un stock (\\d+)$")
    fun unStock(stock: Int) {
        this.productoA.stock = stock
    }

    @Given("^Un precio normal (\\d+)$")
    fun unPrecioNormal(precioNormal: Int) {
        this.productoA.itemPrice = precioNormal
    }

    @Given("^Un precio promocional (\\d+)$")
    fun unPrecioPromocional(precioPromocional: Int) {
        this.productoA.promotionalPrice = precioPromocional
    }

    @When("^Cuando modifico su nombre por \"([^\"]*)\"$")
       fun cuandoModificoSuNombrePor(newName: String?) {
        productoA.itemName = newName!!
        this.productoService.actualizarProducto(productoA)
    }

    @When("^Creo un producto con estos datos$")
    fun creoUnProductoConEstosDatos() {
        this.proveedorService.crearProveedor(proveedorA)
        this.productoService.nuevoProducto(this.productoA)

    }

    @When("^El producto cargado \"([^\"]*)\" se encuentra en la base de datos$")
    fun elProductoSeEncuentraEnLaBaseDeDatos(nombreProducto: String?) {
        val productoRecuperado = productoService.obtenerProducto(proveedorA.id.toString(), productoA.itemName)
        assertEquals(nombreProducto, productoRecuperado.itemName)
    }

    @Then("^sus datos son \"([^\"]*)\", \"([^\"]*)\", (\\d+), (\\d+), (\\d+)$")
    fun susDatosSon(nombreProducto: String?, descripcion: String?, stock: Int, precioNormal: Int, precioPromocional: Int) {
        val productoRecuperado = this.productoService.recuperarProducto(this.productoA.id.toString())
        assertEquals(nombreProducto, productoRecuperado.itemName)
        assertEquals(descripcion, productoRecuperado.description)
        assertEquals(stock, productoRecuperado.stock)
        assertEquals(precioNormal, productoRecuperado.itemPrice)
        assertEquals(precioPromocional, productoRecuperado.promotionalPrice)
    }

    @After
    fun doSomethingAfter() {
        proveedorService.deleteAll()
        productoService.deleteAll()
    }


}