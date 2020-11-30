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
import ar.edu.unq.services.impl.exceptions.ProductoSinStockException
import ar.edu.unq.services.impl.exceptions.ProveedorExistenteException
import ar.edu.unq.services.impl.exceptions.ProveedorInexistenteException
import org.bson.types.ObjectId

class ProductController(val backendProveedorService: ProveedorService, val backendProductoService: ProductoService) {

    val aux: AuxiliaryFunctions = AuxiliaryFunctions()

    fun deleteProduct(ctx: Context){
        try {
            val id = ctx.pathParam("productId")
            val idProveedor = backendProductoService.recuperarProducto(id).idProveedor
            backendProductoService.borrarProducto(id)
            val updatedProd = aux.productoClassListToProductoViewList(backendProveedorService.recuperarProveedor(idProveedor.toString()).productos)
            ctx.status(200)
            ctx.json(updatedProd)
        } catch (e: ProductoInexistenteException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun addProduct(ctx: Context){
        try {
            val newProduct = aux.productBodyValidation(ctx)
            val product = Producto(ObjectId(newProduct.idProveedor), newProduct.itemName!!, newProduct.description!!, newProduct.stock!!, newProduct.itemPrice!!, newProduct.promotionalPrice!!, newProduct.longitud!!, newProduct.ancho!!, newProduct.alto!!, newProduct.pesoGr!!)
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
            val newProduct = aux.productBodyValidation(ctx)
            val producto = backendProductoService.recuperarProducto(id)

            producto.itemName = newProduct.itemName!!
            producto.description = newProduct.description!!
            producto.listImages = newProduct.images!!.toMutableList()
            producto.stock = newProduct.stock!!
            producto.vendidos = newProduct.vendidos!!
            producto.itemPrice = newProduct.itemPrice!!
            producto.promotionalPrice = newProduct.promotionalPrice!!
            producto.longitud = newProduct.longitud!!
            producto.ancho = newProduct.ancho!!
            producto.alto = newProduct.alto!!
            producto.pesoGr = newProduct.pesoGr!!

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

    fun searchProducts(ctx: Context){
        val productToSearch = ctx.queryParam("text")
        if(productToSearch!!.isBlank()){
            throw BadRequestResponse("Invalid query - param text is empty")
        }
        val productsResult= backendProductoService.buscarProductos(productToSearch)
        val allP = aux.productoClassListToProductoViewList(productsResult as MutableCollection<Producto>)
        ctx.status(200)
        ctx.json(
                mapOf(
                        "Products" to allP
                )
        )
    }
    fun createMassive(ctx: Context){
        try {
            val newListProducts = ctx.bodyValidator<ProductListRegisterMapper>()
                    .check(
                            { it.products.all  { it.idProveedor != null && it.itemName != null && it.description != null && it.images != null && it.stock != null && it.vendidos != null && it.itemPrice != null && it.promotionalPrice != null && it.longitud != null && it.ancho != null && it.alto != null && it.pesoGr != null }   },
                            "Invalid body : companyName, companyImage, facebook, instagram and web are required"
                    )
                    .get()
            newListProducts.products.forEach {
                val product = Producto(ObjectId(it.idProveedor), it.itemName!!, it.description!!, it.stock!!, it.itemPrice!!, it.promotionalPrice!!, it.longitud!!, it.ancho!!, it.alto!!, it.pesoGr!!)
                product.addImage(it.images!!)
                backendProductoService.nuevoProducto(product)
            }
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: ProveedorExistenteException) {//seria bueno que contemple excepcion por nombre
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun decreaseProduct(ctx: Context){
        try {
            val ventas = ctx.body<SalesMapper>().sales
            val paresVenta = emptyList<Pair<Producto, Int>>().toMutableList()
            val productosSinStock = emptyList<String>().toMutableList()
            var todosTienenStock = true
            for(venta in ventas) {
                val producto = backendProductoService.recuperarProducto(venta.idProducto)
                paresVenta.add(Pair(producto, venta.cantidadVendida))
                val tieneStockSuficiente = (producto.stock >= venta.cantidadVendida)
                if(!tieneStockSuficiente) {
                    todosTienenStock = false
                    productosSinStock.add(producto.itemName)
                }
            }
            if(todosTienenStock) {
                for(par in paresVenta) {
                    par.first.cargarVenta(par.second)
                    backendProductoService.actualizarProducto(par.first)
                }
                ctx.status(201)
                ctx.json(OkResultMapper("ok"))
            } else {
                ctx.status(500)
                ctx.json(OkResultMapper(
                        "Los siguientes productos no tienen el stock requerido: " +
                                productosSinStock.toString()
                )
                )
            }
        } catch(e: ProductoSinStockException) {
            throw NotFoundResponse(e.message.toString())
        }
    }
}