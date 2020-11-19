package ar.edu.unq.dao.mongodb

import ar.edu.unq.dao.ProductoDAO
import ar.edu.unq.modelo.Producto
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Aggregates.*
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import io.javalin.core.util.RouteOverviewUtil.metaInfo
import org.bson.Document
import org.bson.types.ObjectId
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class MongoProductoDAOImpl : ProductoDAO, GenericMongoDAO<Producto>(Producto::class.java) {

    override fun save(objects: List<Producto>) {
        throw Exception("Este metodo no esta permitido")
    }

    override fun update(anObject: Producto, id: String?) {
        throw Exception("Este metodo no esta permitido")
    }

    override fun deleteAll() {
        throw Exception("Este metodo no esta permitido")
    }

    override fun <E> deleteBy(property: String, value: E?) {
        throw Exception("Este metodo no esta permitido")
    }

    override fun get(idProveedor: String, nombreProducto: String): Producto? {
        return find(
                and(
                        eq("idProveedor", ObjectId(idProveedor)),
                        eq("itemName",nombreProducto)
                )
        ).firstOrNull()
    }

    override fun createColection(objectType: String, database: MongoDatabase) {
        this.borrarViewSiCambiaronAtributos(database)
        if(database.listCollectionNames().contains("Producto").not()) {
            super.createColection("Proveedor", database)
            val proyectarProductos = project(Document("listProducts", "\$productos"))
            val separarProductos = unwind("\$listProducts")
            val proyectarProductosIndividuales = project(this.estructuraDeProducto())
            database.createView("Producto", "Proveedor", listOf(
                    proyectarProductos,
                    separarProductos,
                    proyectarProductosIndividuales
            )
            )
        }
    }

    private fun borrarViewSiCambiaronAtributos(database: MongoDatabase){
        if(this.propiedadesViewProducto(database) != this.propiedadesProducto().map { it.name }.toSet()){
            database.getCollection("Producto").drop()
        }
    }

    private fun estructuraDeProducto(): Document{
        return Document(mutableMapOf<String,String>().plus(this.propiedadesProducto().map { Pair(it.name, "\$listProducts.${it.name}") }))
    }

    private fun propiedadesProducto(): List<KMutableProperty<*>> {
        return Producto::class.java.kotlin.memberProperties.filterIsInstance<KMutableProperty<*>>()
    }

    private fun propiedadesViewProducto(database: MongoDatabase): Set<String>?{
        return (database.listCollections().toList().find { it["name"] == "Producto" }?.get("options", Document::class.java)?.get("pipeline", List::class.java)?.lastOrNull() as Document?)?.get("\$project", Document::class.java)?.keys
    }
}