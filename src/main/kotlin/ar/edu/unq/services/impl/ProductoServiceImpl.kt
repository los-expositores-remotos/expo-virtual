package ar.edu.unq.services.impl

import ar.edu.unq.dao.ProductoDAO
import ar.edu.unq.dao.ProveedorDAO
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.ProductoService
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner
import ar.edu.unq.services.runner.TransactionType
//import org.omg.CORBA.Object
import java.lang.Exception

class ProductoServiceImpl(
        private val proveedorDAO: ProveedorDAO,
        private val productoDAO: ProductoDAO,
        private val dataBaseType: DataBaseType
) : ProductoService {

    override fun nuevoProducto(producto: Producto) {
        TransactionRunner.runTrx({
            val proveedor: Proveedor = this.proveedorDAO.get(producto.idProveedor.toString())!!
            proveedor.addProduct(producto)
            this.proveedorDAO.update(proveedor, proveedor.id.toString())
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun obtenerProducto(proveedorId: String, nombreItem: String): Producto {
        val proveedorRecuperado = TransactionRunner.runTrx({ this.proveedorDAO.get(proveedorId) }, listOf(TransactionType.MONGO), this.dataBaseType)
        proveedorRecuperado?:throw Exception("No existe el proveedor en la coleccion")
        val producto = TransactionRunner.runTrx({ this.productoDAO.get(proveedorId, nombreItem) }, listOf(TransactionType.MONGO), this.dataBaseType)
        return producto?:throw Exception("No existe el producto en la coleccion")
    }

    override fun actualizarProducto(producto: Producto) {
        TransactionRunner.runTrx({
            val proveedor: Proveedor = this.proveedorDAO.get(producto.idProveedor.toString())!!
            if(proveedor.productos.contains(producto)){
                proveedor.removeProduct(producto)
                proveedor.addProduct(producto)
            }else{
                throw Exception("No existe el producto en la coleccion del proveedor")
            }
            this.proveedorDAO.update(proveedor, proveedor.id.toString())
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun recuperarATodosLosProductos(): Collection<Producto> {
        return TransactionRunner.runTrx({ this.productoDAO.getAll() }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun recuperarProducto(id: String): Producto {
        return TransactionRunner.runTrx({ this.productoDAO.get(id) }, listOf(TransactionType.MONGO), this
                .dataBaseType)!! //TODO: testear
    }



    override fun borrarProducto(id: String) {
        TransactionRunner.runTrx({
            val producto: Producto = this.productoDAO.get(id)!!
            val proveedor: Proveedor = this.proveedorDAO.get(producto.idProveedor.toString())!!
            proveedor.removeProduct(producto)
            this.proveedorDAO.update(proveedor,proveedor.id.toString())
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun deleteAll() {
        TransactionRunner.runTrx({
            val proveedores = this.proveedorDAO.getAll()
            for(proveedor in proveedores){
                proveedor.productos.clear()
                this.proveedorDAO.update(proveedor,proveedor.id.toString())
            }
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

}