package ar.edu.unq.dao.mongodb

import com.mongodb.MongoCommandException
import com.mongodb.client.ClientSession
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import org.bson.conversions.Bson
import ar.edu.unq.services.runner.TransactionRunner
import com.mongodb.BasicDBObject
import org.bson.BSONObject
import org.bson.Document

abstract class GenericMongoDAO<T>(entityType: Class<T>) {

    val entityType: Class<T> = entityType

    fun deleteAll() {
        this.session_check()
        this.getCollection(entityType.simpleName)!!.drop()
    }

    fun getAll(): List<T> {
        this.session_check()
        val items: java.util.ArrayList<T> = java.util.ArrayList<T>()
        val cursor: com.mongodb.client.MongoCursor<Document> = this.getCollection(entityType.simpleName)!!.find().cursor()
        for(document: Document in cursor){
            val item: T = this.mapFromDocument(document)
            items.add(item)
        }
        return items
    }

     private fun getCollection(objectType: String): MongoCollection<Document>?{
        // Precondición: Debe haber una sesión en el contexto
        val database = TransactionRunner.getTransaction()?.sessionFactoryProvider()?.getDatabase()
        try {
            database?.createCollection(objectType)
        }catch (exception: MongoCommandException){

        }
        return database?.getCollection(objectType)
    }

    fun save(anObject: T) {
        save(listOf(anObject))
    }

    fun update(anObject: T, id: String?) {
        val session:ClientSession = this.session_check()
        this.getCollection(entityType.simpleName)!!.replaceOne(session, eq("id", id), this.mapToDocument(anObject))
    }

    fun save(objects: List<T>) {
        val session:ClientSession = this.session_check()
        this.getCollection(entityType.simpleName)!!.insertMany(mapAllToDocument(objects))
    }

    fun delete(id: String){// TODO: testear
        this.getCollection(entityType.simpleName)!!.deleteOne(eq("id", id))
    }

    fun deleteBy(property:String, value: String?){// TODO: testear
        this.getCollection(entityType.simpleName)!!.deleteOne(eq(property, value))
    }

    operator fun get(id: String?): T? {
        return getBy("id", id)
    }

    fun getBy(property:String, value: String?): T? {
        val result: T?
        val session:ClientSession = this.session_check()
        val document = this.getCollection(entityType.simpleName)!!.find(session, eq(property, value)).first()
        if(document != null) {
            result = this.mapFromDocument(document)
        }else {
            result = null
        }
        return result
    }

    fun <E> findEq(field:String, value:E ): List<T> {
        return find(eq(field, value))
    }

    fun find(filter:Bson): List<T> {
        //val session:ClientSession =
        this.session_check()
        return this.mapAllFromDocuments(this.getCollection(entityType.simpleName)!!.find(filter).toMutableList()) //into(mutableListOf())session,
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

    fun mapAllToDocument(objects: List<T>): List<Document> {
        val documents = emptyList<Document>().toMutableList()
        for(obj in objects){
            documents.add(this.mapToDocument(obj))
        }
        return documents
    }

    abstract fun mapToDocument(obj: T): Document

    fun mapAllFromDocuments(documents: List<Document>): List<T> {
        val items = emptyList<T>().toMutableList()
        for(document in documents){
            items.add(this.mapFromDocument(document))
        }
        return items
    }

    abstract fun mapFromDocument(document: Document): T

}