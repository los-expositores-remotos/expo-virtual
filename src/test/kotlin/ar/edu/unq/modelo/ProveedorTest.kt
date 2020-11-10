package ar.edu.unq.modelo

import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class ProveedorTest : ModelObjectWithBsonIdTest<Proveedor>(Proveedor::class.java) {
    private lateinit var producto1: Producto
    private lateinit var producto2: Producto
    private lateinit var proveedor: Proveedor

    @Before
    override fun setUp(){
        super.setUp()
        this.proveedor = Proveedor("LaCompany", "www.images.com/lacompany.png", "www.facebook.com/LaCompany", "www.instagram.com/LaCompany", "www.lacompany.com")
        this.producto1 = Producto()
        this.producto2 = Producto()
        this.proveedor.addProduct(this.producto1)
        this.proveedor.addProduct(this.producto2)
    }


    @Test
    fun testAgregarProducto() {
        assertEquals(this.proveedor.id, this.producto1.idProveedor)
        assertEquals(this.proveedor.id, this.producto2.idProveedor)
        assertEquals(setOf(this.producto1, this.producto2), this.proveedor.productos.toSet())
    }

    @Test
    fun testBorrarProducto() {
        this.proveedor.removeProduct(this.producto1)
        assertEquals(setOf(this.producto2), this.proveedor.productos.toSet())
        this.proveedor.removeProduct(this.producto2)
        assertEquals(emptySet(), this.proveedor.productos.toSet())
    }
}