package ar.edu.unq.helpers

import kotlin.reflect.KProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties

object PropertyHelper {
    inline fun <reified T: Any> publicProperties(): List<KProperty<*>> {
        return T::class.java.kotlin.memberProperties.filter { it.visibility == KVisibility.PUBLIC }
    }
}