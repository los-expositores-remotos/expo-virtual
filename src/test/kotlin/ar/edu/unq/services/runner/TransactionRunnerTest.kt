package ar.edu.unq.services.runner

import ar.edu.unq.services.runner.TransactionRunner.getTransaction
import ar.edu.unq.services.runner.TransactionRunner.runTrx
import ar.edu.unq.services.runner.exceptions.NoSessionContextException
import ar.edu.unq.services.runner.exceptions.NoTransactionsException
import org.bson.Document
import org.junit.Test
import java.lang.Exception
import kotlin.test.assertEquals

class TransactionRunnerTest {


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
    fun alObtenerSessionFactoryProviderMeConectoConLaBaseDelTipoQueElegi() {
        var dataBaseName = runTrx({ TransactionType.MONGO.getTransaction().sessionFactoryProvider.getDatabase().name }, listOf(TransactionType.MONGO), DataBaseType.TEST)
        assertEquals(DataBaseType.TEST.databasename, dataBaseName)
        dataBaseName = runTrx({ TransactionType.MONGO.getTransaction().sessionFactoryProvider.getDatabase().name }, listOf(TransactionType.MONGO), DataBaseType.PRODUCCION)
        assertEquals(DataBaseType.PRODUCCION.databasename, dataBaseName)
    }

    @Test
    fun testSiDuranteUnaTransaccionAgregoUnDocumentoYEnElProcesoSeProduceUnErrorLosCambiosNoSeConfirman() {
        try {
            runTrx({
                val dataBase = getTransaction().sessionFactoryProvider.getDatabase()
                if(dataBase.listCollectionNames().contains("Transacciones")){
                    dataBase.getCollection("Transacciones").drop()
                }
                dataBase.createCollection("Transacciones")
            }, listOf(TransactionType.MONGO), DataBaseType.TEST)
            runTrx({
                val dataBase = getTransaction().sessionFactoryProvider.getDatabase()
                dataBase.getCollection("Transacciones").insertOne(Document.parse("{name: elpepe}"))
                throw Exception("ExcepcionDuranteTransaccion")
            }, listOf(TransactionType.MONGO), DataBaseType.TEST)
        }catch(e: Exception){
            val result = runTrx({
                val dataBase = getTransaction().sessionFactoryProvider.getDatabase()
                dataBase.getCollection("Transacciones").find().toList()
            }, listOf(TransactionType.MONGO), DataBaseType.TEST)
            assertEquals(emptyList(), result)
        }
    }

}