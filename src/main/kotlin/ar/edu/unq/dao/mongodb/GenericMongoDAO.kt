package ar.edu.unq.dao.mongodb

import com.mongodb.MongoCommandException
import com.mongodb.client.ClientSession
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import org.bson.conversions.Bson
import ar.edu.unq.services.runner.TransactionRunner
import com.mongodb.BasicDBObject
import org.bson.Document

abstract class GenericMongoDAO<T>(entityType: Class<T>) {

    val entityType: Class<T> = entityType


    fun deleteAll() {
        this.session_check()
        this.getCollection(entityType.simpleName, entityType)!!.drop()
    }

     fun getAll(): List<T> {
        this.session_check()
        val items: java.util.ArrayList<T> = java.util.ArrayList<T>()
        val cursor: com.mongodb.client.MongoCursor<T> = this.getCollection(entityType.simpleName, entityType)!!.find().cursor()
        for(item: T in cursor){
            this.traerLoQueFalte(item)
            items.add(item)
        }
        return items
    }

    abstract fun traerLoQueFalte(item: T)

    private fun getCollection(objectType: String, entityType: Class<T>): MongoCollection<T>?{
        // Precondición: Debe haber una sesión en el contexto
        val database = TransactionRunner.getTransaction()?.sessionFactoryProvider()?.getDatabase()
        try {
            database?.createCollection(objectType)
        }catch (exception: MongoCommandException){

        }
        return database?.getCollection(objectType, entityType)
    }

//
//    fun getCollection(objectType: String): MongoCollection<Document>?{
//        // Precondición: Debe haber una sesión en el contexto
//        val database = TransactionRunner.getTransaction()?.sessionFactoryProvider()?.getDatabase()
//        try {
//            database?.createCollection(objectType)
//        }catch (exception: MongoCommandException){
//
//        }
//        return database?.getCollection(objectType)
//    }

    fun save(anObject: T) {
        save(listOf(anObject))
    }

    fun update(anObject: T, id: String?) {
        val session:ClientSession = this.session_check()
        this.guardarLoQueFalte(anObject)
        this.getCollection(entityType.simpleName, entityType)!!.replaceOne(session, eq("id", id), anObject)
    }

    abstract fun guardarLoQueFalte(anObject: T)

    fun save(objects: List<T>) {
        val session:ClientSession = this.session_check()
        for(obj in objects){
            this.guardarLoQueFalte(obj)
        }
        this.getCollection(entityType.simpleName, entityType)!!.insertMany(session, objects)
        //this.getCollection(entityType.simpleName)!!.insertMany(session, getBsonDocumentsFrom(objects))
    }

//    fun getBsonDocumentsFrom(objects: List<T>): List<Document>{
//        val documents = emptyList<Document>().toMutableList()
//        for(obj in objects){
//            documents.add(this.getBsonDocumentFrom(obj))
//        }
//        return documents
//    }


    //abstract fun getBsonDocumentFrom(obj: T): Document

    operator fun get(id: String?): T? {
        return getBy("id", id)
    }

    fun getBy(property:String, value: String?): T? {
        val session:ClientSession = this.session_check()
        val item = this.getCollection(entityType.simpleName, entityType)!!.find(session, eq(property, value)).first()
        this.traerLoQueFalte(item)
        return item
        //this.getProveedorFromDocument(this.getCollection(entityType.simpleName)!!.find(session, eq(property, value)).first())
    }


    fun <E> findEq(field:String, value:E ): List<T> {
        return find(eq(field, value))
    }

    fun find(filter:Bson): List<T> {
        val session:ClientSession = this.session_check()
        val items = this.getCollection(entityType.simpleName, entityType)!!.find(session, filter).into(mutableListOf())
        for(item in items){
            this.traerLoQueFalte(item)
        }
        return items
    }

    fun <T> aggregate(pipeline:List<Bson> , resultClass:Class<T>): List<T> {
        val session:ClientSession = this.session_check()
        //if(session != null) {
        return this.getCollection(entityType.simpleName, entityType)!!.aggregate(session, pipeline, resultClass).into(ArrayList())
        //}
        //return this.getCollection(entityType.simpleName, entityType)!!.aggregate(pipeline, resultClass).into(ArrayList())
    }

    fun session_check(): ClientSession{
        val session: ClientSession? = TransactionRunner.getTransaction()!!.currentSession()
        return session ?: throw Exception("No hay una sesión en el contexto")
    }
}