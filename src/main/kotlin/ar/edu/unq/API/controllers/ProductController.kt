package ar.edu.unq.API.controllers

import ar.edu.unq.API.ExistsException
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import modelo.Expo

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
}