package ar.edu.unq.services

import ar.edu.unq.dao.ProductoDAO
import ar.edu.unq.dao.ProveedorDAO
import ar.edu.unq.dao.mongodb.MongoProductoDAOImpl
import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.impl.ProductoServiceImpl
import ar.edu.unq.services.impl.ProveedorServiceImpl
import ar.edu.unq.services.impl.exceptions.ProductoInexistenteException
import ar.edu.unq.services.impl.exceptions.ProductoNoEncontradoException
import ar.edu.unq.services.impl.exceptions.ProveedorInexistenteException
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner.runTrx
import ar.edu.unq.services.runner.TransactionType
import org.bson.types.ObjectId
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ProductoServiceImplTest {
    private val proveedorDAO: ProveedorDAO = MongoProveedorDAOImpl()
    private val productoDAO: ProductoDAO = MongoProductoDAOImpl()
    private val proveedorService: ProveedorService = ProveedorServiceImpl(proveedorDAO, DataBaseType.TEST)
    private val productoService: ProductoService = ProductoServiceImpl(proveedorDAO, productoDAO, DataBaseType.TEST)
    lateinit var proveedorA: Proveedor
    private lateinit var proveedorB: Proveedor
    private lateinit var proveedorC: Proveedor
    lateinit var productoA: Producto
    lateinit var productoB: Producto
    private lateinit var productoC: Producto
    private var productos: MutableSet<Producto> = emptySet<Producto>().toMutableSet()

    @Before
    fun setUp() {
        proveedorA = Proveedor("AA", "foto", "www.imagenes.com/lasegundacompany.png","https://www.facebook.com/GibsonES/", "https://www.instagram.com/gibsonguitar/?hl=es-la", "https://www.gibson.com/")
        proveedorB = Proveedor("BB", "foto", "www.imagenes.com/lasegundacompany.png","https://www.facebook.com/GibsonES/", "https://www.instagram.com/gibsonguitar/?hl=es-la", "https://www.gibson.com/")
        proveedorC = Proveedor("CC", "foto", "www.imagenes.com/lasegundacompany.png","https://www.facebook.com/GibsonES/", "https://www.instagram.com/gibsonguitar/?hl=es-la", "https://www.gibson.com/")
        productoA = Producto(proveedorA.id, "Les Paul", "SARASA", 7, 1000000, 800000, 10, 10, 10, 10)
        productoB = Producto(proveedorB.id, "Les PaulB", "SARASA", 7, 1000000, 800000, 10, 10, 10, 10)
        productoC = Producto(proveedorC.id, "Les PaulC", "SARASA", 7, 1000000, 800000, 10, 10, 10, 10)
        proveedorA.addProduct(productoA)
        proveedorB.addProduct(productoB)
        proveedorC.addProduct(productoC)
        runTrx({this.proveedorDAO.save(listOf(this.proveedorA, this.proveedorB, proveedorC))}, listOf(TransactionType.MONGO),DataBaseType.TEST)
        this.productos.addAll(setOf(this.productoA, this.productoB, this.productoC))
    }

    @Test
    fun testCreacionDeProducto(){
        val producto = Producto(this.proveedorA.id, "ElProducto", "Hola soy el producto", 20, 50, 48, 10, 10, 10, 10)
        this.productoService.nuevoProducto(producto)
        val productoRecuperado = this.productoService.recuperarProducto(producto.id.toString())
        assertEquals(producto, productoRecuperado)
    }

    @Test(expected = ProveedorInexistenteException::class)
    fun testCreacionDeProductoDeProveedorInexistente(){
        val producto = Producto(ObjectId(), "ElProducto", "Hola soy el producto", 20, 50, 48, 10, 10, 10, 10)
        this.productoService.nuevoProducto(producto)
    }

    @Test(expected = ProductoInexistenteException::class)
    fun testRecuperarProductoInexistente(){
        this.productoService.recuperarProducto(ObjectId().toString())
    }

    @Test
    fun testObtenerProductoPorNombre(){
        val productoRecuperado = this.productoService.obtenerProducto(this.proveedorA.id.toString(),this.productoA.itemName)
        assertEquals(this.productoA, productoRecuperado)
    }

    @Test(expected = ProveedorInexistenteException::class)
    fun testObtenerProductoDeUnProveedorInexistente(){
        this.productoService.obtenerProducto(ObjectId().toString(), this.productoA.itemName)
    }

    @Test(expected = ProductoNoEncontradoException::class)
    fun testObtenerProductoDeUnProveedorQueNoLoContiene(){
        this.productoService.obtenerProducto(this.proveedorA.id.toString(), this.productoC.itemName)
    }

    @Test
    fun testRecuperarTodosLosProductos(){
        val productosRecuperados = this.productoService.recuperarATodosLosProductos()
        assertEquals(this.productos, productosRecuperados.toSet())
    }

    @Test
    fun testActualizarProducto(){
        var productoRecuperado = this.productoService.recuperarProducto(this.productoA.id.toString())
        assertEquals("Les Paul", productoRecuperado.itemName)
        this.productoA.itemName = "ElProductoActualizado"
        this.productoService.actualizarProducto(this.productoA)
        productoRecuperado = this.productoService.recuperarProducto(this.productoA.id.toString())
        assertEquals("ElProductoActualizado", productoRecuperado.itemName)
    }

    @Test(expected = ProveedorInexistenteException::class)
    fun testActualizarProductoDeUnProveedorInexistente(){
        val producto = Producto(ObjectId(), "ProductoInexistente", "NoExisto", 0, 0, 0, 10, 10, 10, 10)
        this.productoService.actualizarProducto(producto)
    }

    @Test(expected = ProductoNoEncontradoException::class)
    fun testActualizarProductoDeUnProveedorQueNoLoContiene(){
        val producto = Producto(this.proveedorA.id, "ProductoInexistente", "NoExisto", 0, 0, 0, 10, 10, 10, 10)
        this.productoService.actualizarProducto(producto)
    }

    @Test
    fun testBorrarProducto(){
        var productosRecuperados = this.productoService.recuperarATodosLosProductos()
        assertTrue(productosRecuperados.contains(this.productoA))
        this.productoService.borrarProducto(this.productoA.id.toString())
        productosRecuperados = this.productoService.recuperarATodosLosProductos()
        assertFalse(productosRecuperados.contains(this.productoA))
    }

    @Test(expected = ProductoInexistenteException::class)
    fun testBorrarProductoInexistente(){
        val producto = Producto(ObjectId(), "ProductoInexistente", "NoExisto", 0, 0, 0, 10, 10, 10, 10)
        this.productoService.borrarProducto(producto.id.toString())
    }

    @Test
    fun testBorrarTodosLosProductos(){
        var productosRecuperados = this.productoService.recuperarATodosLosProductos()
        assertEquals(3, productosRecuperados.count())
        this.productoService.deleteAll()
        productosRecuperados = this.productoService.recuperarATodosLosProductos()
        assertEquals(0, productosRecuperados.count())
    }

    @Test
    fun buscarProductos(){
        assertEquals(this.productos.map{ it.itemName }.toSet(), productoService.buscarProductos("Les").map { it.itemName }.toSet())
    }

    @After
    fun deleteAll(){
        this.proveedorService.deleteAll()
    }

}