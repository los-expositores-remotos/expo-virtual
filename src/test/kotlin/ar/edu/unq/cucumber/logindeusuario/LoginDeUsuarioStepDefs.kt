package ar.edu.unq.cucumber.logindeusuario

import ar.edu.unq.dao.mongodb.MongoUsuarioDAOImpl
import ar.edu.unq.modelo.Usuario
import ar.edu.unq.services.UsuarioService
import ar.edu.unq.services.impl.UsuarioServiceImpl
import ar.edu.unq.services.impl.exceptions.UsuarioInexistenteException
import ar.edu.unq.services.runner.DataBaseType
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.Scenario
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class LoginDeUsuarioStepDefs {

    val usuario = Usuario("Tobias", "Towers", 39484178)
    val usuarioService: UsuarioService = UsuarioServiceImpl(MongoUsuarioDAOImpl(), DataBaseType.TEST)
    var exceptionUsuarioInexistenteException: UsuarioInexistenteException? = null
    lateinit var usuarioRecuperado: Usuario

    @Before
    fun registrarUsuario(){
        this.usuarioService.crearUsuario(usuario)
    }


    @Given("^un dni del usuario (\\d+)$")
    fun unDniDelUsuario(dni: Int?) {
      this.usuario.dni = dni!!
    }

    @When("^el usuario ingresa su dni para loguearse$")
    fun elUsuarioIngresaSuDniParaLoguearse() {
        usuarioRecuperado = this.usuarioService.recuperarUsuario(usuario.dni)
    }

    @Then("^el usuario se loguea$")
    fun elUsuarioSeLoguea() {
       assertEquals(usuario.dni, usuarioRecuperado.dni)
    }

    @When("^el usuario intenta loguearse$")
    fun elUsuarioIntentaLoguearse() {
        try {
            this.usuarioService.recuperarUsuario(usuario.dni)
        } catch(e: UsuarioInexistenteException){
            exceptionUsuarioInexistenteException = e
        }
    }

    @Then("^el usuario no se puede loguear por no estar registrado$")
    fun elUsuarioNoSePuedeLoguearPorNoEstarRegistrado() {
        assertNotNull(exceptionUsuarioInexistenteException)
    }

    @After
    fun doSomethingAfter(scenario: Scenario?) {
        usuarioService.deleteAll()
    }
}