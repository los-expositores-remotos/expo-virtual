package ar.edu.unq.modelo

import org.bson.types.ObjectId

class Usuario : ModelObjectWithBsonId {
    var userName: String = ""
    var password: String = ""
    /*var level: String = ""*/

    constructor()

    constructor(
        userName: String,
        password: String/*,
        level: String*/
    ) {
        this.userName = userName
        this.password = password
      /*  this.level = level*/
    }
}