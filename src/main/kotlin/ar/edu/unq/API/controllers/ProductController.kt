package ar.edu.unq.API.controllers

import ar.edu.unq.API.*
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.ProductoService
import ar.edu.unq.services.ProveedorService
import org.bson.types.ObjectId

class ProductController(val backendProveedorService: ProveedorService, val backendProductoService: ProductoService) {

    val aux: AuxiliaryFunctions = AuxiliaryFunctions(backendProveedorService, backendProductoService)

    fun deleteProduct(ctx: Context){
        try {
            val id = ctx.pathParam("productId")
            backendProductoService.borrarProducto(id)
            ctx.status(204)
        } catch (e: ExistsException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun addProduct(ctx: Context){
        try {
            val newProduct = ctx.bodyValidator<ProductRegisterMapper>()
                .check(
                    { it.idProveedor != null && it.itemName != null && it.description != null && it.images != null && it.stock != null && it.itemPrice != null && it.promotionalPrice != null },
                    "Invalid body : idProveedor, itemName, description, images, stock, itemPrice and promotionalPrice are required"
                )
                .get()
            val product = Producto(ObjectId(newProduct.idProveedor), newProduct.itemName!!, newProduct.description!!, newProduct.stock!!, newProduct.itemPrice!!, newProduct.promotionalPrice!!)
            product.addImage(newProduct.images!!)
            backendProductoService.nuevoProducto(product)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: ExistsException) {
            throw BadRequestResponse(e.message.toString())
        }
    }


    fun getProductById(ctx: Context) {   //falta validacion de que el id exista PRODUCTO X ID DE PRODUCTO
        try {
            val productId: String = ctx.pathParam("productId")
            val product: Producto = backendProductoService.recuperarProducto(productId)
            ctx.status(200)
            println(product.id.toString())
            ctx.json(aux.productoClassToProductoView(product))
        } catch (e: NotFoundException) {
            throw NotFoundResponse(e.message.toString())
        }
    }

    fun modifyProduct(ctx: Context) {
        try {
            val id = ctx.pathParam("productId")
            val newProduct = ctx.bodyValidator<ProductRegisterMapper>()
                .check(
                    { it.idProveedor != null && it.itemName != null && it.description != null && it.images != null && it.stock != null && it.itemPrice != null && it.promotionalPrice != null },
                    "Invalid body : idProveedor, itemName, description, images, stock, itemPrice and promotionalPrice are required"
                )
                .get()
            val producto = aux.searchProductoById(id)

            producto.itemName = newProduct.itemName!!
            producto.description = newProduct.description!!
            producto.listImages = newProduct.images!!.toMutableList()
            producto.stock = newProduct.stock!!
            producto.itemPrice = newProduct.itemPrice!!
            producto.promotionalPrice = newProduct.promotionalPrice!!

            backendProductoService.actualizarProducto(producto)
            println(producto)
            println(newProduct.idProveedor)
            val updated = this.backendProductoService.recuperarProducto(id)
            ctx.json(aux.productoClassToProductoView(updated))
        } catch (e: NotFoundException) {
            throw NotFoundResponse(e.message.toString())
        }
    }

    fun allProducts(ctx: Context) {
        val productsLists = backendProductoService.recuperarATodosLosProductos()
        var allP = aux.productoClassListToProductoViewList(productsLists as MutableCollection<Producto>)
        ctx.status(200)
        ctx.json(allP)
    }

    fun getProductsBySuppId(ctx: Context) {
        try {
            val supplierId: String = ctx.pathParam("supplierId")
            val supplier: Proveedor = aux.searchProveedorById(supplierId) as Proveedor
            println(supplier)
            val products = aux.productoClassListToProductoViewList(supplier.productos)
            ctx.status(200)
            ctx.json(products)
        } catch (e: NotFoundException) {
            throw NotFoundResponse(e.message.toString())
        }
    }
}