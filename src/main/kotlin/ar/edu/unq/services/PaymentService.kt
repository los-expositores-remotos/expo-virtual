import ar.edu.unq.API.PaymentMapper

/*
package ar.edu.unq.services

interface PaymentService {

    procesarPago(request: Request)
}*/
interface PaymentService {
    fun realizarPago(pago: PaymentMapper): String
}
