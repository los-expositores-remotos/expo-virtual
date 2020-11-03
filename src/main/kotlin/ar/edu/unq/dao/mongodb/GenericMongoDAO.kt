package ar.edu.unq.dao.mongodb

import com.mongodb.client.ClientSession
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import org.bson.conversions.Bson
import ar.edu.unq.services.runner.TransactionRunner
import org.bson.Document
import org.bson.types.ObjectId

abstract class GenericMongoDAO<T>(val entityType: Class<T>) {
    open fun deleteAll() {
        this.session_check()
        this.getCollection(entityType.simpleName, entityType)!!.deleteMany(Document())
    }

    fun getAll(): List<T> {
        this.session_check()
        return this.getCollection(entityType.simpleName, entityType)!!.find().toMutableList()
    }

     protected open fun getCollection(objectType: String, classType: Class<T>): MongoCollection<T>?{
        // Precondición: Debe haber una sesión en el contexto
        val database = TransactionRunner.getTransaction()?.sessionFactoryProvider()?.getDatabase()
        if(database?.listCollectionNames()!!.contains(objectType).not()) {
            database.createCollection(objectType)
        }
        return database.getCollection(objectType, classType)
    }

    open fun save(anObject: T) {
        save(listOf(anObject))
    }

    open fun update(anObject: T, id: String?) {
        this.session_check()
        this.getCollection(entityType.simpleName, entityType)!!.replaceOne(eq("id", ObjectId(id)), anObject)
    }

    open fun save(objects: List<T>) {
        this.session_check()
        this.getCollection(entityType.simpleName, entityType)!!.insertMany(objects)
    }

    open fun delete(id: String){// TODO: testear
        this.getCollection(entityType.simpleName, entityType)!!.deleteOne(eq("id", ObjectId(id)))
    }

    open fun deleteBy(property:String, value: String?){// TODO: testear
        this.getCollection(entityType.simpleName, entityType)!!.deleteOne(eq(property, value))
    }

    operator fun get(id: String?): T? {
        return getBy("id", ObjectId(id))
    }

    fun <E> getBy(property:String, value: E?): T? {
        this.session_check()
        return this.getCollection(entityType.simpleName, entityType)!!.find(Document(property,value)).first()
    }

    fun <E> findEq(field:String, value:E ): List<T> {
        return find(eq(field, value))
    }

    fun find(filter:Bson): List<T> {
        this.session_check()
        return this.getCollection(entityType.simpleName, entityType)!!.find(filter).toMutableList()
    }

//    fun <T> aggregate(pipeline:List<Bson> , resultClass:Class<T>): List<T> {
//        val session:ClientSession = this.session_check()
//        //if(session != null) {
//        return this.getCollection(entityType.simpleName, entityType)!!.aggregate(session, pipeline, resultClass).into(ArrayList())
//        //}
//        //return this.getCollection(entityType.simpleName, entityType)!!.aggregate(pipeline, resultClass).into(ArrayList())
//    }

    fun session_check(): ClientSession{
        val session: ClientSession? = TransactionRunner.getTransaction()!!.currentSession()
        return session ?: throw Exception("No hay una sesión en el contexto")
    }
}