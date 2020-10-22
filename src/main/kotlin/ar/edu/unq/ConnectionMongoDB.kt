package ar.edu.unq

import com.mongodb.*
import com.mongodb.client.*
import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters.eq
import org.w3c.dom.Document

class MongoConnection {
    val mongoClient: MongoClient = MongoClients.create(
        "mongodb+srv://Gustavo:99z2CEj2xWnR4Ntw@cluster0.agdwn.mongodb.net/feriavirtualdb?retryWrites=true&w=majority"
    )
    val database: MongoDatabase = mongoClient.getDatabase("feriavirtualdb")

    // SE CREA UNA COLECCION
    //database.createCollection("users2")

    // SE ACCEDE A LA COLLECCION
    //var tbl = database.getCollection("users2")

    // SE GENERA UN DOCUMENTO PARA GUARDARSE EN LA COLLECCION
    //val document = org.bson.Document()
    //document.put("name","Carlos2")
    //document.put("lastname","Perez")
    //document.put("years",28)

    // SE INSERTA EL DOCUMENTO A LA COLECCION
    //tbl.insertOne(document)

    fun <T> getCollection(name: String, entityType: Class<T>): MongoCollection<T>? {
        var result: MongoCollection<T>? = null
        try {
            result = this.database.getCollection(name, entityType)
        } catch (exception: MongoCommandException) {
            println("No se pudo recuperar la coleccion $name")
        }
        return result
    }

    fun createCollection(name: String) {
        try {
            database.createCollection(name)
        } catch (exception: MongoCommandException) {
            println("No se pudo crear la coleccion $name")
        }
    }

    fun deleteCollection(collection: String) {
        try {
            database.getCollection(collection).drop()
        } catch (exception: MongoCommandException) {
            println("No se pudo eliminar la coleccion $collection")
        }
    }

    fun countItemsFromCollection(collection: String): Int {
        var result: Int = 0
        try {
            result = database.getCollection(collection).count().toInt()
        } catch (exception: MongoCommandException) {
            println("No se puedo obtener el resultado en la coleccion $collection ")
        }
        return result
    }


    fun <T, E> findEq(field: String, value: E, collection: String): FindIterable<org.bson.Document>? {
        var result: FindIterable<org.bson.Document>? = null
        try {
            result = database.getCollection(collection).find(eq(field, value))
        } catch (exception: MongoCommandException) {
            println("No se puedo obtener el resultado en la coleccion $collection ")
        }
        return result
    }

    fun insertItem(entity: org.bson.Document, collection: String) {
        try {
            database.getCollection(collection).insertOne(entity)
        } catch (exception: MongoCommandException) {
            println("No se puedo agregar el item a la coleccion $collection ")
        }
    }

    fun getAllItems(collection: String): FindIterable<org.bson.Document>? {
        var result: FindIterable<org.bson.Document>? = null
        try {
            result = database.getCollection(collection).find()
        } catch (exception: MongoCommandException) {
            println("No se puedo obtener los items de la coleccion $collection ")
        }
        return result
    }

    fun insertManyItems(collection: String, items: List<org.bson.Document>) {
        try {
            database.getCollection(collection).insertMany(items)
        } catch (exception: MongoCommandException) {
            println("No se puedieron guardar los items en la coleccion $collection ")
        }
    }
}
