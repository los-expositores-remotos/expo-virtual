package ar.edu.unq.services.runner.sessionfactoryprovider

class MongoSessionFactoryProviderProduccion : MongoSessionFactoryProvider() {
    override fun dataBaseName(): String {
        return "produccionback"
    }

    companion object {

        private var INSTANCE: MongoSessionFactoryProvider? = null

        val instance: MongoSessionFactoryProvider
            get() {
                if (INSTANCE == null) {
                    INSTANCE = MongoSessionFactoryProviderProduccion()
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
