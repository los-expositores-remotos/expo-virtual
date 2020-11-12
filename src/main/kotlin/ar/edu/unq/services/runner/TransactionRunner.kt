package ar.edu.unq.services.runner

import ar.edu.unq.services.runner.exceptions.NoSessionContextException
import ar.edu.unq.services.runner.exceptions.NoTransactionsException
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
            return session ?: throw NoSessionContextException("No hay una sesión en el contexto")
        }
    val sessionFactoryProvider: MongoSessionFactoryProvider
        get() {
            return staticSessionFactoryProvider ?: throw NoSessionContextException("No hay una sesión en el contexto")
        }

    override fun start(dataBaseType: DataBaseType) {
        staticSessionFactoryProvider = dataBaseType.getSessionFactoryProvider()
        session = sessionFactoryProvider.createSession()
        currentSession.startTransaction()
    }

    override fun commit() {
        currentSession.commitTransaction()
        currentSession.close()
        staticSessionFactoryProvider = null
        session = null
    }

    override fun rollback() {
        currentSession.abortTransaction()
        currentSession.close()
        staticSessionFactoryProvider = null
        session = null
    }
}

enum class DataBaseType {
    TEST {
        override val databasename: String
            get() = "pruebasbackadrian"
    },
    PRODUCCION {
        override val databasename: String
            get() = "produccionback"
    };
    fun getSessionFactoryProvider(): MongoSessionFactoryProvider {
        MongoSessionFactoryProvider.dataBaseName = this.databasename
        return MongoSessionFactoryProvider.instance
    }

    abstract val databasename: String
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
            throw NoTransactionsException("Debe haber al menos una transaccion en curso")
        }
    }


    fun <T> runTrx(bloque: ()->T, types: List<TransactionType>, dataBaseType: DataBaseType): T {
        transactions = types.map { it.getTransaction() }
        try{
            transactions.forEach { it.start(dataBaseType) }
            val result = bloque()
            transactions.forEach { it.commit() }
            transactions = emptyList()
            return result
        } catch (exception:Throwable){
            transactions.forEach { it.rollback() }
            transactions = emptyList()
            throw exception
        }
    }
}