package ar.edu.unq.API.controllers

import ar.edu.unq.API.*
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.ProductoService
import ar.edu.unq.services.ProveedorService
import ar.edu.unq.services.impl.exceptions.ProductoInexistenteException
import ar.edu.unq.services.impl.exceptions.ProveedorInexistenteException
import org.bson.types.ObjectId

class ProductController(val backendProveedorService: ProveedorService, val backendProductoService: ProductoService) {

    val aux: AuxiliaryFunctions = AuxiliaryFunctions(backendProveedorService, backendProductoService)

    fun deleteProduct(ctx: Context){
        try {
            val id = ctx.pathParam("productId")
            backendProductoService.borrarProducto(id)
            ctx.status(204)
        } catch (e: ProductoInexistenteException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun addProduct(ctx: Context){
        try {
            val newProduct = aux.productBodyvalidation(ctx)
            val product = Producto(ObjectId(newProduct.idProveedor), newProduct.itemName!!, newProduct.description!!, newProduct.stock!!, newProduct.itemPrice!!, newProduct.promotionalPrice!!)
            product.addImage(newProduct.images!!)
            backendProductoService.nuevoProducto(product)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: ProveedorInexistenteException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun getProductById(ctx: Context) {   //falta validation de que el id exista PRODUCTO X ID DE PRODUCTO
        try {
            val productId: String = ctx.pathParam("productId")
            val product: Producto = backendProductoService.recuperarProducto(productId)
            ctx.status(200)
            ctx.json(aux.productoClassToProductoView(product))
        } catch (e: ProductoInexistenteException) {
            throw NotFoundResponse(e.message.toString())
        }
    }

    fun modifyProduct(ctx: Context) {
        try {
            val id = ctx.pathParam("productId")
            val newProduct = aux.productBodyvalidation(ctx)
            val producto = backendProductoService.recuperarProducto(id)

            producto.itemName = newProduct.itemName!!
            producto.description = newProduct.description!!
            producto.listImages = newProduct.images!!.toMutableList()
            producto.stock = newProduct.stock!!
            producto.itemPrice = newProduct.itemPrice!!
            producto.promotionalPrice = newProduct.promotionalPrice!!

            backendProductoService.actualizarProducto(producto)

            val updated = this.backendProductoService.recuperarProducto(id)
            ctx.json(aux.productoClassToProductoView(updated))
        } catch (e: ProductoInexistenteException) {
            throw NotFoundResponse(e.message.toString())
        }
    }

    fun allProducts(ctx: Context) {
        val productsLists = backendProductoService.recuperarATodosLosProductos()
        val allP = aux.productoClassListToProductoViewList(productsLists as MutableCollection<Producto>)
        ctx.status(200)
        ctx.json(allP)
    }

    fun getProductsBySuppId(ctx: Context) {
        try {
            val supplierId: String = ctx.pathParam("supplierId")
            val supplier: Proveedor = backendProveedorService.recuperarProveedor(supplierId)//aux.searchProveedorById(supplierId)
            val products = aux.productoClassListToProductoViewList(supplier.productos)
            ctx.status(200)
            ctx.json(products)
        } catch (e: ProveedorInexistenteException) {
            throw NotFoundResponse(e.message.toString())
        }
    }

    fun searchProduct(ctx: Context){/*
        val productToSearch = ctx.queryParam("text")
        if(productToSearch!!.isBlank()){
            throw BadRequestResponse("Invalid query - param text is empty")
        }
        val productsResult= backendProductoService.buscarProducto(productToSearch)
        val allP = aux.productoClassListToProductoViewList(productsResult as MutableCollection<Producto>)
        ctx.status(200)
        ctx.json(
                mapOf(
                        "Notes" to allP
                )
        )*/
    }
}