package ar.edu.unq.services.impl

import ar.edu.unq.dao.ProveedorDAO
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.modelo.Producto
import ar.edu.unq.services.ProveedorService
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner
import ar.edu.unq.services.runner.TransactionType
import org.bson.types.ObjectId
import javax.xml.crypto.dsig.TransformService

class ProveedorServiceImpl(
        private val proveedorDAO: ProveedorDAO,
        private val dataBaseType: DataBaseType
) : ProveedorService {

    override fun crearProveedor(proveedor: Proveedor) {
        TransactionRunner.runTrx({ this.proveedorDAO.save(proveedor) }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun recuperarProveedor(id: String): Proveedor? {
        return TransactionRunner.runTrx({ this.proveedorDAO.get(id) }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun recuperarATodosLosProveedores(): Collection<Proveedor> {
        return TransactionRunner.runTrx({ this.proveedorDAO.getAll() }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun actualizarProveedor(proveedor: Proveedor) {
        TransactionRunner.runTrx({ this.proveedorDAO.update(proveedor, proveedor.id.toString()) }, listOf(TransactionType
                .MONGO),
                this
                .dataBaseType)
    }

    override fun borrarProveedor(id: String) {
        TransactionRunner.runTrx({ this.proveedorDAO.delete(id) }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun deleteAll() {
        TransactionRunner.runTrx({ this.proveedorDAO.deleteAll() }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

}