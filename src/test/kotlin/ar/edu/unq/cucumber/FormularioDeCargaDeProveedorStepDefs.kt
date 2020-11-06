package ar.edu.unq.cucumber

import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.ProveedorService
import ar.edu.unq.services.impl.ProveedorServiceImpl
import ar.edu.unq.services.runner.DataBaseType
import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import kotlin.test.assertEquals

class FormularioDeCargaDeProveedorStepDefs {
    private lateinit var proveedor1: Proveedor
    private lateinit var proveedor2: Proveedor
    private val proveedorService: ProveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.TEST)

    @Given("^Un nombre de proveedor \"([^\"]*)\"$")
    fun unNombreDeProveedor(nombreProveedor: String?) {
        this.proveedor1 = Proveedor(nombreProveedor!!)
    }

    @Given("^Un enlace a una imagen \"([^\"]*)\"$")
    fun unEnlaceAUnaImagen(linkImage: String?) {
        this.proveedor1.companyImage = linkImage!!
    }

    @Given("^Un enlace a facebook \"([^\"]*)\"$")
    fun unEnlaceAFacebook(linkFacebook: String?) {
        this.proveedor1.facebook = linkFacebook!!
    }

    @Given("^Un enlace a instagram \"([^\"]*)\"$")
    fun unEnlaceAInstagram(linkInstagram: String?) {
        this.proveedor1.instagram = linkInstagram!!
    }

    @Given("^Un enlace a su pagina web \"([^\"]*)\"$")
    fun unEnlaceASuPaginaWeb(linkWeb: String?) {
        this.proveedor1.web = linkWeb!!
    }

    @When("^Creo al proveedor con esos datos$")
    fun creoAlProveedorConEsosDatos() {
        this.proveedorService.crearProveedor(this.proveedor1)
    }

    @Then("^El proveedor \"([^\"]*)\" se encuentra en la base de datos$")
    fun elProveedorSeEncuentraEnLaBaseDeDatos(nombreProveedor: String?) {
        val proveedorRecuperado = this.proveedorService.recuperarProveedor(this.proveedor1.id.toString())
        assertEquals(nombreProveedor, proveedorRecuperado.companyName)
    }

    @And("^Sus datos son \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    fun susDatosSon(linkImage: String?, linkFacebook: String?, linkInstagram: String?, linkWeb: String?) {
        val proveedorRecuperado = this.proveedorService.recuperarProveedor(this.proveedor1.id.toString())
        assertEquals(linkImage, proveedorRecuperado.companyImage)
        assertEquals(linkFacebook, proveedorRecuperado.facebook)
        assertEquals(linkInstagram, proveedorRecuperado.instagram)
        assertEquals(linkWeb, proveedorRecuperado.web)
    }

    @Given("^Un proveedor \"([^\"]*)\" sin ningun productos$")
    fun unProveedorSinNingunProductos(nombreProveedor: String?) {
        this.proveedor2 = Proveedor(nombreProveedor!!)
        this.proveedorService.crearProveedor(this.proveedor2)
    }

    @When("^Le agrego el producto \"([^\"]*)\"$")
    fun leAgregoElProducto(nombreProducto: String?) {
        val producto = Producto()
        producto.itemName = nombreProducto!!
        this.proveedor2.addProduct(producto)
        this.proveedorService.actualizarProveedor(this.proveedor2)
    }

    @Then("^Los productos del proveedor recuperado son \"([^\"]*)\" y \"([^\"]*)\"$")
    fun losProductosDelProveedorRecuperadoSonY(producto1: String?, producto2: String?) {
        val proveedorRecuperado = this.proveedorService.recuperarProveedor(this.proveedor2.id.toString())
        val productosRecuperados = proveedorRecuperado.productos.map{ it.itemName }.toSet()
        val productos = setOf(producto1!!, producto2!!)
        assertEquals(productos, productosRecuperados)
    }

    @cucumber.api.java.After
    fun clear() {
        proveedorService.deleteAll()
    }
}