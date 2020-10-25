package ar.edu.unq.dao.mongodb

import ar.edu.unq.dao.ProveedorDAO
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import com.mongodb.BasicDBObject
import org.bson.Document
import org.bson.types.ObjectId

class MongoProveedorDAOImpl : ProveedorDAO, GenericMongoDAO<Proveedor>(Proveedor::class.java) {
//    override fun getBsonDocumentFrom(obj: Proveedor): Document {
//        val basicDBObject = BasicDBObject()
//        basicDBObject["id"] = obj.id
//        basicDBObject["companyName"] = obj.companyName
//        basicDBObject["companyImage"] = obj.companyImage
//        basicDBObject["facebook"] = obj.facebook
//        basicDBObject["instagram"] = obj.instagram
//        basicDBObject["web"] = obj.web
//        basicDBObject["productos"] = this.getBsonProductList(obj.productos)
//        return Document.parse(basicDBObject.toJson())
//    }
//
//    private fun getBsonProductList(productos: List<Producto>): List<BasicDBObject>{
//        val basicDBObjectList = emptyList<BasicDBObject>().toMutableList()
//        for(producto in productos){
//            basicDBObjectList.add(this.getBsonProduct(producto))
//        }
//        return basicDBObjectList
//    }
//
//    private fun getBsonProduct(producto: Producto): BasicDBObject{
//        val basicDBObject = BasicDBObject()
//        basicDBObject["id"] = producto.id
//        basicDBObject["itemName"] = producto.itemName
//        basicDBObject["description"] = producto.description
//        basicDBObject["image"] = producto.image
//        basicDBObject["stock"] = producto.stock
//        basicDBObject["itemPrice"] = producto.itemPrice
//        basicDBObject["promotionalPrice"] = producto.promotionalPrice
//        return basicDBObject
//    }
//
//    override fun getProveedorFromDocument(document: Document): Proveedor {
//        val proveedor = Proveedor(document["companyName"].toString())
//        proveedor.id = ObjectId(document["id"].toString())
//        proveedor.companyImage = document["companyImage"].toString()
//        proveedor.facebook = document["facebook"].toString()
//        proveedor.instagram = document["instagram"].toString()
//        proveedor.web = document["web"].toString()
//        proveedor.productos = this.getProductsFromDocuments(document["productos"])
//        return proveedor
//    }
//
//
}