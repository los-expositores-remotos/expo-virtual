package ar.edu.unq.services

import ar.edu.unq.API.PaymentMapper
import ar.edu.unq.services.impl.PaymentServiceImpl
import com.mercadopago.resources.Payment
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull

class PaymentServiceImplTest {
    private val paymentService: PaymentService = PaymentServiceImpl()
    private lateinit var payToDo: PaymentMapper
    private lateinit var emptyPay: PaymentMapper

    @Before
    fun setUp() {
        payToDo = PaymentMapper("",370f,"someone@gulp.com","unas compras sin sentido",500f,1,"master",12,"wilobank","dni","1234567")
    }

    @Test
    fun `al Enviar Pago Ante Mercado Pago Obtengo Una Respuesta Distinta De Null`(){
        val payment: Payment = paymentService.realizarPago(payToDo)
        assertNotNull(payment)
    }

    @Test(expected = com.mercadopago.exceptions.MPValidationException::class)
    fun `lanza Excepción Al No Enviar Un Pago Con Todos Los Atributos`(){
        emptyPay = PaymentMapper("",0f,"","",0f,0,"",0,"","","")
        val payment: Payment = paymentService.realizarPago(emptyPay)
        assertNotNull(payment)
    }
}