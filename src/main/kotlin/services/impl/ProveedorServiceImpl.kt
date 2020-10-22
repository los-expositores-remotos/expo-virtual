package services.impl

import dao.ProveedorDAO
import modelo.Proveedor
import modelo.Producto
import services.ProveedorService

class ProveedorServiceImp(
        private val proveedorDAO: ProveedorDAO
        //private val dataDAO: DataDAO     creo que lo vamos a necesitar
) : ProveedorService {
    override fun crearProveedor(proveedor: Proveedor): Int {
        TODO("Not yet implemented")
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
}