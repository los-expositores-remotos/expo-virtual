package ar.edu.unq.services.impl

import ar.edu.unq.dao.ProveedorDAO
import ar.edu.unq.modelo.BuscadorProveedores
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.ProveedorService
import ar.edu.unq.services.impl.exceptions.ProveedorExistenteException
import ar.edu.unq.services.impl.exceptions.ProveedorInexistenteException
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner
import ar.edu.unq.services.runner.TransactionType


class ProveedorServiceImpl(
        private val proveedorDAO: ProveedorDAO,
        private val dataBaseType: DataBaseType
) : ProveedorService {
    override fun buscarProveedores(texto: String): MutableList<Proveedor> {
        val proveedores = recuperarATodosLosProveedores()
        return BuscadorProveedores.filtrar(texto, proveedores)
    }

    override fun crearProveedor(proveedor: Proveedor) {
        TransactionRunner.runTrx({
            this.asegurarQueProveedorNoExista(proveedor.id.toString())
            this.proveedorDAO.save(proveedor)
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun recuperarProveedor(id: String): Proveedor {
        return TransactionRunner.runTrx({
            this.obtenerProveedor(id)
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun recuperarATodosLosProveedores(): Collection<Proveedor> {
        return TransactionRunner.runTrx({ this.proveedorDAO.getAll() }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun actualizarProveedor(proveedor: Proveedor) {
        TransactionRunner.runTrx({
            this.obtenerProveedor(proveedor.id.toString())
            this.proveedorDAO.update(proveedor, proveedor.id.toString())
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun borrarProveedor(id: String) {
        TransactionRunner.runTrx({
            this.obtenerProveedor(id)
            this.proveedorDAO.delete(id)
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun deleteAll() {
        TransactionRunner.runTrx({ this.proveedorDAO.deleteAll() }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    private fun obtenerProveedor(id: String): Proveedor{
        val proveedorRecuperado = this.proveedorDAO.get(id)
        return proveedorRecuperado?:throw ProveedorInexistenteException("El proveedor no existe")
    }

    private fun asegurarQueProveedorNoExista(id: String) {
        if(this.proveedorDAO.get(id) != null){
            throw ProveedorExistenteException("El proveedor ya existe")
        }
    }
}