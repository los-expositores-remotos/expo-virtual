package ar.edu.unq.API.controllers

import ar.edu.unq.API.*
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import ar.edu.unq.modelo.Expo
import ar.edu.unq.modelo.Producto

class ProductController(val backend: Expo) {

    fun deleteProduct(ctx: Context){
        try {
            val id = ctx.pathParam("productId")
            backend.removeProduct(id)
            ctx.status(204)
        } catch (e: ExistsException) {
            throw BadRequestResponse(e.message.toString())
        }
    }
    /*  idProveedor: ObjectId,
        itemName: String,
        description: String,
        images: String,
        stock: Int,
        itemPrice: Int,
        promotionalPrice: Int*/
    fun addProduct(ctx: Context){   //falta validacion para no agregar 2 veces al mismo producto
        try {
            val newProduct = ctx.bodyValidator<ProductRegisterMapper>()
                .check(
                    { it.idProveedor != null && it.itemName != null && it.description != null && it.images != null && it.stock != null && it.itemPrice != null && it.promotionalPrice != null },
                    "Invalid body : idProveedor, itemName, description, images, stock, itemPrice and promotionalPrice are required"
                )
                .get()
            val product = Producto(
                backend.setProductId(), newProduct.idProveedor!!.toInt(), newProduct.itemName!!, newProduct.description!!, newProduct.images!!, newProduct.stock!!, newProduct.itemPrice!!, newProduct.promotionalPrice!!)
            backend.addProduct(product)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: ExistsException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun getProductById(ctx: Context) { //falta validacion de que el id exista
        try {
            val productId: String = ctx.pathParam("productId")
            val product: Producto = this.backend.getProduct(productId)
            ctx.status(200)
            println(product)
            ctx.json( ProductsViewMapper(product.id.toString(),
                product.idProveedor.toString(),
                product.itemName,
                product.description,
                product.images,
                product.stock,
                product.itemPrice,
                product.promotionalPrice) )
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
            backend.updateProductWithId(id, Producto(id.toInt(), newProduct.idProveedor!!.toInt(), newProduct.itemName!!, newProduct.description!!, newProduct.images!!, newProduct.stock!!, newProduct.itemPrice!!, newProduct.promotionalPrice!!)
            )
            val updated = this.backend.getProduct(id)
            ctx.json(ProductsViewMapper(
                updated.id.toString(),
                updated.idProveedor.toString(),
                updated.itemName,
                updated.description,
                updated.images,
                updated.stock,
                updated.itemPrice,
                updated.promotionalPrice))
        } catch (e: NotFoundException) {
            throw NotFoundResponse(e.message.toString())
        }
    }
}