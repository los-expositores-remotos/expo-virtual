package ar.edu.unq.services.runner

import com.mongodb.client.ClientSession

interface Transaction {
    fun start(dataBaseType: DataBaseType)
    fun commit()
    fun rollback()
}

class MongoDBTransaction: Transaction {
    companion object {
        private var session: ClientSession? = null
        private var staticSessionFactoryProvider: MongoSessionFactoryProvider? = null
    }

    val currentSession: ClientSession
        get() {
            return session ?: throw Exception("No hay una sesión en el contexto")
        }
    val sessionFactoryProvider: MongoSessionFactoryProvider
        get() {
            return staticSessionFactoryProvider ?: throw Exception("No hay una sesión en el contexto")
        }

    override fun start(dataBaseType: DataBaseType) {
        staticSessionFactoryProvider = dataBaseType.getSessionFactoryProvider()
        session = staticSessionFactoryProvider!!.createSession()
        session?.startTransaction()
    }

    override fun commit() {
        session?.commitTransaction()
        session?.close()
        session = null
    }

    override fun rollback() {
        session?.abortTransaction()
        session?.close()
        session = null
    }
}

enum class DataBaseType {
    TEST {
        override fun getSessionFactoryProvider(): MongoSessionFactoryProvider {
            MongoSessionFactoryProvider.dataBaseName = "pruebasbackadrian"
            return MongoSessionFactoryProvider.instance
        }
    },
    PRODUCCION {
        override fun getSessionFactoryProvider(): MongoSessionFactoryProvider {
            MongoSessionFactoryProvider.dataBaseName = "produccionback"
            return MongoSessionFactoryProvider.instance
        }
    };
    abstract fun getSessionFactoryProvider(): MongoSessionFactoryProvider
}

enum class TransactionType {
    MONGO {
        override fun getTransaction(): MongoDBTransaction {
            return MongoDBTransaction()
        }
    };
    abstract fun getTransaction(): MongoDBTransaction
}

object TransactionRunner {
    private var transactions:List<MongoDBTransaction> = listOf()

    fun getTransaction(): MongoDBTransaction {
        // Precondición: Debe haber una transacción en curso
        if(transactions.isNotEmpty()){
            return transactions[0]
        }else{
            throw Exception("Debe haber al menos una transaccion en curso")
        }
    }


    fun <T> runTrx(bloque: ()->T, types: List<TransactionType> = listOf(), dataBaseType: DataBaseType): T {
        transactions = types.map { it.getTransaction() }
        try{
            transactions.forEach { it.start(dataBaseType) }
            val result = bloque()
            transactions.forEach { it.commit() }
            return result
        } catch (exception:Throwable){
            transactions.forEach { it.rollback() }
            throw exception
        }
    }
}