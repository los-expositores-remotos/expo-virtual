package ar.edu.unq.modelo

import ar.edu.unq.API.PaymentMapper
import com.mercadopago.MercadoPago
import com.mercadopago.exceptions.MPConfException
import com.mercadopago.exceptions.MPException
import com.mercadopago.resources.Payment
import com.mercadopago.resources.datastructures.payment.Payer

/*
package ar.edu.unq.modelo

import com.mercadopago.MercadoPago
import com.mercadopago.exceptions.MPException
import com.mercadopago.resources.Payment
import com.mercadopago.resources.datastructures.payment.Identification
import com.mercadopago.resources.datastructures.payment.Payer

class Payment {
    fun instanceSDK() {
        MercadoPago.SDK.setAccessToken("TEST-7449182497630729-111823-239354f2a2c6af76c94c4f937d954c26-58849892")
        val payment = Payment()
        payment.setTransactionAmount(java.lang.Float.valueOf(request.getParameter("transactionAmount")))
            .setToken(request.getParameter("token"))
            .setDescription(request.getParameter("description"))
            .setInstallments(Integer.valueOf(request.getParameter("installments"))).paymentMethodId =
            request.getParameter("paymentMethodId")
        val identification = Identification()
        identification.setType(request.getParameter("docType")).number = request.getParameter("docNumber")
        val payer = Payer()
        payer.setEmail(request.getParameter("email")).identification = identification
        payment.payer = payer
        try {
            payment.save()
        } catch (e: MPException) {
            e.printStackTrace()
        }
        println(payment.status)
    }
}*/

object Payment {
    @Throws(MPException::class, MPConfException::class)
    @JvmStatic
    fun main(pago: PaymentMapper) {
        MercadoPago.SDK.setAccessToken("TEST-7449182497630729-111823-239354f2a2c6af76c94c4f937d954c26-58849892")
        val payment = Payment()
                .setTransactionAmount(pago.amount)
                .setToken(pago.token)
                .setDescription(pago.description)
                .setInstallments(pago.installments)
                .setPaymentMethodId(pago.paymentMethodId)
                .setPayer(Payer()
                        .setEmail(pago.email))
        val response = payment.save()
        println(response.feeDetails)
    }
}