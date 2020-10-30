package ar.edu.unq.API.controllers

import ar.edu.unq.API.ExistsException
import ar.edu.unq.API.OkResultMapper
import ar.edu.unq.API.ProductRegisterMapper
import ar.edu.unq.API.SupplierRegisterMapper
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import modelo.Company
import modelo.Expo
import modelo.Product

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
            val product = Product(
                backend.setProductId(), newProduct.idProveedor!!.toInt(), newProduct.itemName!!, newProduct.description!!, newProduct.images!!, newProduct.stock!!, newProduct.itemPrice!!, newProduct.promotionalPrice!!)
            backend.addProduct(product)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: ExistsException) {
            throw BadRequestResponse(e.message.toString())
        }
    }
}