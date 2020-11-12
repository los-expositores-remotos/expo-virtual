package ar.edu.unq.services.runner

import ar.edu.unq.services.runner.exceptions.DataBaseNameNotSettedException
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class MongoSessionFactoryProviderTest {
    @Before
    fun setUp(){

    }

    @Test(expected = DataBaseNameNotSettedException::class)
    fun testAlPedirInstanciaSinSetearPreviamenteLaBaseDeDatosLanzaUnaExcepcion() {
        MongoSessionFactoryProvider.destroy()
        MongoSessionFactoryProvider.instance
    }

    @Test
    fun testPedirUnaInstanciaConUnaBaseDeDatosYaSeteada() {
        MongoSessionFactoryProvider.destroy()
        MongoSessionFactoryProvider.dataBaseName = "PepitoDataBase"
        val sessionFactoryProvider = MongoSessionFactoryProvider.instance
        assertEquals("PepitoDataBase" ,sessionFactoryProvider.getDatabase().name)
    }

    @Test
    fun testAlSetearElDataBaseNameSeteoLaBaseDeDatosALaQueMeConecto() {
        MongoSessionFactoryProvider.dataBaseName = "PepitoDataBase"
        var sessionFactoryProvider = MongoSessionFactoryProvider.instance
        assertEquals("PepitoDataBase" ,sessionFactoryProvider.getDatabase().name)
        MongoSessionFactoryProvider.dataBaseName = "ElPepeDataBase"
        sessionFactoryProvider = MongoSessionFactoryProvider.instance
        assertEquals("ElPepeDataBase" ,sessionFactoryProvider.getDatabase().name)
    }

    @Test
    fun testAlDestruirElSessionFactoryProviderSeDestruyeTambienLaSession() {
        MongoSessionFactoryProvider.dataBaseName = "PepitoDataBase"
        val sessionFactoryProvider = MongoSessionFactoryProvider.instance
        sessionFactoryProvider.createSession()
        assertNotNull(sessionFactoryProvider.session)
        MongoSessionFactoryProvider.destroy()
        assertNull(sessionFactoryProvider.session)
    }

    @After
    fun deleteAll() {
        MongoSessionFactoryProvider.dataBaseName = "PepitoDataBase"
        var sessionFactoryProvider = MongoSessionFactoryProvider.instance
        sessionFactoryProvider.getDatabase().drop()
        MongoSessionFactoryProvider.dataBaseName = "ElPepeDataBase"
        sessionFactoryProvider = MongoSessionFactoryProvider.instance
        sessionFactoryProvider.getDatabase().drop()
    }
}