package ar.edu.unq.services.impl

import ar.edu.unq.dao.ProveedorDAO
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.modelo.Producto
import ar.edu.unq.services.ProveedorService
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner
import ar.edu.unq.services.runner.TransactionType

class ProveedorServiceImpl(
        private val proveedorDAO: ProveedorDAO,
        private val dataBaseType: DataBaseType
) : ProveedorService {
    override fun crearProveedor(proveedor: Proveedor) {
        val document = org.bson.Document()
        document.put("companyName", proveedor.companyName)
        document.put("companyImage", proveedor.companyImage)
        document.put("facebook", proveedor.facebook)
        document.put("instagram", proveedor.instagram)
        document.put("web", proveedor.web)
        //collection.insertOne(document)
    }

    override fun crearProveedor(companyName: String): Proveedor {
        val proveedor = Proveedor(companyName)
        TransactionRunner.runTrx({ this.proveedorDAO.save(proveedor) }, listOf(TransactionType.MONGO), this.dataBaseType)
        return proveedor
    }

    override fun recuperarProveedor(id: Int): Proveedor {
        TODO("Not yet implemented")
    }

    override fun recuperarATodosLosProveedores(): Collection<Proveedor> {
        TODO("Not yet implemented")
    }

    override fun agregarProducto(idProveedor: Int, producto: Producto): Producto {
        TODO("Not yet implemented")
    }

    override fun recuperarProducto(id: Int, nombreProducto: String): Producto {
        TODO("Not yet implemented")
    }

    override fun nuevoProducto(proveedor: String, producto: String): Producto {
        TODO("Not yet implemented")
    }

    override fun obtenerProveedor(proveedor: String): Proveedor {
        TODO("Not yet implemented")
    }
}