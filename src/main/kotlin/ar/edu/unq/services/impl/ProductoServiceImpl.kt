package ar.edu.unq.services.impl

import ar.edu.unq.dao.ProductoDAO
import ar.edu.unq.modelo.Producto
import ar.edu.unq.services.ProductoService
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner
import ar.edu.unq.services.runner.TransactionType
import org.bson.types.ObjectId

class ProductoServiceImpl(
        private val productoDAO: ProductoDAO,
        private val dataBaseType: DataBaseType
) : ProductoService {

    override fun nuevoProducto(producto: Producto) {
        TransactionRunner.runTrx({ this.productoDAO.update(producto, producto.id.toString()) }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun obtenerProducto(productoId: String): Producto? {
        return TransactionRunner.runTrx({ this.productoDAO.get(productoId) }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun actualizarProducto(producto: Producto) {
        TransactionRunner.runTrx({ this.productoDAO.update(producto, producto.id.toString()) }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

}