package ar.edu.unq.modelo

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BuscadorProductosTest {

    private lateinit var productoD: Producto
    lateinit var productoE: Producto
    private lateinit var productoF: Producto
    lateinit var proveedorA: Proveedor
    private lateinit var proveedorB: Proveedor
    private lateinit var proveedorC: Proveedor
    lateinit var productoA: Producto
    lateinit var productoB: Producto
    private lateinit var productoC: Producto

    @Before
    fun setUp() {
        proveedorA = Proveedor("AA", "foto", "www.images.com/lacompany.png", "https://www.facebook.com/GibsonES/", "https://www.instagram.com/gibsonguitar/?hl=es-la", "https://www.gibson.com/")
        proveedorB = Proveedor("BB", "foto", "www.images.com/lacompany.png","https://www.facebook.com/GibsonES/", "https://www.instagram.com/gibsonguitar/?hl=es-la", "https://www.gibson.com/")
        proveedorC = Proveedor("CC", "foto", "www.images.com/lacompany.png","https://www.facebook.com/GibsonES/", "https://www.instagram.com/gibsonguitar/?hl=es-la", "https://www.gibson.com/")
        productoA = Producto(proveedorA.id, "Les Paul", "A   electric guitar", 7, 100, 10, 10, 10, 10, 10)
        productoB = Producto(proveedorA.id, "Stratocaster", "Red guitar", 7, 1000, 100, 10, 10, 10, 10)
        productoC = Producto(proveedorA.id, "Explorer", "Black electric   guitar", 7, 1000000, 10000, 10, 10, 10, 10)
        productoD = Producto(proveedorA.id, "lala", "gg", 7, 1000000, 10000, 10, 10, 10, 10)
        productoE = Producto(proveedorA.id, "jojojo", "lelele", 7, 1000000, 10000, 10, 10, 10, 10)
        productoF = Producto(proveedorA.id, "jsjssj", "pepepe", 7, 1000000, 10000, 10, 10, 10, 10)

    }

    @Test
    fun getResultFromProductoSearch() {
        productoA.addTag("Guitar")
        productoB.addTag("Hola")
        val resultList = BuscadorProductos.filtrar("A   electric guitar", listOf(productoA, productoB, productoC, productoD, productoE, productoF))
        Assert.assertEquals("Les Paul", resultList.first().itemName)
        Assert.assertEquals("Explorer", resultList[1].itemName)
    }
}
