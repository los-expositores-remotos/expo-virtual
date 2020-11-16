package ar.edu.unq.cucumber.formulariodecargadeproveedor

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
import org.bson.types.ObjectId
import kotlin.test.assertEquals

class FormularioDeCargaDeProveedorStepDefs {
    private lateinit var proveedor1: Proveedor
    private lateinit var proveedor2: Proveedor
    private val proveedorService: ProveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.TEST)

    @Given("^Un proveedor con los siguientes datos")
    fun unNombreDeProveedor(proveedorData: Map<String, String>) {
        this.proveedor1 = Proveedor(proveedorData["companyName"]!!, proveedorData["companyImage"]!!, proveedorData["facebook"]!!, proveedorData["instagram"]!!, proveedorData["web"]!!)
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

    @And("^Sus datos son$")
    fun susDatosSon(proveedorData: Map<String, String>) {
        val proveedorRecuperado = this.proveedorService.recuperarProveedor(this.proveedor1.id.toString())
        assertEquals(proveedorData["companyImage"], proveedorRecuperado.companyImage)
        assertEquals(proveedorData["facebook"], proveedorRecuperado.facebook)
        assertEquals(proveedorData["instagram"], proveedorRecuperado.instagram)
        assertEquals(proveedorData["web"], proveedorRecuperado.web)
    }

    @Given("^Un proveedor \"([^\"]*)\" sin ningun producto$")
    fun unProveedorSinNingunProducto(nombreProveedor: String?) {
        this.proveedor2 = Proveedor(nombreProveedor!!, "www.images.com/${nombreProveedor}.png", "www.facebook.com/$nombreProveedor", "www.instagram.com/$nombreProveedor", "www.${nombreProveedor}.com")
        this.proveedorService.crearProveedor(this.proveedor2)
    }

    @When("^Le agrego el producto \"([^\"]*)\"$")
    fun leAgregoElProducto(nombreProducto: String?) {
        val producto = Producto(ObjectId(), nombreProducto!!, "Soy$nombreProducto", 0, 0 , 0)
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