package ar.edu.unq.dao.mongodb

import ar.edu.unq.dao.UsuarioDAO
import ar.edu.unq.modelo.Usuario

class MongoUsuarioDAOImpl : UsuarioDAO, GenericMongoDAO<Usuario>(Usuario::class.java)