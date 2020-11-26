import ar.edu.unq.API.PaymentMapper
import com.mercadopago.resources.Payment

interface PaymentService {
    fun realizarPago(pago: PaymentMapper): Payment
}
