package ar.edu.unq.modelo

import org.bson.types.ObjectId

class Usuario : ModelObjectWithBsonId {
    var nombre: String = ""
    var apellido: String = ""
    var dni : Int = 0

    constructor()

    constructor(
        nombre: String,
        apellido: String,
        dni: Int
    ) {
        this.nombre = nombre
        this.apellido = apellido
        this.dni = dni
    }
    override fun castearAMiTipo(other: Any): Usuario {
        return  other as Usuario
    }
}