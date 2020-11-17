package ar.edu.unq.cucumber.eliminarunaimagendeunproducto

import ar.edu.unq.dao.mongodb.MongoProductoDAOImpl
import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.impl.ProductoServiceImpl
import ar.edu.unq.services.impl.ProveedorServiceImpl
import ar.edu.unq.services.runner.DataBaseType
import cucumber.api.java.After
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.bson.types.ObjectId
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class EliminarUnaImagenDeUnProductoMyStepdefs {

    private val productoService = ProductoServiceImpl(MongoProveedorDAOImpl(), MongoProductoDAOImpl(), DataBaseType.TEST)
    private val proveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.TEST)
    private val producto = Producto(ObjectId(), "ElProducto", "SoyElProducto", 50, 22, 1000)


    @Given("^Un producto con las imagenes$")
    fun unProductoConLasImagenes(imagenes: List<String>) {
        val proveedor = Proveedor("LaCompany", "www.images.com/lacompany.png", "www.facebook.com/LaCompany", "www.instagram.com/LaCompany", "www.lacompany.com")
        proveedorService.crearProveedor(proveedor)
        proveedor.addProduct(producto)
        producto.addImage(imagenes)
        productoService.nuevoProducto(producto)
    }

    @When("^Elimino la imagen \"([^\"]*)\"$")
    fun eliminoLaImagen(imagen: String?) {
        val productoRecuperado = productoService.recuperarProducto(producto.id.toString())
        productoRecuperado.listImages.remove(imagen)
        productoService.actualizarProducto(productoRecuperado)
    }

    @Then("^El producto contiene solo a la imagen \"([^\"]*)\"$")
    fun elProductoContieneSoloALaImagen(imagen: String?) {
        val productoRecuperado = productoService.recuperarProducto(producto.id.toString())
        assertEquals(setOf(imagen), productoRecuperado.listImages.toSet())
    }

    @Then("^El producto no contiene a la imagen \"([^\"]*)\"$")
    fun elProductoNoContieneALaImagen(imagen: String?) {
        val productoRecuperado = productoService.recuperarProducto(producto.id.toString())
        assertFalse(productoRecuperado.listImages.contains(imagen))
    }

    @After
    fun deleteAll(){
        proveedorService.deleteAll()
        productoService.deleteAll()
    }
}