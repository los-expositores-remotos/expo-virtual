package ar.edu.unq.cucumber.visualizaciondeproveedores

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
import io.cucumber.java.Scenario
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.bson.types.ObjectId
import org.junit.Assert
import java.util.*

class VisualizacionDeProveedoresStepdefs {
    private val proveedorService: ProveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.TEST)
    private val productoService: ProductoService =
        ProductoServiceImpl(MongoProveedorDAOImpl(), MongoProductoDAOImpl(), DataBaseType.TEST)
    private var proveedorId: String? = null
    private var productoNombre: String? = null

    @Given("^Un proveedor sin productos$")
    fun unProveedorSinProductos() {
        val proveedor = Proveedor(
            "Gibson",
            "foto",
            "http://res.cloudinary.com/instaclongbarreiro/image/upload/v1606355739/lqbiomw4bllgi6yjb0s5.jpg",
            "https://www.facebook.com/GibsonES/",
            "https://www.instagram.com/gibsonguitar/?hl=es-la",
            "https://www.gibson.com/"
        )
        proveedorId = proveedor.id.toString()
        proveedorService.crearProveedor(proveedor)
    }

    @When("^Agrego un producto \"([^\"]*)\"$")
    fun agregoUnProducto(nombreProducto: String?) {
        val idDeProveedor = ObjectId(proveedorId)
        val productoNuevo = Producto(idDeProveedor, nombreProducto!!, "", 5, 5, 5, 10, 10, 10, 10)
        productoService.nuevoProducto(productoNuevo)
    }

    @Then("^Los productos del proveedor son \"([^\"]*)\" y \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun losProductosDelProveedorSonY(producto1: String?, producto2: String?) {
        val productos: MutableList<Producto> = ArrayList()
        productos.add(productoService.obtenerProducto(proveedorId!!, producto1!!))
        productos.add(productoService.obtenerProducto(proveedorId!!, producto2!!))
        //assertEquals(productos, this.proveedorService.recuperarProveedor(this.proveedorId).getProductos());    NO DEBERIAS PEDIR LOS PRODUCTOS DE X PROVEEDOR?
    }

    @Given("^Un producto vacio$")
    fun unProductoVacio() {
        val proveedor = Proveedor(
            "Chibson",
            "www.imagenes.com/lasegundacompany.png",
            "www.imagenes.com/lasegundacompany.png",
            "https://www.facebook.com/GibsonES/",
            "https://www.instagram.com/gibsonguitar/?hl=es-la",
            "https://www.gibson.com/"
        )
        proveedorService.crearProveedor(proveedor)
        proveedorId = proveedor.id.toString()
        val productoNuevo = Producto(proveedor.id, "ElProducto", "", 5, 5, 5, 10, 10, 10, 10)
        productoService.nuevoProducto(productoNuevo)
        productoNombre = productoNuevo.itemName
    }

    @When("^Le asigno la descripcion \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun leAsignoLaDescripcion(descripcion: String?) {
        val producto = productoService.obtenerProducto(proveedorId!!, productoNombre!!)
        producto.description = descripcion!!
        productoService.actualizarProducto(producto)
    }

    @When("^Le asigno el precio (\\d+)$")
    fun leAsignoElPrecio(precio: Int) {
        val producto = productoService.obtenerProducto(proveedorId!!, productoNombre!!)
        producto.itemPrice = precio
        productoService.actualizarProducto(producto)
    }

    @Then("^La descripción del producto es \"([^\"]*)\"$")
    fun laDescripcionDelProductoEs(descripcion: String?) {
        val producto = productoService.obtenerProducto(proveedorId!!, productoNombre!!)
        Assert.assertEquals(descripcion, producto.description)
    }

    @And("^El precio del producto es (\\d+)$")
    fun elPrecioDelProductoEs(precio: Int) {
        val producto = productoService.obtenerProducto(proveedorId!!, productoNombre!!)
        Assert.assertEquals(precio.toLong(), producto.itemPrice.toLong())
    }

    @After
    fun doSomethingAfter(scenario: Scenario?) {
        proveedorService.deleteAll()
        productoService.deleteAll()
    }
}