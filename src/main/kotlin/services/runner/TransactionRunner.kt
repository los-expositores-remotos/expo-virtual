package services.runner

import com.mongodb.client.ClientSession

interface Transaction {
    fun start()
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

    override fun start() {
        sessionFactoryProvider = MongoSessionFactoryProvider.instance
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

    fun sessionFactoryProvider(): MongoSessionFactoryProvider?{
        return sessionFactoryProvider
    }

    fun currentSession(): ClientSession?{
        return session
    }

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

    fun getTransaction(): MongoDBTransaction? {
        return transactions.get(0)
    }


    fun <T> runTrx(bloque: ()->T, types: List<TransactionType> = listOf()): T {
        transactions = types.map { it.getTransaction() }
        try{
            transactions.forEach { it.start() }
            val result = bloque()
            transactions.forEach { it.commit() }
            return result
        } catch (exception:Throwable){
            transactions.forEach { it.rollback() }
            throw exception
        }
    }
}