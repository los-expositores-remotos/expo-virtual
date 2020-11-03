package ar.edu.unq.API.controllers

import ar.edu.unq.API.CompanyViewMapper
import ar.edu.unq.API.NotFoundException
import ar.edu.unq.API.ProductViewMapper
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.ProductoService
import ar.edu.unq.services.ProveedorService
import java.util.ArrayList

class AuxiliaryFunctions(val backendProveedorService: ProveedorService, val backendProductoService: ProductoService) {

    //TODO: toda la funcion es reemplazable por iter.flatMap { it } jaja

    fun productoClassToProductoView(p: Producto): ProductViewMapper {
        return  ProductViewMapper(p.id.toString(), p.idProveedor.toString(), p.itemName, p.description, p.listImages, p.stock, p.itemPrice, p.promotionalPrice)
    }

    fun proveedorClassToProveedorView(p: Proveedor): CompanyViewMapper {
        return CompanyViewMapper(p.id.toString(), p.companyName, p.companyImage, p.facebook, p.instagram, p.web, this.productoClassListToProductoViewList(p.productos))
    }

    fun productoClassListToProductoViewList(lista: MutableCollection<Producto>): List<ProductViewMapper> {
        return lista.map { this.productoClassToProductoView(it) }
    }

    fun proveedorClassListToProveedorViewList(lista: MutableCollection<Proveedor>) : List<CompanyViewMapper> {
        return lista.map{ proveedorClassToProveedorView(it) }
    }
    /*
    fun classToView(objeto: Object) {
        objeto.    /*{
            Producto::class -> this.productoClassToProductoView(objeto as Producto)
            else -> this.proveedorClassToProveedorView(objeto as Proveedor)
        }      // INTENTO DE PARAMETRIZAR CLASES CON SUS MAPPERS*/
    }

   inline fun <reified C> classListToViewList(lista: MutableCollection<C>) {
        when (C::class) {
            Producto::class -> this.productoClassListToProductoViewList(lista as MutableCollection<Producto>)
            Proveedor::class -> this.proveedorClassListToProveedorViewList(lista as MutableCollection<Proveedor>)
        }      // INTENTO DE PARAMETRIZAR CLASES CON SUS MAPPERS
    }

*/
}

