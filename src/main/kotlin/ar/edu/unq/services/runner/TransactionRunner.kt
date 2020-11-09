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
        private var sessionFactoryProvider: MongoSessionFactoryProvider? = null
        val currentSession: ClientSession?
            get() {
                if (session == null) {
                    throw RuntimeException("No hay ninguna session en el contexto")
                }
                return session
            }
    }

    override fun start(dataBaseType: DataBaseType) {
        sessionFactoryProvider = dataBaseType.getSessionFactoryProvider()
        session = sessionFactoryProvider!!.createSession()
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

    fun sessionFactoryProvider(): MongoSessionFactoryProvider {
        return sessionFactoryProvider ?: throw Exception("No hay una sesión en el contexto")
    }

    fun currentSession(): ClientSession{
        return session ?: throw Exception("No hay una sesión en el contexto")
    }

}

enum class DataBaseType {
    TEST {
        override fun getSessionFactoryProvider(): MongoSessionFactoryProvider {
            MongoSessionFactoryProvider.dataBaseName = "pruebasMias"
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