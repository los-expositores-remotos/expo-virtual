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

class MongoSessionFactoryProvider(databasename: String) {
    private var client : MongoClient
    private var dataBase : MongoDatabase
    private var session : ClientSession? = null

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
        dataBase = client.getDatabase(databasename)
    }

    companion object {

        private var INSTANCE: MongoSessionFactoryProvider? = null
        var dataBaseName: String? = null
        val instance: MongoSessionFactoryProvider
            get() {
                if (INSTANCE == null) {
                    INSTANCE =
                        MongoSessionFactoryProvider(
                            dataBaseName
                                ?: throw Exception("La base de datos no esta definida")
                        )
                }
                return INSTANCE!!
            }

        fun reset(){
            INSTANCE = null
            dataBaseName = null
        }

        fun destroy() {
            if (INSTANCE != null && INSTANCE!!.session != null) {
                INSTANCE!!.client.close()
            }
            INSTANCE = null
        }
    }

    fun getDatabase(): MongoDatabase{
        return dataBase
    }

    fun createSession(): ClientSession {
        val session = this.client.startSession()
        this.session = session
        return session
    }
}