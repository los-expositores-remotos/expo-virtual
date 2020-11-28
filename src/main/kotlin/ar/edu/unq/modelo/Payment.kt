package ar.edu.unq.modelo

import ar.edu.unq.API.PaymentMapper
import com.mercadopago.MercadoPago
import com.mercadopago.exceptions.MPConfException
import com.mercadopago.exceptions.MPException
import com.mercadopago.resources.Payment
import com.mercadopago.resources.datastructures.payment.Identification
import com.mercadopago.resources.datastructures.payment.Payer
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties

object Payment {

    @Throws(MPException::class, MPConfException::class)
    @JvmStatic
    fun main(pago: PaymentMapper): Payment {
        MercadoPago.SDK.setAccessToken("TEST-7484070905477197-112319-c2b1400369673f7d22fc5f32bdd028a3-674632494")

        val payment = Payment()
        payment.setTransactionAmount(pago.amount)
            .setToken(pago.token)
            .setDescription(pago.description)
            .setIssuerId(pago.issuerId)
            .setInstallments(pago.installments).paymentMethodId = pago.paymentMethodId

        val identification = Identification()
        identification.setType(pago.docType).number = pago.docNumber

        val payer = Payer()
        payer.setEmail(pago.email).identification = identification
        payment.payer = payer

        return payment.save()
    }
}