package ar.edu.unq.API.controllers

import ar.edu.unq.API.*
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.ProductoService
import ar.edu.unq.services.ProveedorService

class ProductController(val backendProveedorService: ProveedorService, val backendProductoService: ProductoService) {

    /*
    fun nuevoProducto(producto: Producto)
    fun obtenerProducto(proveedorId: String, nombreItem: String): Producto
    fun actualizarProducto(producto: Producto)
    */

    fun deleteProduct(ctx: Context){
/*        try {
            val id = ctx.pathParam("productId")
            backendProductoService.removeProduct(id.toInt())
            ctx.status(204)
        } catch (e: ExistsException) {
            throw BadRequestResponse(e.message.toString())
        }*/
    }

    fun addProduct(ctx: Context){   //falta validacion para no agregar 2 veces al mismo producto
/*

        try {
            val newProduct = ctx.bodyValidator<ProductRegisterMapper>()
                .check(
                    { it.idProveedor != null && it.itemName != null && it.description != null && it.images != null && it.stock != null && it.itemPrice != null && it.promotionalPrice != null },
                    "Invalid body : idProveedor, itemName, description, images, stock, itemPrice and promotionalPrice are required"
                )
                .get()
            val product = Producto(
                , newProduct.itemName!!, newProduct.description!!, newProduct.images!!, newProduct.stock!!, newProduct.itemPrice!!, newProduct.promotionalPrice!!)
            backend.addProduct(product)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: ExistsException) {
            throw BadRequestResponse(e.message.toString())
        }

 */
    }


    fun getProductById(ctx: Context) {                              //falta validacion de que el id exista
/*
        try {
            val productId: String = ctx.pathParam("productId")
            val product: Producto = this.backend.getProduct(productId)
            ctx.status(200)
            println(product)
            ctx.json( ProductsViewMapper(product.id.toString(),
                product.idProveedor.toString(),
                product.itemName,
                product.description,
                product.listImages,
                product.stock,
                product.itemPrice,
                product.promotionalPrice) )
        } catch (e: NotFoundException) {
            throw NotFoundResponse(e.message.toString())
        }
        */
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
            val producto = this.searchProductById(id)

            producto.itemName = newProduct.itemName!!
            producto.description = newProduct.description!!
            producto.listImages = newProduct.images!!.toMutableList()
            producto.stock = newProduct.stock!!
            producto.itemPrice = newProduct.itemPrice!!
            producto.promotionalPrice = newProduct.promotionalPrice!!

            backendProductoService.actualizarProducto(producto)

            val updated = this.backendProductoService.obtenerProducto(id, newProduct.idProveedor!!)
            ctx.json(ProductsViewMapper(
                updated.id.toString(),
                updated.idProveedor.toString(),
                updated.itemName,
                updated.description,
                updated.listImages,
                updated.stock,
                updated.itemPrice,
                updated.promotionalPrice))
        } catch (e: NotFoundException) {
            throw NotFoundResponse(e.message.toString())
        }
    }

    fun allProducts(ctx: Context) {

        val productsLists = backendProductoService.recuperarATodosLosProductos()
        var allP = productsLists.map{ ProductsViewMapper(it.id.toString(),
            it.idProveedor.toString(),
            it.itemName,
            it.description,
            it.listImages,
            it.stock,
            it.itemPrice,
            it.promotionalPrice) }
        ctx.status(200)
        ctx.json(allP)
    }

    fun getProductsBySuppId(ctx: Context) {
        try {
            val supplierId: String = ctx.pathParam("supplierId")
            val supplier: Proveedor = this.searchContentById(supplierId) as Proveedor
            val products = supplier.productos.map{ ProductsViewMapper(it.id.toString(),
                it.idProveedor.toString(),
                it.itemName,
                it.description,
                it.listImages,
                it.stock,
                it.itemPrice,
                it.promotionalPrice) }
            ctx.status(200)
            ctx.json(products)
        } catch (e: NotFoundException) {
            throw NotFoundResponse(e.message.toString())
        }
    }

    fun searchContentById(supplierId: String?): Proveedor {
        return backendProveedorService.recuperarProveedor(supplierId!!) ?: throw NotFoundException("Supplier", "id", supplierId!!)
    }

    fun searchProductById(productId: String?): Producto {
        return backendProductoService.recuperarATodosLosProductos().find { it.id.toString() == productId } ?: throw NotFoundException("Supplier", "id", productId!!)
    }
}