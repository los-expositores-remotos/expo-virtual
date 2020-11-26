package ar.edu.unq.services.runner

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.ClientSession
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider

class MongoSessionFactoryProvider() {
    private var client : MongoClient
    var session : ClientSession? = null

    init {
        val codecRegistry: CodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
        )
        val uri = System.getenv().getOrDefault("MONGO_URI", "mongodb+srv://Gustavo:99z2CEj2xWnR4Ntw@cluster0.agdwn.mongodb.net/")
        val connectionString = ConnectionString(uri)
        val settings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry)
                .applyConnectionString(connectionString)
                .build()
        client = MongoClients.create(settings)
    }

    companion object {

        private var INSTANCE: MongoSessionFactoryProvider? = null
        val instance: MongoSessionFactoryProvider
            get() {
                INSTANCE = INSTANCE ?: MongoSessionFactoryProvider()
                return  INSTANCE!!
            }

        fun destroy() {
            if (INSTANCE != null && INSTANCE!!.session != null) {
                INSTANCE!!.client.close()
                INSTANCE!!.session = null
            }
            INSTANCE = null
        }
    }

    fun getDatabase(databasename: String): MongoDatabase{
        return this.client.getDatabase(databasename)
    }

    fun createSession(): ClientSession {
        val session = this.client.startSession()
        this.session = session
        return session
    }
}