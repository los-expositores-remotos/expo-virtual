package ar.edu.unq.dao.mongodb

import ar.edu.unq.dao.ProductoDAO
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner
import ar.edu.unq.services.runner.TransactionType
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Aggregates.*
import com.mongodb.client.model.Field
import com.mongodb.client.model.Filters
import org.bson.Document
import org.bson.conversions.Bson
import org.bson.types.ObjectId

class MongoProductoDAOImpl : ProductoDAO, GenericMongoDAO<Producto>(Producto::class.java) {

    override fun save(objects: List<Producto>) {//TODO: tirar error
        throw Exception("Este metodo no esta permitido")
    }

    override fun save(anObject: Producto) {//TODO: tirar error
        throw Exception("Este metodo no esta permitido")
    }

    override fun update(anObject: Producto, id: String?) {//TODO: tirar error
        throw Exception("Este metodo no esta permitido")
    }

    override fun delete(id: String) {//TODO: tirar error
        throw Exception("Este metodo no esta permitido")
    }

    override fun deleteAll() {//TODO: tirar error
        throw Exception("Este metodo no esta permitido")
    }

    override fun deleteBy(property: String, value: String?) {//TODO: tirar error
        throw Exception("Este metodo no esta permitido")
    }

    override fun get(idProveedor: String, nombreProducto: String): Producto? {
        return find(Filters.and(Filters.eq("idProveedor", ObjectId(idProveedor)),Filters.eq("itemName",nombreProducto)))
                .first()
    }

    override fun getCollection(objectType: String, classType: Class<Producto>): MongoCollection<Producto>? {
        val database = TransactionRunner.getTransaction()?.sessionFactoryProvider()?.getDatabase()

        if(database?.listCollectionNames()!!.contains("Producto").not()) {
            val proyectarProductos = Document()
                                .append("\$project", Document()
                                        .append("listProducts", "\$productos"))
            val separarProductos = Document()
                                .append("\$unwind", Document()
                                        .append("path", "\$listProducts"))
            val proyectarProductosIndividuales = Document()
                                .append("\$project", Document()
                                        .append("id", "\$listProducts.id")
                                        .append("idProveedor", "\$listProducts.idProveedor")
                                        .append("itemName", "\$listProducts.itemName")
                                        .append("description", "\$listProducts.description")
                                        .append("listImages", "\$listProducts.listImages")
                                        .append("stock", "\$listProducts.stock")
                                        .append("itemPrice", "\$listProducts.itemPrice")
                                        .append("promotionalPrice", "\$listProducts.promotionalPrice"))
            database.createView("Producto", "Proveedor", listOf(
                    proyectarProductos,
                    separarProductos,
                    proyectarProductosIndividuales
                    )
            )
        }
        return database.getCollection(objectType,classType)
    }
}