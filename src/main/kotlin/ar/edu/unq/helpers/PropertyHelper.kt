package ar.edu.unq.helpers

import ar.edu.unq.modelo.Producto
import com.mongodb.client.MongoDatabase
import org.bson.Document
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties

object PropertyHelper {
    inline fun <reified T: Any> publicProperties(): List<KProperty<*>> {
        return T::class.java.kotlin.memberProperties.filter { it.visibility == KVisibility.PUBLIC }
    }
}