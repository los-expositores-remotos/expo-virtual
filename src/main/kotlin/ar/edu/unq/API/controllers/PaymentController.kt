package ar.edu.unq.API.controllers

import ar.edu.unq.services.PaymentService
import ar.edu.unq.API.*
import ar.edu.unq.services.impl.PaymentServiceImpl
import com.mercadopago.resources.Payment
import io.javalin.http.Context

class PaymentController {
    private val backendPaymentService: PaymentService = PaymentServiceImpl()

    private fun getResultPayMessage(payment: Payment):ResultTransactionMapper{
        val statusList: List<ResultTransactionMapper> = listOf(
                ResultTransactionMapper("accredited","¡Listo! Se acreditó tu pago. En tu resumen verás el cargo de "+payment.transactionAmount+" como "+payment.statementDescriptor+"."),
                ResultTransactionMapper("pending_contingency","Estamos procesando tu pago.  No te preocupes, menos de 2 días hábiles te avisaremos por e-mail si se acreditó."),
                ResultTransactionMapper("pending_review_manual","Estamos procesando tu pago.  No te preocupes, menos de 2 días hábiles te avisaremos por e-mail si se acreditó o si necesitamos más información."),
                ResultTransactionMapper("cc_rejected_bad_filled_card_number","Revisa el número de tarjeta."),
                ResultTransactionMapper("cc_rejected_bad_filled_date","Revisa la fecha de vencimiento."),
                ResultTransactionMapper("cc_rejected_bad_filled_other","Revisa los datos."),
                ResultTransactionMapper("cc_rejected_bad_filled_security_code","Revisa el código de seguridad de la tarjeta."),
                ResultTransactionMapper("cc_rejected_blacklist","No pudimos procesar tu pago."),
                ResultTransactionMapper("cc_rejected_call_for_authorize","Debes autorizar ante "+payment.paymentMethodId+" el pago de "+payment.transactionAmount+"."),
                ResultTransactionMapper("cc_rejected_card_disabled","Llama a "+payment.paymentMethodId+" para activar tu tarjeta o usa otro medio de pago.  El teléfono está al dorso de tu tarjeta."),
                ResultTransactionMapper("cc_rejected_card_error","No pudimos procesar tu pago."),
                ResultTransactionMapper("cc_rejected_duplicated_payment","Ya hiciste un pago por ese valor.  Si necesitas volver a pagar usa otra tarjeta u otro medio de pago."),
                ResultTransactionMapper("cc_rejected_high_risk","Tu pago fue rechazado.  Elige otro de los medios de pago, te recomendamos con medios en efectivo."),
                ResultTransactionMapper("cc_rejected_insufficient_amount","Tu "+payment.paymentMethodId+" no tiene fondos suficientes."),
                ResultTransactionMapper("cc_rejected_invalid_installments",""+payment.paymentMethodId+" no procesa pagos en installments cuotas."),
                ResultTransactionMapper("cc_rejected_max_attempts","Llegaste al límite de intentos permitidos.  Elige otra tarjeta u otro medio de pago."),
                ResultTransactionMapper("cc_rejected_other_reason",""+payment.paymentMethodId+" no procesó el pago")
        )
        return statusList.find { it.status_detail == payment.statusDetail.toString() }!!
    }

    fun processPayment(ctx: Context){
        try {
            val payment = this.backendPaymentService.realizarPago(ctx.body<PaymentMapper>())
            val resultTrMsg = this.getResultPayMessage(payment)
            ctx.status(200)
            ctx.json(resultTrMsg)
        } catch (e: NullPointerException) {
            ctx.status(404)
            ctx.json(ErrorViewMapper(e.message.toString(), "Error en los datos ingresados"))
        }
    }
}