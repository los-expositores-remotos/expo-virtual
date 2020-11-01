package ar.edu.unq.dao.mongodb

import ar.edu.unq.dao.ProveedorDAO
import ar.edu.unq.modelo.Proveedor

class MongoProveedorDAOImpl : ProveedorDAO, GenericMongoDAO<Proveedor>(Proveedor::class.java)