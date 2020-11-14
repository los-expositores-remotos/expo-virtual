package ar.edu.unq.services.runner

import ar.edu.unq.services.runner.TransactionRunner.getTransaction
import ar.edu.unq.services.runner.TransactionRunner.runTrx
import ar.edu.unq.services.runner.exceptions.DataBaseNameNotSettedException
import ar.edu.unq.services.runner.exceptions.NoSessionContextException
import ar.edu.unq.services.runner.exceptions.NoTransactionsException
import org.bson.Document
import org.junit.Test
import java.lang.Exception
import kotlin.test.assertEquals

class TransactionRunnerTest {

    @Test(expected = DataBaseNameNotSettedException::class)
    fun obtenerBaseDeDatosFallaCuandoAunNoSeSeteoElNombre() {
        TransactionType.MONGO.getTransaction().dataBase
    }

    @Test(expected = NoTransactionsException::class)
    fun obtenerTransaccionActualFallaCuandoNoHayTransacciones() {
        getTransaction()
    }

    @Test(expected = NoSessionContextException::class)
    fun obtenerSesionActualDeMongoFallaCuandoNoHaySesionEnContexto() {
        TransactionType.MONGO.getTransaction().currentSession
    }

    @Test(expected = NoSessionContextException::class)
    fun obtenerSesionFactoryProviderFallaCuandoNoHaySesionEnContexto() {
        TransactionType.MONGO.getTransaction().sessionFactoryProvider
    }

    @Test
    fun alObtenerLaBaseDeDatosMeConectoConLaBaseDelTipoQueElegi() {
        var dataBaseName = runTrx({ getTransaction().dataBase.name }, listOf(TransactionType.MONGO), DataBaseType.TEST)
        assertEquals(DataBaseType.TEST.databasename, dataBaseName)
        dataBaseName = runTrx({ getTransaction().dataBase.name }, listOf(TransactionType.MONGO), DataBaseType.PRODUCCION)
        assertEquals(DataBaseType.PRODUCCION.databasename, dataBaseName)
    }

    @Test
    fun testSiDuranteUnaTransaccionAgregoUnDocumentoYEnElProcesoSeProduceUnErrorLosCambiosNoSeConfirman() {
        runTrx({
            val dataBase = getTransaction().dataBase
            if(dataBase.listCollectionNames().toList().contains("Transacciones")){
                dataBase.getCollection("Transacciones").drop()
            }
            dataBase.createCollection("Transacciones")
        }, listOf(TransactionType.MONGO), DataBaseType.TEST)
        try {
            runTrx({
                val dataBase = getTransaction().dataBase
                dataBase.getCollection("Transacciones").insertOne(getTransaction().currentSession, Document.parse("{\"name\": \"elpepe\"}"))
                throw Exception("ExcepcionDuranteTransaccion")
            }, listOf(TransactionType.MONGO), DataBaseType.TEST)
        }catch(e: Exception){
            val result = runTrx({
                val dataBase = getTransaction().dataBase
                dataBase.getCollection("Transacciones").find(getTransaction().currentSession).toList()
            }, listOf(TransactionType.MONGO), DataBaseType.TEST)
            assertEquals(emptyList(), result)
            assertEquals("ExcepcionDuranteTransaccion" ,e.message)
        }
        runTrx({
            val dataBase = getTransaction().dataBase
            dataBase.getCollection("Transacciones").drop()
        }, listOf(TransactionType.MONGO), DataBaseType.TEST)
    }

}