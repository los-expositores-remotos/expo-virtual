package ar.edu.unq.API.controllers

import PaymentService
import ar.edu.unq.API.CompanyRegisterMapper
import ar.edu.unq.API.ErrorViewMapper
import ar.edu.unq.API.PaymentMapper
import ar.edu.unq.API.ResultTransaction
import ar.edu.unq.services.impl.PaymentServiceImpl
import ar.edu.unq.services.impl.exceptions.BannerExistenteException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context

class PaymentController {

    private val backendPaymentService: PaymentService = PaymentServiceImpl()
    var msg: String = ""
    fun processPayment(ctx: Context){
        try {
        println(ctx.body())
        msg = this.backendPaymentService.realizarPago(ctx.body<PaymentMapper>())
        ctx.status(200)
        ctx.json(ResultTransaction(msg))
        //this.backendPaymentService.realizarPago()
        } catch (e: NullPointerException) {
            ctx.status(404)
            ctx.json(ErrorViewMapper(e.message.toString(), msg))
        }
    }
}