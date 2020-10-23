package services.impl

import dao.ProveedorDAO
import modelo.Proveedor
import modelo.Producto
import services.ProveedorService

class ProveedorServiceImp(
        private val proveedorDAO: ProveedorDAO
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