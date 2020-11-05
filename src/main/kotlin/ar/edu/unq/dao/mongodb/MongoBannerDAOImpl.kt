package ar.edu.unq.dao.mongodb

import ar.edu.unq.dao.BannerDAO
import ar.edu.unq.modelo.banner.Banner

class MongoBannerDAOImpl : BannerDAO, GenericMongoDAO<Banner>(Banner::class.java)
