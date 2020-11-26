package ar.edu.unq.helpers

import org.junit.Test
import kotlin.test.assertEquals

class PropertyHelperTest {
    private class MultiVisibilityPropertiesClass

    @Test
    fun testObtenerPropiedadesPublicas() {
        assertEquals(setOf("property1", "property2"), PropertyHelper.publicProperties<MultiVisibilityPropertiesClass>().map { it.name }.toSet())
    }
}