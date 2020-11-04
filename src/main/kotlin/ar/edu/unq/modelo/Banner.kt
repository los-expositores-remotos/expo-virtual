package ar.edu.unq.modelo

import ar.edu.unq.API.controllers.BannerController
import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.services.impl.ProveedorServiceImpl
import ar.edu.unq.services.runner.DataBaseType

class Banner {
    var id: Int? = null
    var image: String = ""
    var category: String = ""

    constructor(){}

    constructor(bannerId: Int, image: String, category: String) {
        this.id = bannerId
        this.image = image
        this.category = category
    }
}