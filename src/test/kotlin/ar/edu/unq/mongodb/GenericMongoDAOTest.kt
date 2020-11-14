package ar.edu.unq.mongodb

import ar.edu.unq.dao.mongodb.GenericMongoDAO
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner
import ar.edu.unq.services.runner.TransactionRunner.runTrx
import ar.edu.unq.services.runner.TransactionType
import org.bson.types.ObjectId
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotEquals

abstract class GenericMongoDAOTest<T>(protected val dao: GenericMongoDAO<T>) {

    var items: MutableList<T> = emptyList<T>().toMutableList()

    @Before
    fun setUp() {
        this.generarItems()
    }

    abstract fun generarItems()

    open fun obtenerTodos(): List<T>{
        return this.dao.getAll()
    }

    @Test
    fun testGettingItems() {
        var result = runTrx({ this.dao.getAll() }, listOf(TransactionType.MONGO), DataBaseType.TEST)
        Assert.assertEquals(this.items.toHashSet(), result.toHashSet())
        runTrx({ this.borrarNItems(1) }, listOf(TransactionType.MONGO), DataBaseType.TEST)
        result = runTrx({ this.dao.getAll() }, listOf(TransactionType.MONGO), DataBaseType.TEST)
        Assert.assertEquals(this.items.toHashSet(), result.toHashSet())
    }

    @Test
    fun testSiNoEstaCreadaLaColeccionSeCrea(){
        runTrx({
             this.deleteColection()
        }, listOf(TransactionType.MONGO), DataBaseType.TEST)
        val result = runTrx({ this.dao.getAll() }, listOf(TransactionType.MONGO), DataBaseType.TEST)
        Assert.assertEquals(0, result.count())
    }

    @Test
    abstract fun encontrarItemsQueCumplenPropiedad()

    open fun deleteColection(){
        val database = TransactionRunner.getTransaction().dataBase
        database.getCollection(this.dao.entityType.simpleName).drop()
    }

    abstract fun borrarNItems(n: Int)

    @After
    open fun deleteAll(){
        runTrx({ this.dao.deleteAll() }, listOf(TransactionType.MONGO), DataBaseType.TEST)
    }
}