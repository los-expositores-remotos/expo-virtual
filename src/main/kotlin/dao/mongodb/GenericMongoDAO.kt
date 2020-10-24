package dao.mongodb

import com.mongodb.MongoCommandException
import com.mongodb.WriteConcern
import com.mongodb.client.ClientSession
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import modelo.Proveedor
import org.bson.conversions.Bson
import org.eclipse.jetty.io.ssl.ALPNProcessor
import services.runner.TransactionRunner

open class GenericMongoDAO<T>(entityType: Class<T>) {

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
            items.add(item)
        }
        return items
    }

    fun getCollection(objectType: String, entityType: Class<T>): MongoCollection<T>?{
        // Precondición: Debe haber una sesión en el contexto
        val database = TransactionRunner.getTransaction()?.sessionFactoryProvider()?.getDatabase()
        try {
            database?.createCollection(objectType)
        }catch (exception: MongoCommandException){

        }
        return database?.getCollection(objectType, entityType)
    }

    fun save(anObject: T) {
        save(listOf(anObject))
    }

    fun update(anObject: T, id: String?) {
        val session:ClientSession = this.session_check()
        this.getCollection(entityType.simpleName, entityType)!!.replaceOne(session, eq("id", id), anObject)
    }

    fun save(objects: List<T>) {
        val session:ClientSession = this.session_check()
        this.getCollection(entityType.simpleName, entityType)!!.insertMany(session, objects)
    }

    operator fun get(id: String?): T? {
        return getBy("id", id)
    }

    fun getBy(property:String, value: String?): T? {
        val session:ClientSession = this.session_check()
        return this.getCollection(entityType.simpleName, entityType)!!.find(session, eq(property, value)).first()
    }

    fun <E> findEq(field:String, value:E ): List<T> {
        return find(eq(field, value))
    }

    fun find(filter:Bson): List<T> {
        val session:ClientSession = this.session_check()
        return this.getCollection(entityType.simpleName, entityType)!!.find(session, filter).into(mutableListOf())
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