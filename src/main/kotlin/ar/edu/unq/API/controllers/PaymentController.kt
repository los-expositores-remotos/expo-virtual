package ar.edu.unq.API.controllers

import io.javalin.http.Context

class PaymentController {

    fun processPayment(ctx: Context){
        println(ctx.body())
    }
}