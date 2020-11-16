package ar.edu.unq.cucumber

import ar.edu.unq.dao.mongodb.MongoAdminDAOImpl
import ar.edu.unq.modelo.Admin
import ar.edu.unq.services.AdminService
import ar.edu.unq.services.impl.AdminServiceImpl
import ar.edu.unq.services.impl.exceptions.AdministradorInexistenteException
import ar.edu.unq.services.runner.DataBaseType
import cucumber.api.Scenario
import cucumber.api.java.After
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class LoginAdminStepdefs {

    val admin = Admin("agustina", "pinero")
    val adminService: AdminService = AdminServiceImpl(MongoAdminDAOImpl(), DataBaseType.TEST)
    var adminInexistenteException: AdministradorInexistenteException? = null
    lateinit var adminRecuperado: Admin

    @cucumber.api.java.Before
    fun crearAdmin(){
        this.adminService.crearAdmin(admin)
    }

    @Given("^un usuario \"([^\"]*)\"$")
    fun unUsuario(userName: String?) {
        this.admin.userName = userName!!
    }

    @Given("^un password \"([^\"]*)\"$")
    fun unPassword(password: String?) {
        this.admin.password = password!!
    }

    @When("^el admin ingresa su usuario y password$")
    fun elAdminIngresaSuUsuarioYPassword() {
        adminRecuperado = this.adminService.recuperarAdmin(admin.userName, admin.password)
    }

    @Then("^se loguea a la aplicacion$")
    fun seLogueaALaAplicacion() {
        assertEquals(admin.userName, adminRecuperado.userName)
        assertEquals(admin.password, adminRecuperado.password)
    }

    @When("^el admin intenta loguearse$")
    fun elAdminIntentaLoguearse(){
        val any = try {
            this.adminService.recuperarAdmin(admin.userName, admin.password)

        } catch (e: AdministradorInexistenteException) {
            adminInexistenteException = e

        }
    }

    @Then("^el admin no se puede loguear por no estar registrado$")
    fun elAdminNoSePuedeLoguearPorNoEstarRegistrado() {
        assertNotNull(adminInexistenteException)
    }

    @After
    fun doSomethingAfter(scenario: Scenario?) {
        adminService.deleteAll()
    }

}