package ar.edu.unq.cucumber.ordenaralfabeticamentelosproductos

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

class OrdenarAlfabeticamenteLosProductosStepdefs {
    private val productoService: ProductoService = ProductoServiceImpl(MongoProveedorDAOImpl(), MongoProductoDAOImpl(), DataBaseType.TEST)
    private val proveedorService: ProveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.TEST)
    private lateinit var productosOrdenados: MutableList<Producto>
    private val proveedorA = Proveedor("AA", "foto","http://res.cloudinary.com/instaclongbarreiro/image/upload/v1606355739/lqbiomw4bllgi6yjb0s5.jpg", "https://www.facebook.com/GibsonES/", "https://www.instagram.com/gibsonguitar/?hl=es-la", "https://www.gibson.com/")


    @Given("^Una base de datos con cinco productos: \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    fun unaBaseDeDatosConCincoProductos(productoEName: String?, productoCName: String?, productoBName: String?, productoAName: String?, productoDName: String?) {
            val productoA = Producto(proveedorA.id, productoAName!!, "A electric guitar", 7, 100, 800000, 10, 10, 10, 10)
            val productoB = Producto(proveedorA.id, productoBName!!, "A electric guitar", 7, 100, 800000, 10, 10, 10, 10)
            val productoC = Producto(proveedorA.id, productoCName!!, "A electric guitar", 7, 1000, 800000, 10, 10, 10, 10)
            val productoD = Producto(proveedorA.id, productoDName!!, "A electric guitar", 7, 1000000, 800000, 10, 10, 10, 10)
            val productoE = Producto(proveedorA.id, productoEName!!, "A electric guitar", 7, 1000000, 800000, 10, 10, 10, 10)

            proveedorA.addProduct(productoA)
            proveedorA.addProduct(productoB)
            proveedorA.addProduct(productoC)
            proveedorA.addProduct(productoD)
            proveedorA.addProduct(productoE)
            proveedorService.crearProveedor(proveedorA)
    }

    @When("^ordeno los productos alfabeticamente de forma ascendente$")
    fun ordenoLosProductosAlfabeticamenteDeFormaAscendente() {
        this.productosOrdenados = productoService.recuperarATodosLosProductos().sortedBy { it.itemName }.toMutableList()
    }

    @Then("^obtengo los productos ordendos \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    fun obtengoLosProductosOrdendos(productoAName: String?, productoBName: String?, productoCName: String?, productoDName: String?, productoEName: String?) {
        Assert.assertEquals(proveedorService.recuperarProveedor(proveedorA.id.toString()).productos, this.productosOrdenados)
    }

    @io.cucumber.java.After
    fun clear() {
        productoService.deleteAll()
        proveedorService.deleteAll()
    }
}