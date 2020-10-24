package services.runner

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.ClientSession
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider

class MongoSessionFactoryProvider {
    private var client : MongoClient
    private var dataBase : MongoDatabase
    private val session : ClientSession? = null

    init {
        val codecRegistry: CodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
        )
        val uri = System.getenv().getOrDefault("MONGO_URI", "mongodb+srv://Gustavo:99z2CEj2xWnR4Ntw@cluster0.agdwn.mongodb.net/pruebasback?retryWrites=true&w=majority")
        val connectionString = ConnectionString(uri)
        var settings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry)
                .applyConnectionString(connectionString)
                .build()
        client = MongoClients.create(settings)
        dataBase = client.getDatabase("pruebasback")
    }

    fun getDatabase(): MongoDatabase{
        return dataBase
    }

    fun createSession(): ClientSession {
        return this.client.startSession()
    }

    companion object {

        private var INSTANCE: MongoSessionFactoryProvider? = null

        val instance: MongoSessionFactoryProvider
            get() {
                if (INSTANCE == null) {
                    INSTANCE = MongoSessionFactoryProvider()
                }
                return INSTANCE!!
            }

        fun destroy() {
            if (INSTANCE != null && INSTANCE!!.session != null) {
                INSTANCE!!.client.close()
            }
            INSTANCE = null
        }
    }
}