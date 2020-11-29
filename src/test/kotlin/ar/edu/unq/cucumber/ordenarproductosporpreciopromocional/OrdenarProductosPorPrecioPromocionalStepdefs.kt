package ar.edu.unq.cucumber.ordenarproductosporpreciopromocional

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

class OrdenarProductosPorPrecioPromocionalStepdefs {

    private val productoService: ProductoService = ProductoServiceImpl(MongoProveedorDAOImpl(), MongoProductoDAOImpl(), DataBaseType.TEST)
    private val proveedorService: ProveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.TEST)
    private lateinit var productosOrdenados: MutableList<Producto>

    val proveedorA = Proveedor("AA", "foto","http://res.cloudinary.com/instaclongbarreiro/image/upload/v1606355739/lqbiomw4bllgi6yjb0s5.jpg", "https://www.facebook.com/GibsonES/", "https://www.instagram.com/gibsonguitar/?hl=es-la", "https://www.gibson.com/")

    @Given("^una base de datos con seis productos cuyos precios promocionales son: (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+)$")
    fun unaBaseDeDatosConSeisProductosCuyosPreciosPromocionalesSon(precioPromocionalA: Int, precioPromocionalB: Int, precioPromocionalC: Int, precioPromocionalD: Int, precioPromocionalE: Int, precioPromocionalF: Int) {
        val productoA = Producto(proveedorA.id, "prod1", "A electric guitar", 7, 100, precioPromocionalA, 10, 10, 10, 10)
        val productoB = Producto(proveedorA.id, "prod2", "A electric guitar", 7, 200, precioPromocionalB, 10, 10, 10, 10)
        val productoC = Producto(proveedorA.id, "prod3", "A electric guitar", 7, 300, precioPromocionalC, 10, 10, 10, 10)
        val productoD = Producto(proveedorA.id, "prod4", "A electric guitar", 7, 400, precioPromocionalD, 10, 10, 10, 10)
        val productoE = Producto(proveedorA.id, "prod5", "A electric guitar", 7, 500, precioPromocionalE, 10, 10, 10, 10)
        val productoF = Producto(proveedorA.id, "prod6", "A electric guitar", 7, 600, precioPromocionalF, 10, 10, 10, 10)
        proveedorA.addProduct(productoA)
        proveedorA.addProduct(productoB)
        proveedorA.addProduct(productoC)
        proveedorA.addProduct(productoD)
        proveedorA.addProduct(productoE)
        proveedorA.addProduct(productoF)
        proveedorService.crearProveedor(proveedorA)
    }

    @When("^ordeno los productos por precios promocional$")
    fun ordenoLosProductosPorPreciosPromocional() {
        this.productosOrdenados = productoService.recuperarATodosLosProductos().sortedBy { it.promotionalPrice }.toMutableList()
    }

    @Then("^obtengo los productos ordenados por precio promocional$")
    fun obtengoLosProductosOrdenadosPorPrecioPromocional() {
        val productoRecuperados = proveedorService.recuperarProveedor(proveedorA.id.toString()).productos.sortedBy { it.promotionalPrice }

        Assert.assertEquals(productoRecuperados, this.productosOrdenados)
    }

    @io.cucumber.java.After
    fun clear() {
        productoService.deleteAll()
        proveedorService.deleteAll()
    }

}