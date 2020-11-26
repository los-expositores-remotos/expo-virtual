package ar.edu.unq.API.controllers

import ar.edu.unq.API.*
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.ProductoService
import ar.edu.unq.services.ProveedorService
import io.javalin.http.Context

class AuxiliaryFunctions(val backendProveedorService: ProveedorService, val backendProductoService: ProductoService) {

    //TODO: toda la funcion es reemplazable por iter.flatten() jaja

    fun productoClassToProductoView(p: Producto): ProductViewMapper {
        return ProductViewMapper(p.id.toString(), p.idProveedor.toString(), p.itemName, p.description, p.listImages, p.stock, p.vendidos, p.itemPrice, p.promotionalPrice, p.longitud, p.ancho, p.alto, p.pesoKg)
    }

    fun proveedorClassToProveedorView(p: Proveedor): CompanyViewMapper {
        return CompanyViewMapper(p.id.toString(), p.companyName, p.companyImage, p.companyBanner, p.facebook, p.instagram, p.web, this.productoClassListToProductoViewList(p.productos))
    }

    fun productoClassListToProductoViewList(lista: MutableCollection<Producto>): List<ProductViewMapper> {
        return lista.map { productoClassToProductoView(it) }
    }

    fun proveedorClassListToProveedorViewList(lista: MutableCollection<Proveedor>) : List<CompanyViewMapper> {
        return lista.map{ proveedorClassToProveedorView(it) }
    }

    fun productBodyValidation(ctx: Context): ProductRegisterMapper {
        val newProduct = ctx.bodyValidator<ProductRegisterMapper>()
            .check(
                { it.idProveedor != null && it.itemName != null && it.description != null && it.images != null && it.stock != null && it.vendidos != null && it.itemPrice != null && it.promotionalPrice != null && it.longitud != null && it.ancho != null && it.alto != null && it.pesoKg != null },
                "Invalid body : idProveedor, itemName, description, images, stock, itemPrice and promotionalPrice are required"
            )
            .get()
        return newProduct
    }

    fun companyBodyValidation(ctx: Context): CompanyRegisterMapper {
        val newCompany = ctx.bodyValidator<CompanyRegisterMapper>()
            .check(
                { it.companyName != null && it.companyImage != null && it.facebook != null && it.instagram != null && it.web != null },
                "Invalid body : companyName, companyImage, facebook, instagram and web are required"
            )
            .get()
        return newCompany
    }


//  SIGO TENIENDO UN TEMITA CON ESTA FUNC PORQUE UN PROVEEDOR QUE TIENE PRODUCTOS TIENE MAPEAR ESOS PRODUCTOS TB
/*    inline fun <reified E:Any, T:Any> mapTo(objeto: T): E{
        val objetoNuevo = E::class.java.getDeclaredConstructor().newInstance()
        val propiedades = objeto::class.java.kotlin.memberProperties.filterIsInstance<KMutableProperty<*>>()
        val propiedadesNuevas = objetoNuevo::class.java.kotlin.memberProperties.filterIsInstance<KMutableProperty<*>>()
        for(propiedadNueva in propiedadesNuevas){
            val propiedadesFiltrada = propiedades.filter { prop -> prop.name == propiedadNueva.name }
            if(propiedadesFiltrada.isNotEmpty()){
                val propiedad = propiedadesFiltrada.first()
                propiedadNueva.setter.call(objetoNuevo, propiedad.getter.call(objeto))
            }
        }
        return objetoNuevo
    }*/

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

