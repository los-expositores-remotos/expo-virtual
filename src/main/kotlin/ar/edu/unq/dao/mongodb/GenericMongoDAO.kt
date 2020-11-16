package ar.edu.unq.dao.mongodb

import com.mongodb.client.ClientSession
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import org.bson.conversions.Bson
import ar.edu.unq.services.runner.TransactionRunner
import com.mongodb.client.MongoDatabase
import org.bson.Document
import org.bson.types.ObjectId

abstract class GenericMongoDAO<T>(val entityType: Class<T>) {
    open fun deleteAll() {
        val session = this.sessionCheck()
        this.getCollection(entityType.simpleName, entityType).deleteMany(session, Document())
    }

    fun getAll(): List<T> {
        val session = this.sessionCheck()
        return this.getCollection(entityType.simpleName, entityType).find().toMutableList()
    }

     protected open fun getCollection(objectType: String, classType: Class<T>): MongoCollection<T>{
        // Precondición: Debe haber una sesión en el contexto
        val database = this.getDatabase()
        this.createColection(objectType,database)
        return database.getCollection(objectType, classType)
    }

    protected fun createColection(objectType: String, database: MongoDatabase){
        if(database.listCollectionNames().contains(objectType).not()) {
            database.createCollection(objectType)
        }
    }

    open fun save(anObject: T) {
        save(listOf(anObject))
    }

    open fun update(anObject: T, id: String?) {
        val session = this.sessionCheck()
        this.getCollection(entityType.simpleName, entityType).replaceOne(session, eq("id", ObjectId(id)), anObject)
    }

    open fun save(objects: List<T>) {
        val session = this.sessionCheck()
        this.getCollection(entityType.simpleName, entityType).insertMany(session, objects)
    }

    open fun delete(id: String){
        this.deleteBy("id", ObjectId(id))
    }

    open fun <E> deleteBy(property:String, value: E?){
        val session = this.sessionCheck()
        this.getCollection(entityType.simpleName, entityType).deleteOne(session, eq(property, value))
    }

    operator fun get(id: String?): T? {
        return getBy("id", ObjectId(id))
    }

    fun <E> getBy(property:String, value: E?): T? {
        val session = this.sessionCheck()
        return this.getCollection(entityType.simpleName, entityType).find(Document(property,value)).first()
    }

    fun <E> findEq(field:String, value:E ): List<T> {
        return find(eq(field, value))
    }

    fun find(filter:Bson): List<T> {
        val session = this.sessionCheck()
        return this.getCollection(entityType.simpleName, entityType).find(filter).toMutableList()
    }

//    fun <T> aggregate(pipeline:List<Bson> , resultClass:Class<T>): List<T> {
//        val session:ClientSession = this.session_check()
//        //if(session != null) {
//        return this.getCollection(entityType.simpleName, entityType)!!.aggregate(session, pipeline, resultClass).into(ArrayList())
//        //}
//        //return this.getCollection(entityType.simpleName, entityType)!!.aggregate(pipeline, resultClass).into(ArrayList())
//    }

    protected fun getDatabase(): MongoDatabase{
        return TransactionRunner.getTransaction().dataBase
    }

    private fun sessionCheck(): ClientSession{
        return TransactionRunner.getTransaction().currentSession
    }
}