package ar.edu.unq.api

import ar.edu.unq.API.CompanyViewMapper
import ar.edu.unq.API.ProductViewMapper
import ar.edu.unq.API.controllers.AuxiliaryFunctions
import ar.edu.unq.dao.mongodb.MongoProductoDAOImpl
import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.impl.ProductoServiceImpl
import ar.edu.unq.services.impl.ProveedorServiceImpl
import ar.edu.unq.services.runner.DataBaseType
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class ApiTest {

    lateinit var proveedorA: Proveedor
    lateinit var proveedorB: Proveedor
    lateinit var proveedorC: Proveedor
    lateinit var productoA: Producto
    lateinit var productoB: Producto
    var listaProv: MutableCollection<Proveedor> = mutableListOf()
    var listaProd: MutableCollection<Producto> = mutableListOf()
    var listaDeListaProd: MutableList<MutableList<Producto>> = mutableListOf()

    val backendProductoService = ProductoServiceImpl(MongoProveedorDAOImpl(), MongoProductoDAOImpl(), DataBaseType.PRODUCCION)
    val backendProveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.PRODUCCION)
    var auxFuc: AuxiliaryFunctions = AuxiliaryFunctions(backendProveedorService, backendProductoService)


    @Before
    fun setUp() {

        proveedorA = Proveedor("AA","data:image" ,"https://www.facebook.com/GibsonES/", "https://www.instagram.com/gibsonguitar/?hl=es-la", "https://www.gibson.com/")
        proveedorB = Proveedor("BB", "data:image", "https://www.facebook.com/GibsonES/", "https://www.instagram.com/gibsonguitar/?hl=es-la", "https://www.gibson.com/")
        proveedorC = Proveedor("CC","data:image","https://www.facebook.com/GibsonES/", "https://www.instagram.com/gibsonguitar/?hl=es-la", "https://www.gibson.com/")
        productoA = Producto(proveedorB.id, "Les Paul", "A electric guitar.", 7, 1000000, 800000)
        productoB = Producto(proveedorB.id, "Les PaulB", "A electric guitar.", 7, 1000000, 800000)
        listaProv.add(proveedorA)
        listaProv.add(proveedorB)
        listaProd.add(productoA)
        listaProd.add(productoB)
        listaDeListaProd.add(listaProd as MutableList<Producto>)
        listaDeListaProd.add(listaProd as MutableList<Producto>)

    }

    @Test
    fun convertAProductoToViewMapper() {
        val prodMapp = auxFuc.productoClassToProductoView(productoA)
        Assert.assertTrue(prodMapp is ProductViewMapper)
    }

    @Test
    fun convertAProveedorToViewMapper() {
        val provMapp = auxFuc.proveedorClassToProveedorView(proveedorA)
        Assert.assertTrue(provMapp is CompanyViewMapper)
    }

    @Test
    fun convertAProductoListToViewMapperList() {
        val prodMappList = auxFuc.productoClassListToProductoViewList(listaProd)
        Assert.assertTrue(prodMappList is MutableList<*>)
        Assert.assertTrue(prodMappList.random() is ProductViewMapper)
    }

    @Test
    fun convertAProveedorListToViewMapperList() {
        val provMappList = auxFuc.proveedorClassListToProveedorViewList(listaProv)
        Assert.assertTrue(provMappList is MutableList<*>)
        Assert.assertTrue(provMappList.random() is CompanyViewMapper)
    }

    @Test
    fun convertListOfLIstToAList(){
        val prodList = auxFuc.makeListFromListofList(listaDeListaProd)
        Assert.assertTrue(prodList is MutableList<*>)
        Assert.assertTrue(prodList!!.random() is Producto)
    }

  /*  @Test(expected = ConstraintViolationException::class)
    fun searchProductoWithResultNull(){
        val producto = auxFuc.searchProductoById("zaraza")
    }
*/


    @After
    fun dropAll() {}

}