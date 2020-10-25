package ar.edu.unq.services.impl

import ar.edu.unq.dao.ProveedorDAO
import ar.edu.unq.modelo.Producto
import ar.edu.unq.services.ProductoService
import ar.edu.unq.services.runner.DataBaseType

class ProductoServiceImpl(
    proveedorDAO: ProveedorDAO,
    dataBaseType: DataBaseType
) : ProductoService {
    override fun obtenerProducto(producto: String): Producto {
        TODO("Not yet implemented")
    }

    override fun actualizarProducto(producto: Producto) {
        TODO("Not yet implemented")
    }

}