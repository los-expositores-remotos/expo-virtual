package ar.edu.unq.modelo

import ar.edu.unq.modelo.banner.BannerCategory
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BannerCategoryTest {
    @Test
    fun testEsUnStringValido(){
        BannerCategory.values().forEach { assertTrue(BannerCategory.isValid(it.toString())) }
        assertFalse(BannerCategory.isValid("SARASA"))
    }
}