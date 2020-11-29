package ar.edu.unq

import ar.edu.unq.dao.mongodb.MongoProductoDAOImpl
import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.ProductoService
import ar.edu.unq.services.ProveedorService
import ar.edu.unq.services.impl.ProductoServiceImpl
import ar.edu.unq.services.impl.ProveedorServiceImpl
import ar.edu.unq.services.runner.DataBaseType

open class AuxProdStepDefs {
    val proveedorA = Proveedor("AA", "foto", "www.imagenes.com/lasegundacompany.png","https://www.facebook.com/GibsonES/", "https://www.instagram.com/gibsonguitar/?hl=es-la", "https://www.gibson.com/")
    val proveedorService: ProveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.TEST)
    val productoService: ProductoService = ProductoServiceImpl(MongoProveedorDAOImpl(), MongoProductoDAOImpl(), DataBaseType.TEST)
    val productoA = Producto(proveedorA.id, "Les Paul", "SARASA", 7, 1000000, 800000, 10, 10, 10, 1)
    val productoB = Producto(proveedorA.id, "Stratocaster", "A electric guitar", 7, 1000, 800000, 10, 10, 10, 1)
}