package ar.edu.unq.modelo.banner

import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

class Banner {
    @BsonProperty("id")
    var id: ObjectId = ObjectId()
    var image: String = ""
    lateinit var category: BannerCategory

    constructor()

    constructor(image: String, category: BannerCategory) {
        this.image = image
        this.category = category
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Banner

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}