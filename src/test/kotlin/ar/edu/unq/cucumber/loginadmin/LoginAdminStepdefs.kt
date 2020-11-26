package ar.edu.unq.cucumber.loginadmin

import ar.edu.unq.dao.mongodb.MongoAdminDAOImpl
import ar.edu.unq.modelo.Admin
import ar.edu.unq.services.AdminService
import ar.edu.unq.services.impl.AdminServiceImpl
import ar.edu.unq.services.impl.exceptions.AdministradorInexistenteException
import ar.edu.unq.services.runner.DataBaseType
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.Scenario
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class LoginAdminStepdefs {

    private val admin = Admin("agustina", "pinero")
    private val adminService: AdminService = AdminServiceImpl(MongoAdminDAOImpl(), DataBaseType.TEST)
    private var adminInexistenteException: AdministradorInexistenteException? = null
    private lateinit var adminRecuperado: Admin

    @Before
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