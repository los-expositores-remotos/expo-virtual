package dao.mongodb

import com.mongodb.DBCursor
import com.mongodb.client.FindIterable
import dao.ProveedorDAO
import modelo.Producto
import modelo.Proveedor
import org.jongo.MongoCursor
import java.util.*

class MongoProveedorDAOImpl : ProveedorDAO, GenericMongoDAO<Proveedor>(Proveedor::class.java) {

    override fun getAll(): List<Proveedor> {
        val proveedores: ArrayList<Proveedor> = ArrayList<Proveedor>()
        val cursor: com.mongodb.client.MongoCursor<Proveedor> = collection.find().cursor()
        for(proveedor: Proveedor in cursor){
            println(proveedor)
            proveedores.add(proveedor)
        }
        return proveedores
    }
}