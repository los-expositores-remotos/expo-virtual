package ar.edu.unq.services

import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.dao.mongodb.MongoUsuarioDAOImpl
import ar.edu.unq.modelo.Admin
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.modelo.Usuario
import ar.edu.unq.services.impl.ProveedorServiceImpl
import ar.edu.unq.services.impl.UsuarioServiceImpl
import ar.edu.unq.services.impl.exceptions.AdministradorInexistenteException
import ar.edu.unq.services.impl.exceptions.ProveedorExistenteException
import ar.edu.unq.services.impl.exceptions.UsuarioExistenteException
import ar.edu.unq.services.impl.exceptions.UsuarioInexistenteException
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner
import ar.edu.unq.services.runner.TransactionType
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
    private lateinit var admin: Admin
    private val usuarioService: UsuarioService = UsuarioServiceImpl(MongoUsuarioDAOImpl(), DataBaseType.TEST)
    private lateinit var usuarios : MutableSet<Usuario>

    @Before
    fun setUp(){
        this.usuario = Usuario("Tobias", "Towers", 39484178)
        this.admin = Admin ("agustina", "pinero")
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

    @Test
    fun testRecuperarUsuario(){
        val usuarioRecuperado = usuarioService.recuperarUsuario(39484178)
        assertEquals(usuario.nombre, usuarioRecuperado.nombre)
    }

    @Test(expected = UsuarioInexistenteException::class)
    fun testRecuperarUnUsuarioInexistente(){
        this.usuarioService.recuperarUsuario(28380637)
    }

    @Test(expected = AdministradorInexistenteException::class)
    fun testRecuperarUnAdminInexitente(){
        this.usuarioService.recuperarAdmin("sarasa", "sanchez")
    }

    @Test
    fun testRecuperarUnUsuarioPorId(){
        val usuarioRecuperado = this.usuarioService.recuperarUsuario(this.usuario.id.toString())
        assertEquals(this.usuario, usuarioRecuperado)
    }

    @Test
    fun testRecuperarATodosLosUsuarios(){
        val usuarioAdrian = Usuario("Adrian", "Cardozo", 1200)
        this.usuarioService.crearUsuario(usuarioAdrian)
        this.usuarios.add(usuarioAdrian)
        val usuariosRecuperados = this.usuarioService.recuperarATodosLosUsuarios().toSet()
        assertEquals(this.usuarios, usuariosRecuperados)
    }

    @After
    fun deleteAll(){
        this.usuarioService.deleteAll()
    }
}