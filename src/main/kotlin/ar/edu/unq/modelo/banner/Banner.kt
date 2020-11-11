package ar.edu.unq.modelo.banner

import ar.edu.unq.modelo.ModelObjectWithBsonId

class Banner : ModelObjectWithBsonId {
    var image: String = ""
    lateinit var category: BannerCategory

    constructor()

    constructor(image: String, category: BannerCategory) {
        this.image = image
        this.category = category
    }

    override fun castearAMiTipo(other: Any): Banner {
        return other as Banner
    }
}