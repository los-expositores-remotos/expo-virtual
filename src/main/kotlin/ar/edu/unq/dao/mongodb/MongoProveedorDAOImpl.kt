package ar.edu.unq.dao.mongodb

import ar.edu.unq.dao.ProveedorDAO
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner
import ar.edu.unq.services.runner.TransactionType
import com.mongodb.BasicDBObject
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import org.bson.Document
import org.bson.conversions.Bson
import org.bson.types.ObjectId

class MongoProveedorDAOImpl : ProveedorDAO, GenericMongoDAO<Proveedor>(Proveedor::class.java) {

}