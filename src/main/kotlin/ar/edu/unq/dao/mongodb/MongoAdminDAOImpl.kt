package ar.edu.unq.dao.mongodb

import ar.edu.unq.dao.AdminDAO

import ar.edu.unq.modelo.Admin


class MongoAdminDAOImpl : AdminDAO, GenericMongoDAO<Admin>(Admin::class.java)

