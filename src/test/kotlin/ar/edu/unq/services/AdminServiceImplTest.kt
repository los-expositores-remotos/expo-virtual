package ar.edu.unq.services

import ar.edu.unq.dao.mongodb.MongoAdminDAOImpl
import ar.edu.unq.dao.mongodb.MongoUsuarioDAOImpl
import ar.edu.unq.modelo.Admin
import ar.edu.unq.modelo.Usuario
import ar.edu.unq.services.impl.AdminServiceImpl
import ar.edu.unq.services.impl.UsuarioServiceImpl
import ar.edu.unq.services.impl.exceptions.AdministradorExistenteException
import ar.edu.unq.services.impl.exceptions.AdministradorInexistenteException
import ar.edu.unq.services.impl.exceptions.UsuarioExistenteException
import ar.edu.unq.services.impl.exceptions.UsuarioInexistenteException
import ar.edu.unq.services.runner.DataBaseType
import org.bson.types.ObjectId
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class AdminServiceImplTest {
    private lateinit var admin1: Admin
    private lateinit var admin2: Admin
    private val adminService: AdminService = AdminServiceImpl(MongoAdminDAOImpl(), DataBaseType.TEST)
    private lateinit var admins : MutableSet<Admin>

    @Before
    fun setUp(){
        this.admin2 = Admin("Tobias", "Towers")
        this.admin1 = Admin ("agustina", "pinero")
        this.adminService.crearAdmin(this.admin2)
        this.admins = setOf(this.admin2).toMutableSet()
    }

    @Test
    fun testParaCrearAdmin(){
        var adminsRecuperados = this.adminService.recuperarATodosLosAdmin().toSet()
        assertEquals(this.admins, adminsRecuperados)
        val adminA = Admin("Gra", "Daglio")
        this.adminService.crearAdmin(adminA)
        this.admins.add(adminA)
        adminsRecuperados = this.adminService.recuperarATodosLosAdmin().toSet()
        assertEquals(this.admins, adminsRecuperados)
    }

    @Test(expected = AdministradorExistenteException::class)
    fun testCreacionAdminYaExistente(){
        this.adminService.crearAdmin(this.admin2)
    }

    @Test
    fun testRecuperarAdmin(){
        val adminRecuperado = adminService.recuperarAdmin("Tobias", "Towers")
        assertEquals(admin2, adminRecuperado)
    }

    @Test(expected = AdministradorInexistenteException::class)
    fun testRecuperarUnAdminInexitente(){
        this.adminService.recuperarAdmin("sarasa", "sanchez")
    }

    @Test(expected = AdministradorInexistenteException::class)
    fun testRecuperarUnAdminInexitentePorId(){
        this.adminService.recuperarAdmin(ObjectId().toString())
    }

    @Test
    fun testRecuperarUnAdminPorId(){
        val adminRecuperado = this.adminService.recuperarAdmin(this.admin2.id.toString())
        assertEquals(this.admin2, adminRecuperado)
    }

    @Test
    fun testRecuperarATodosLosAdmins(){
        val adminAdrian = Admin("Adrian", "Cardozo")
        this.adminService.crearAdmin(adminAdrian)
        this.admins.add(adminAdrian)
        val usuariosRecuperados = this.adminService.recuperarATodosLosAdmin().toSet()
        assertEquals(this.admins, usuariosRecuperados)
    }

    @After
    fun deleteAll(){
        this.adminService.deleteAll()
    }
}