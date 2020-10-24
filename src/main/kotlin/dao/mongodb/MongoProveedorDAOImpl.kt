package dao.mongodb

import com.mongodb.DBCursor
import com.mongodb.client.FindIterable
import dao.ProveedorDAO
import modelo.Producto
import modelo.Proveedor
import org.jongo.MongoCursor
import java.util.*

class MongoProveedorDAOImpl : ProveedorDAO, GenericMongoDAO<Proveedor>(Proveedor::class.java) {

}