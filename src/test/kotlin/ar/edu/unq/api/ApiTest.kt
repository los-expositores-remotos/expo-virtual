package ar.edu.unq.api

import ar.edu.unq.API.CompanyViewMapper
import ar.edu.unq.API.ProductViewMapper
import ar.edu.unq.API.controllers.AuxiliaryFunctions
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ApiTest {

    lateinit var proveedorA: Proveedor
    private lateinit var proveedorB: Proveedor
    private lateinit var proveedorC: Proveedor
    lateinit var productoA: Producto
    lateinit var productoB: Producto
    private var listaProv: MutableCollection<Proveedor> = mutableListOf()
    private var listaProd: MutableCollection<Producto> = mutableListOf()
    private var listaDeListaProd: MutableList<MutableList<Producto>> = mutableListOf()
    private var auxFuc: AuxiliaryFunctions = AuxiliaryFunctions()


    @Before
    fun setUp() {

        proveedorA = Proveedor("AA","data:image" ,"https://www.facebook.com/GibsonES/", "https://www.instagram.com/gibsonguitar/?hl=es-la", "https://www.gibson.com/")
        proveedorB = Proveedor("BB", "data:image", "https://www.facebook.com/GibsonES/", "https://www.instagram.com/gibsonguitar/?hl=es-la", "https://www.gibson.com/")
        proveedorC = Proveedor("CC","data:image","https://www.facebook.com/GibsonES/", "https://www.instagram.com/gibsonguitar/?hl=es-la", "https://www.gibson.com/")
        productoA = Producto(proveedorB.id, "Les Paul", "A electric guitar.", 7, 1000000, 800000, 10, 10, 10, 10)
        productoB = Producto(proveedorB.id, "Les PaulB", "A electric guitar.", 7, 1000000, 800000, 10, 10, 10, 10)
        listaProv.add(proveedorA)
        listaProv.add(proveedorB)
        listaProd.add(productoA)
        listaProd.add(productoB)
        listaDeListaProd.add(listaProd as MutableList<Producto>)
        listaDeListaProd.add(listaProd as MutableList<Producto>)

    }

    @Test
    fun convertAProductoToViewMapper() {
        val prodMapp: Any = auxFuc.productoClassToProductoView(productoA)
        Assert.assertTrue(prodMapp is ProductViewMapper)
    }

    @Test
    fun convertAProveedorToViewMapper() {
        val provMapp: Any = auxFuc.proveedorClassToProveedorView(proveedorA)
        Assert.assertTrue(provMapp is CompanyViewMapper)
    }

    @Test
    fun convertAProductoListToViewMapperList() {
        val prodMappList: List<Any> = auxFuc.productoClassListToProductoViewList(listaProd)
        Assert.assertTrue(prodMappList is MutableList<*>)
        Assert.assertTrue(prodMappList.random() is ProductViewMapper)
    }

    @Test
    fun convertAProveedorListToViewMapperList() {
        val provMappList: List<Any> = auxFuc.proveedorClassListToProveedorViewList(listaProv)
        Assert.assertTrue(provMappList is MutableList<*>)
        Assert.assertTrue(provMappList.random() is CompanyViewMapper)
    }

  /*  @Test(expected = ConstraintViolationException::class)
    fun searchProductoWithResultNull(){
        val producto = auxFuc.searchProductoById("zaraza")
    }
*/


    @After
    fun dropAll() {}

}