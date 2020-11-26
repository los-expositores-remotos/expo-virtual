package ar.edu.unq.services

import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.impl.ProveedorServiceImpl
import ar.edu.unq.services.impl.exceptions.ProveedorExistenteException
import ar.edu.unq.services.impl.exceptions.ProveedorInexistenteException
import ar.edu.unq.services.runner.DataBaseType
import org.bson.types.ObjectId
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class ProveedorServiceImplTest {

    private lateinit var proveedores: MutableSet<Proveedor>
    private lateinit var proveedor: Proveedor
    private val proveedorService: ProveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.TEST)

    @Before
    fun setUp(){
        this.proveedor = Proveedor("LaCompany", "www.images.com/lacompany.png", "www.banners.com/lacompany.png", "www.facebook.com/LaCompany"
                , "www.instagram.com/LaCompany", "www.lacompany.com")
        this.proveedorService.crearProveedor(this.proveedor)
        this.proveedores = setOf(this.proveedor).toMutableSet()
    }

    @Test
    fun testCreacionProveedor(){
        var proveedoresRecuperados = this.proveedorService.recuperarATodosLosProveedores().toSet()
        assertEquals(this.proveedores, proveedoresRecuperados)
        val proveedor = Proveedor("LaCompany", "www.images.com/lacompany.png", "www.banners.com/lacompany.png", "www.facebook.com/LaCompany"
                , "www.instagram.com/LaCompany", "www.lacompany.com")
        this.proveedorService.crearProveedor(proveedor)
        this.proveedores.add(proveedor)
        proveedoresRecuperados = this.proveedorService.recuperarATodosLosProveedores().toSet()
        assertEquals(this.proveedores, proveedoresRecuperados)
    }

    @Test(expected = ProveedorExistenteException::class)
    fun testCreacionProveedorYaExistente(){
        this.proveedorService.crearProveedor(this.proveedor)
    }

    @Test
    fun testRecuperarProveedor() {
        val proveedorRecuperado = this.proveedorService.recuperarProveedor(this.proveedor.id.toString())
        assertEquals(this.proveedor, proveedorRecuperado)
    }

    @Test(expected = ProveedorInexistenteException::class)
    fun testRecuperarProveedorInexistente(){
        this.proveedorService.recuperarProveedor(ObjectId().toString())
    }

    @Test
    fun testRecuperarATodosLosProveedores(){
        val proveedoresRecuperados = this.proveedorService.recuperarATodosLosProveedores().toSet()
        assertEquals(this.proveedores, proveedoresRecuperados)
    }

    @Test
    fun testActualizarProveedor(){
        var proveedorRecuperado = this.proveedorService.recuperarProveedor(this.proveedor.id.toString())
        assertEquals(this.proveedor.companyName, proveedorRecuperado.companyName)
        this.proveedor.companyName = "LaCompanyActualizada"
        this.proveedorService.actualizarProveedor(this.proveedor)
        proveedorRecuperado = this.proveedorService.recuperarProveedor(this.proveedor.id.toString())
        assertEquals(this.proveedor.companyName, proveedorRecuperado.companyName)
    }

    @Test(expected = ProveedorInexistenteException::class)
    fun testActualizarProveedorInexistente(){
        this.proveedor.id = ObjectId()
        this.proveedorService.actualizarProveedor(this.proveedor)
    }

    @Test
    fun testBorrarProveedor(){
        var proveedoresRecuperados = this.proveedorService.recuperarATodosLosProveedores()
        assertTrue(proveedoresRecuperados.contains(this.proveedor))
        this.proveedorService.borrarProveedor(this.proveedor.id.toString())
        proveedoresRecuperados = this.proveedorService.recuperarATodosLosProveedores()
        assertFalse(proveedoresRecuperados.contains(this.proveedor))
    }

    @Test(expected = ProveedorInexistenteException::class)
    fun testBorrarProveedorInexistente(){
        this.proveedorService.borrarProveedor(ObjectId().toString())
    }

    @Test
    fun testDeleteAll(){
        var proveedoresRecuperados = this.proveedorService.recuperarATodosLosProveedores()
        assertNotEquals(0, proveedoresRecuperados.count())
        this.proveedorService.deleteAll()
        proveedoresRecuperados = this.proveedorService.recuperarATodosLosProveedores()
        assertEquals(0, proveedoresRecuperados.count())
    }

    @After
    fun deleteAll(){
        this.proveedorService.deleteAll()
    }
}