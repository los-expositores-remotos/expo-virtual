package ar.edu.unq.modelo

import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

abstract class ModelObjectWithBsonId {
    @BsonProperty("id")
    var id: ObjectId = ObjectId()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        if (id != this.castearAMiTipo(other).id) return false

        return true
    }

    abstract fun castearAMiTipo(other: Any): ModelObjectWithBsonId

    override fun hashCode(): Int {
        return id.hashCode()
    }
}