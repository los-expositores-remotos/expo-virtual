package ar.edu.unq.dao.mongodb

import ar.edu.unq.dao.ProductoDAO
import ar.edu.unq.modelo.Producto

class MongoProductoDAOImpl : ProductoDAO, GenericMongoDAO<Producto>(Producto::class.java)  {

    override fun traerLoQueFalte(item: Producto) {

    }

    override fun guardarLoQueFalte(anObject: Producto) {

    }

}