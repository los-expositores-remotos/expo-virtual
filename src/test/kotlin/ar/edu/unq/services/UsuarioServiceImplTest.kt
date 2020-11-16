package ar.edu.unq.services

import ar.edu.unq.dao.mongodb.MongoUsuarioDAOImpl
import ar.edu.unq.modelo.Admin
import ar.edu.unq.modelo.Usuario
import ar.edu.unq.services.impl.UsuarioServiceImpl
import ar.edu.unq.services.impl.exceptions.*
import ar.edu.unq.services.runner.DataBaseType
import org.bson.types.ObjectId
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class UsuarioServiceImplTest {

    //--fun recuperarUsuario(id: String): Usuario
    //--fun recuperarUsuario(dni: Int): Usuario
    //--fun crearUsuario(usuario: Usuario)
    //--NO EXISTE LA FORMA DE CREAR UN ADMIN fun recuperarAdmin(userName: String?, password: String?): Admin
    //fun obtenerUsuario(id: String): Usuario
    //fun asegurarQueUsuarioNoExista(id: String)
    //fun recuperarATodosLosUsuarios(): Collection<Usuario> {

    private lateinit var usuario: Usuario
    private val usuarioService: UsuarioService = UsuarioServiceImpl(MongoUsuarioDAOImpl(), DataBaseType.TEST)
    private lateinit var usuarios : MutableSet<Usuario>

    @Before
    fun setUp(){
        this.usuario = Usuario("Tobias", "Towers", 39484178)
        this.usuarioService.crearUsuario(this.usuario)
        this.usuarios = setOf(this.usuario).toMutableSet()
    }

    @Test
    fun testParaCrearUsuario(){
        var usuariosRecuperados = this.usuarioService.recuperarATodosLosUsuarios().toSet()
        assertEquals(this.usuarios, usuariosRecuperados)
        val usuarioA = Usuario("Gra", "Daglio", 16900236)
        this.usuarioService.crearUsuario(usuarioA)
        this.usuarios.add(usuarioA)
        usuariosRecuperados = this.usuarioService.recuperarATodosLosUsuarios().toSet()
        assertEquals(this.usuarios, usuariosRecuperados)
    }

    @Test(expected = UsuarioExistenteException::class)
    fun testCreacionUsuarioYaExistente(){
        this.usuarioService.crearUsuario(this.usuario)
    }

    @Test(expected = UsuarioExistenteException::class)
    fun testCreacionUsuarioConDNIYaExistente(){
        this.usuario.id = ObjectId()
        this.usuarioService.crearUsuario(this.usuario)
    }

    @Test
    fun testRecuperarUsuario(){
        val usuarioRecuperado = usuarioService.recuperarUsuario(39484178)
        assertEquals(usuario.nombre, usuarioRecuperado.nombre)
    }

    @Test(expected = UsuarioInexistenteException::class)
    fun testRecuperarUnUsuarioInexistente(){
        this.usuarioService.recuperarUsuario(28380637)
    }

    @Test(expected = UsuarioInexistenteException::class)
    fun testRecuperarUnUsuarioInexistentePorId(){
        this.usuarioService.recuperarUsuario(ObjectId().toString())
    }

    @Test
    fun testRecuperarUnUsuarioPorId(){
        val usuarioRecuperado = this.usuarioService.recuperarUsuario(this.usuario.id.toString())
        assertEquals(this.usuario, usuarioRecuperado)
    }

    @Test
    fun testRecuperarATodosLosUsuarios(){
        val usuarioAdrian = Usuario("Adrian", "Cardozo", 1200000000)
        this.usuarioService.crearUsuario(usuarioAdrian)
        this.usuarios.add(usuarioAdrian)
        val usuariosRecuperados = this.usuarioService.recuperarATodosLosUsuarios().toSet()
        assertEquals(this.usuarios, usuariosRecuperados)
    }

    @Test
    fun testIngresoDniValido(){
        val usuarioB = Usuario("sarasa", "falopa", 1200000)
    }

    @Test(expected = UsuarioConDniInvalidoException::class)
    fun testRegistroConDniInvalido() {
        val usuarioB = Usuario("sarasa", "falopa", 120)
        this.usuarioService.crearUsuario(usuarioB)
    }

    @After
    fun deleteAll(){
        this.usuarioService.deleteAll()
    }
}