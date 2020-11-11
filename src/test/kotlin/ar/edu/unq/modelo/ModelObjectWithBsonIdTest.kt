package ar.edu.unq.modelo

import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

abstract class ModelObjectWithBsonIdTest<T: ModelObjectWithBsonId>(private val entityType: Class<T>) {
    private var t1: T? = null
    private var t2: T? = null

    @Before
    open fun setUp(){
        this.t1 = entityType.getDeclaredConstructor().newInstance()
        this.t2 = entityType.getDeclaredConstructor().newInstance()
    }

    @Test
    fun testLosObjetosSonIgualesASiMismos(){
        assertEquals(this.t1, this.t1)
    }

    @Test
    fun testUnObjetoEsIgualAOtroConSuMismoId(){
        this.t2!!.id = this.t1!!.id
        assertEquals(this.t1, this.t2)
    }

    @Test
    fun testUnObjetoEsDistintoAUnObjetoDeOtraClase(){
        val objeto = emptyList<String>()
        assertFalse(this.t1 == objeto)
    }

    @Test
    fun testUnObjetoEsDistintoDeOtroConDiferenteId(){
        val t = entityType.getDeclaredConstructor().newInstance()
        assertNotEquals(this.t1!!.id, t.id)
        assertNotEquals(this.t1, t)
    }

    @Test
    fun testElHashCodeDeUnObjetoEsIgualAlDeOtroConElMismoId(){
        this.t2!!.id = this.t1!!.id
        assertEquals(this.t1.hashCode(), this.t2.hashCode())
    }

    @Test
    fun testElHashCodeDeUnObjetoEsDistintoAlDeOtroConDiferenteId(){
        val t = entityType.getDeclaredConstructor().newInstance()
        assertNotEquals(this.t1!!.id, t.id)
        assertNotEquals(this.t1.hashCode(), t.hashCode())
    }
}