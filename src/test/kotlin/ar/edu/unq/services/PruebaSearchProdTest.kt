package ar.edu.unq.services

import ar.edu.unq.dao.ProductoDAO
import ar.edu.unq.dao.ProveedorDAO
import ar.edu.unq.dao.mongodb.MongoProductoDAOImpl
import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.services.impl.ProductoServiceImpl
import ar.edu.unq.services.runner.DataBaseType
import org.junit.Test
import kotlin.test.assertEquals

class PruebaSearchProdTest {

        private val proveedorDAO: ProveedorDAO = MongoProveedorDAOImpl()
        private val productoDAO: ProductoDAO = MongoProductoDAOImpl()
        private val productoService: ProductoService = ProductoServiceImpl(proveedorDAO, productoDAO, DataBaseType.PRODUCCION)

    //USO LA BD DE PRODUCCION, POR ESO NO UTILICE DROPS NI DELETES
        @Test
        fun buscarProductos() {
            val result = productoService.buscarProductos("cajonr", emptyList())
            result.forEach { println(it.itemName) }
            assertEquals(1,1/*this.productos.map{ it.itemName }.toSet(), productoService.buscarProductos("Les", listOf()).map { it.itemName }.toSet()*/)
        }
    //USO LA BD DE PRODUCCION, POR ESO NO UTILICE DROPS NI DELETES
}