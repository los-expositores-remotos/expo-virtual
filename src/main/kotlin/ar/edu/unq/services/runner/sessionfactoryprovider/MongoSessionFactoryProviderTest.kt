package ar.edu.unq.services.runner.sessionfactoryprovider

class MongoSessionFactoryProviderTest  : MongoSessionFactoryProvider() {
    override fun dataBaseName(): String {
        return "pruebasback"
    }

    companion object {

        private var INSTANCE: MongoSessionFactoryProvider? = null

        val instance: MongoSessionFactoryProvider
            get() {
                if (INSTANCE == null) {
                    INSTANCE = MongoSessionFactoryProviderTest()
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
