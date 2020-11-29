package ar.edu.unq.cucumber.mostrartodoslosproveedoresdesdeelmenu

import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.ProveedorService
import ar.edu.unq.services.impl.ProveedorServiceImpl
import ar.edu.unq.services.runner.DataBaseType
import io.cucumber.java.After
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import kotlin.test.assertEquals


class MostrarTodosLosProveedoresDesdeElMenuStepDefs {

    private var listaDeProveedores : MutableSet<Proveedor> = emptySet<Proveedor>().toMutableSet()

    private var proveedorService: ProveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.TEST)

    @Given("^Una database de proveedores vacia$")
    fun unaDatabaseDeProveedoresVacia() {
        proveedorService.deleteAll()
    }

    @When("^Agrego un proveedor \"([^\"]*)\"$")
    fun agregoUnProveedor(proveedor1: String?) {
        listaDeProveedores.add(Proveedor(proveedor1!!,"hola.imagen","hola.banner ","www.facebook.com/empresa1","www.instragram.com/empresa1","www.empresa1.com.ar"))
        proveedorService.crearProveedor(listaDeProveedores.last())
    }

    @Then("^Los proveedores \"([^\"]*)\" y \"([^\"]*)\" estan cargados en la base de datos$")
    fun losProveedoresYEstanCargadosEnLaBaseDeDatos(proveedor1: String?, proveedor2: String?) {
        val proveedoresRecuperados = proveedorService.recuperarATodosLosProveedores().map { it.companyName }.toSet()
        assertEquals(setOf(proveedor1,proveedor2),proveedoresRecuperados)
    }

    @After
    fun deleteAll(){
        proveedorService.deleteAll()
    }
}