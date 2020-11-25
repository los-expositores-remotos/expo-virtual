package ar.edu.unq.services.impl

import PaymentService
import ar.edu.unq.API.PaymentMapper
import ar.edu.unq.modelo.Payment

class PaymentServiceImpl: PaymentService {
    override fun realizarPago(pago: PaymentMapper): String {
        return Payment.main(pago)
    }

}