package ar.edu.unq.modelo

import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

abstract class ModelObjectWithBsonId {
    @BsonProperty("id")
    var id: ObjectId = ObjectId()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ModelObjectWithBsonId

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}