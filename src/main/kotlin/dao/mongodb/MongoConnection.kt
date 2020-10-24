package dao.mongodb

import com.mongodb.*
import com.mongodb.client.*
import com.mongodb.client.MongoClient
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider


/**
 * As per documentation:
 *
 * "The MongoClient instance actually represents a pool of connections to the database;
 * you will only need one instance of class MongoClient even with multiple threads."
 *
 * This singleton ensures that only one instance of MongoClient ever exists
 */
class MongoConnection {
    var client: MongoClient
    var dataBase: MongoDatabase
    var session:ClientSession? = null

    fun startTransaction() {
        try {
            session = client.startSession()
            session?.startTransaction(TransactionOptions.builder().writeConcern(WriteConcern.MAJORITY).build())
        }catch (exception: MongoClientException){
            exception.printStackTrace()
        }
    }

    fun commitTransaction() {
        session?.commitTransaction()
        closeSession()
    }

    fun rollbackTransaction() {
        session?.abortTransaction()
        closeSession()
    }

    protected fun closeSession(){
        session?.close()
        session = null
    }

    fun <T> getCollection(name:String, entityType: Class<T> ): MongoCollection<T> {
        try{
            dataBase.createCollection(name)
        } catch (exception: MongoCommandException){
            println("Ya existe la coleccion $name")
        }
        return dataBase.getCollection(name, entityType)
    }

    init {
        val codecRegistry: CodecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
        )
        val uri = System.getenv().getOrDefault("MONGO_URI", "mongodb+srv://Gustavo:99z2CEj2xWnR4Ntw@cluster0.agdwn.mongodb.net/pruebasback?retryWrites=true&w=majority")
        val connectionString = ConnectionString(uri)
        val settings = MongoClientSettings.builder()
            .codecRegistry(codecRegistry)
            .applyConnectionString(connectionString)
            .build()
        client = MongoClients.create(settings)
        dataBase = client.getDatabase("pruebasback")
    }
}