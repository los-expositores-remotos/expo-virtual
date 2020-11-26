package ar.edu.unq.modelo

import org.bson.types.ObjectId
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class ProductoTest : ModelObjectWithBsonIdTest<Producto>(Producto::class.java) {
    private lateinit var producto: Producto
    private lateinit var image1: String
    private lateinit var image2: String
    private lateinit var image3: String
    private lateinit var image4: String
    private lateinit var image5: String
    private lateinit var images: MutableSet<String>
    private lateinit var tag1: String
    private lateinit var tag2: String
    private lateinit var tag3: String
    private lateinit var tag4: String
    private lateinit var tag5: String
    private lateinit var tags: MutableSet<String>

    @Before
    override fun setUp(){
        super.setUp()
        this.producto = Producto(ObjectId(), "ElProducto", "SoyElProducto", 50, 22, 1000, 10, 10, 10, 100)
        this.image1 = "www.images.com/image1.png"
        this.image2 = "www.images.com/image2.png"
        this.image3 = "www.images.com/image3.png"
        this.image4 = "www.images.com/image4.png"
        this.image5 = "www.images.com/image5.png"
        this.images = emptySet<String>().toMutableSet()

        this.tag1 = "tag1"
        this.tag2 = "tag2"
        this.tag3 = "tag3"
        this.tag4 = "tag4"
        this.tag5 = "tag5"
        this.tags = emptySet<String>().toMutableSet()
    }


    @Test
    fun testCalcularVolumenYPeso() {
        var volumenResultante = 0
        var pesoTotal = 0
        val productoB = Producto(ObjectId(), "ElProducto", "SoyElProducto", 50, 22, 1000, 10, 10, 10, 200)
        val productoC = Producto(ObjectId(), "ElProducto", "SoyElProducto", 50, 22, 1000, 5, 30, 10, 10)
        listOf(producto, productoB, productoC).map { volumenResultante += it.longitud * it.ancho * it.alto; pesoTotal += it.pesoKg }
        assertEquals(3500, volumenResultante)
        assertEquals(310, pesoTotal)
    }

    @Test
    fun testAgregarImagenes() {
        assertEquals(this.images, this.producto.listImages.toSet())
        producto.addImage(image1)
        this.images.add(image1)
        assertEquals(this.images, this.producto.listImages.toSet())
        producto.addImage(listOf(this.image2, this.image3, this.image4, this.image5))
        this.images.addAll(listOf(this.image2, this.image3, this.image4, this.image5))
        assertEquals(this.images, this.producto.listImages.toSet())
    }

    @Test
    fun testAgregarTags(){
        assertEquals(this.tags, this.producto.listTags.toSet())
        this.producto.addTag(tag1)
        this.tags.add(tag1)
        assertEquals(this.tags, this.producto.listTags.toSet())
        this.producto.addTag(listOf(this.tag2, this.tag3, this.tag4, this.tag5))
        this.tags.addAll(listOf(this.tag2, this.tag3, this.tag4, this.tag5))
        assertEquals(this.tags, this.producto.listTags.toSet())
    }

    @Test
    fun testCargarVentas(){
        val ventasIniciales = 0
        val primerasVentas = 7
        val segundasVentas = 20
        assertEquals(ventasIniciales, this.producto.vendidos)
        this.producto.cargarVenta(primerasVentas)
        assertEquals(ventasIniciales + primerasVentas, this.producto.vendidos)
        this.producto.cargarVenta(segundasVentas)
        assertEquals(ventasIniciales + primerasVentas + segundasVentas, this.producto.vendidos)
    }

    @Test
    fun testDescontarStock(){
        val stockInicial = 50
        val primerDescuentoDeStock = 7
        val segundoDescuentoDeStock = 20
        assertEquals(stockInicial, this.producto.stock)
        this.producto.cargarVenta(primerDescuentoDeStock)
        assertEquals(stockInicial - primerDescuentoDeStock, this.producto.stock)
        this.producto.cargarVenta(segundoDescuentoDeStock)
        assertEquals(stockInicial - primerDescuentoDeStock - segundoDescuentoDeStock, this.producto.stock)
    }
}