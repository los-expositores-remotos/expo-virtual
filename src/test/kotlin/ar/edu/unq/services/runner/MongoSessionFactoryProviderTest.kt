package ar.edu.unq.services.runner

import ar.edu.unq.services.runner.exceptions.DataBaseNameNotSettedException
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class MongoSessionFactoryProviderTest {
    @Before
    fun setUp(){

    }

    @Test
    fun testSePuedeObtenerUnaBaseDeDatosSabiendoSuNombre(){
        val dataBaseName = "ElPepeDataBase"
        assertEquals(dataBaseName, MongoSessionFactoryProvider.instance.getDatabase(dataBaseName).name)
        MongoSessionFactoryProvider.instance.getDatabase(dataBaseName).drop()
    }

    @Test
    fun siElSessionFactoryProviderNoEsDestruidoReutilizaLaInstancia() {
        val sessionFactoryProvider = MongoSessionFactoryProvider.instance
        assertEquals(sessionFactoryProvider, MongoSessionFactoryProvider.instance)
    }

    @Test
    fun testAlDestruirElSessionFactoryProviderSeDestruyeTambienLaSession() {
        val sessionFactoryProvider = MongoSessionFactoryProvider.instance
        sessionFactoryProvider.createSession()
        assertNotNull(sessionFactoryProvider.session)
        MongoSessionFactoryProvider.destroy()
        assertNull(sessionFactoryProvider.session)
    }

    @Test
    fun testAlPedirUnaInstanciaLuegoDeDestruirElSessionFactoryProviderSeGeneraUnaNueva() {
        val sessionFactoryProvider = MongoSessionFactoryProvider.instance
        MongoSessionFactoryProvider.destroy()
        assertNotEquals(sessionFactoryProvider, MongoSessionFactoryProvider.instance)
    }

    @Test
    fun testEsPosibleDestruirElSessionFactoryProviderCuandoYaFueDestruido() {
        val sessionFactoryProvider = MongoSessionFactoryProvider.instance
        MongoSessionFactoryProvider.destroy()
        MongoSessionFactoryProvider.destroy()
        assertNotEquals(sessionFactoryProvider, MongoSessionFactoryProvider.instance)
    }

}