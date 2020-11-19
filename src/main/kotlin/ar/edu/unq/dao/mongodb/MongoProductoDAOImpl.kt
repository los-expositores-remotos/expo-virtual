package ar.edu.unq.dao.mongodb

import ar.edu.unq.dao.ProductoDAO
import ar.edu.unq.modelo.Producto
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Aggregates.*
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import org.bson.Document
import org.bson.types.ObjectId

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

    override fun getCollection(objectType: String, classType: Class<Producto>): MongoCollection<Producto> {
        val database = this.getDatabase()
        if(database.listCollectionNames().contains("Producto").not()) {
            this.createColection("Proveedor", database)
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
        return database.getCollection(objectType,classType)
    }

    private fun estructuraDeProducto(): Document {
        val estructuraProducto = Document()
        estructuraProducto["id"] = "\$listProducts.id"
        estructuraProducto["idProveedor"] = "\$listProducts.idProveedor"
        estructuraProducto["itemName"] = "\$listProducts.itemName"
        estructuraProducto["description"] = "\$listProducts.description"
        estructuraProducto["listImages"] = "\$listProducts.listImages"
        estructuraProducto["listTags"] = "\$listProducts.listTags"
        estructuraProducto["stock"] = "\$listProducts.stock"
        estructuraProducto["itemPrice"] = "\$listProducts.itemPrice"
        estructuraProducto["promotionalPrice"] = "\$listProducts.promotionalPrice"
        estructuraProducto["alto"] = "\$listProducts.alto"
        estructuraProducto["ancho"] = "\$listProducts.ancho"
        estructuraProducto["longitud"] = "\$listProducts.longitud"
        estructuraProducto["pesoKg"] = "\$listProducts.pesoKg"
        estructuraProducto["vendidos"] = "\$listProducts.vendidos"
        return estructuraProducto
    }
}