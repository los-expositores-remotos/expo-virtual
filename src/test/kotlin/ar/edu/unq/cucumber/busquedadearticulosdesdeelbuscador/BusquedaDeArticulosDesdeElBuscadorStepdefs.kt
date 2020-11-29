package ar.edu.unq.cucumber.busquedadearticulosdesdeelbuscador

import ar.edu.unq.dao.mongodb.MongoProductoDAOImpl
import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.ProductoService
import ar.edu.unq.services.ProveedorService
import ar.edu.unq.services.impl.ProductoServiceImpl
import ar.edu.unq.services.impl.ProveedorServiceImpl
import ar.edu.unq.services.runner.DataBaseType
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.Assert

class BusquedaDeArticulosDesdeElBuscadorStepdefs {
    private val productoService: ProductoService = ProductoServiceImpl(MongoProveedorDAOImpl(), MongoProductoDAOImpl(), DataBaseType.TEST)
    private val proveedorService: ProveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.TEST)
    private lateinit var textoBusqueda: String
    private lateinit var productosEncontrados: MutableList<Producto>

    @Given("^Una base de datos con cuatro productos: \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    fun unaBaseDeDatosConProductos(productoAName: String?, productoBName: String?, productoCName: String?, productoDName: String?) {
        val proveedorA = Proveedor("AA", "foto", "banner", "https://www.facebook.com/GibsonES/", "https://www.instagram.com/gibsonguitar/?hl=es-la", "https://www.gibson.com/")
        val productoA = Producto(proveedorA.id, productoAName!!, "A electric guitar", 7, 100, 800000, 10, 10, 10, 10)
        val productoB = Producto(proveedorA.id, productoBName!!, "A electric guitar", 7, 100, 800000, 10, 10, 10, 10)
        val productoC = Producto(proveedorA.id, productoCName!!, "A electric guitar", 7, 1000, 800000, 10, 10, 10, 10)
        val productoD = Producto(proveedorA.id, productoDName!!, "A electric guitar", 7, 1000000, 800000, 10, 10, 10, 10)
        proveedorA.addProduct(productoA)
        proveedorA.addProduct(productoB)
        proveedorA.addProduct(productoC)
        proveedorA.addProduct(productoD)
        proveedorService.crearProveedor(proveedorA)
    }

    @Given("^Una palabra \"([^\"]*)\"$")
    fun unaPalabra(palabraBuscada: String?) {
        this.textoBusqueda = palabraBuscada!!
    }

    @When("^Busco productos con ese dato$")
    fun buscoProductosConEseDato() {
        this.productosEncontrados = productoService.buscarProductos(this.textoBusqueda)
    }

    @Then("^Devuelvo de los productos \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" y \"([^\"]*)\", los que coinciden con la busqueda$")
    fun devuelvoLosProductosYQueCoincidanConLaBusqueda(nombreProductoA: String?, nombreProductoB: String?, nombreProductoC: String?, nombreProductoD: String?) {
        Assert.assertEquals(setOf(nombreProductoA, nombreProductoB, nombreProductoC, nombreProductoD),this.productosEncontrados.map { it.itemName }.toSet())
    }

    @io.cucumber.java.After
    fun clear() {
        productoService.deleteAll()
        proveedorService.deleteAll()
    }
}