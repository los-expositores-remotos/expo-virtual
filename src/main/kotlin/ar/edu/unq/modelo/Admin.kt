package ar.edu.unq.modelo

class Admin : ModelObjectWithBsonId {

    var userName : String = ""
    var password : String = ""

    constructor()

    constructor(
        userName: String,
        password: String
    ) {
        this.userName = userName
        this.password = password
        }
    override fun castearAMiTipo(other: Any): Admin {
        return  other as Admin
    }
}
