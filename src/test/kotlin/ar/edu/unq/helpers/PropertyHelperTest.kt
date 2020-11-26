package ar.edu.unq.helpers

import org.junit.Test
import kotlin.test.assertEquals

class PropertyHelperTest {
    private class MultiVisibilityPropertiesClass{
        val property1: String = ""
        val property2: String = ""
        private val property3: String = ""
        private val property4: String = ""
    }

    @Test
    fun testObtenerPropiedadesPublicas() {
        assertEquals(setOf("property1", "property2"), PropertyHelper.publicProperties<MultiVisibilityPropertiesClass>().map { it.name }.toSet())
    }
}