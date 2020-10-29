package ar.edu.unq.dao.mongodb

import ar.edu.unq.dao.ProductoDAO
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner
import ar.edu.unq.services.runner.TransactionType
import org.bson.Document
import org.bson.types.ObjectId

class MongoProductoDAOImpl : ProductoDAO, GenericMongoDAO<Producto>(Producto::class.java) {

    override fun mapToDocument(obj: Producto): Document {
        val document = Document()
        document["id"] = obj.id.toString()
        document["idProveedor"] = obj.idProveedor.toString()
        document["itemName"] = obj.itemName
        document["description"] = obj.description
        document["listImages"] = obj.listImages
        document["stock"] = obj.stock.toString()
        document["itemPrice"] = obj.itemPrice.toString()
        document["promotionalPrice"] = obj.promotionalPrice.toString()
        return document
    }



    override fun mapFromDocument(document: Document): Producto {
        val producto = Producto()
        producto.id = ObjectId(document["id"].toString())
        producto.idProveedor = ObjectId(document["idProveedor"].toString())
        producto.itemName = document["itemName"].toString()
        producto.description = document["description"].toString()
        producto.listImages = document["listImages"] as MutableList<String>
        producto.stock = document["stock"].toString().toInt()
        producto.itemPrice = document["itemPrice"].toString().toInt()
        producto.promotionalPrice = document["promotionalPrice"].toString().toInt()
        return producto
    }

    override fun get(idProveedor: String, nombreProducto: String): Producto? {
        return this.findEq("idProveedor", idProveedor).filter { it.itemName == nombreProducto }.first()
    }

    override fun saveOrUpdate(productos: List<Producto>, dataBaseType: DataBaseType) {
        for(producto in productos){
            try {
                TransactionRunner.runTrx({ this.save(producto) }, listOf(TransactionType.MONGO), dataBaseType)
            }catch (exception: Throwable){
                TransactionRunner.runTrx({ this.update(producto, producto.id.toString()) }, listOf(TransactionType.MONGO), dataBaseType)
            }
        }
    }
}