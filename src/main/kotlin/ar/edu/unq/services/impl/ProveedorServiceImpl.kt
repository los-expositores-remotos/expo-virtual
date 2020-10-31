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
        this.proveedorDAO.saveInTrx(proveedor, this.dataBaseType)
    }

    override fun recuperarProveedor(id: String): Proveedor? {
        return this.proveedorDAO.getByInTrx("id", id, this.dataBaseType)
    }

    override fun recuperarATodosLosProveedores(): Collection<Proveedor> {
        return this.proveedorDAO.getAllInTrx(this.dataBaseType)
    }

    override fun actualizarProveedor(proveedor: Proveedor) {
        this.proveedorDAO.updateInTrx(proveedor, proveedor.id.toString(), this.dataBaseType)
    }

    override fun borrarProveedor(id: String) {
        this.proveedorDAO.deleteInTrx(id, this.dataBaseType)
    }

    override fun deleteAll() {
        TransactionRunner.runTrx({ this.proveedorDAO.deleteAll() }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

}