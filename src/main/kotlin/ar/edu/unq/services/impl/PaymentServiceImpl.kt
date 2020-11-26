package ar.edu.unq.services.impl

import ar.edu.unq.services.PaymentService
import ar.edu.unq.API.PaymentMapper
import ar.edu.unq.modelo.Payment

class PaymentServiceImpl: PaymentService {
    override fun realizarPago(pago: PaymentMapper): com.mercadopago.resources.Payment {
        return Payment.main(pago)
    }
}