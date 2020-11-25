package ar.edu.unq.API.controllers

import PaymentService
import ar.edu.unq.API.CompanyRegisterMapper
import ar.edu.unq.API.PaymentMapper
import ar.edu.unq.API.ResultTransaction
import ar.edu.unq.services.impl.PaymentServiceImpl
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.javalin.http.Context

class PaymentController {

    private val backendPaymentService: PaymentService = PaymentServiceImpl()

    fun processPayment(ctx: Context){
        println(ctx.body())
        val msg = this.backendPaymentService.realizarPago(ctx.body<PaymentMapper>())
        ctx.status(200)
        ctx.json(ResultTransaction(msg))
        //this.backendPaymentService.realizarPago()
    }
}