package ar.edu.unq.API

import ar.edu.unq.modelo.Usuario

class ExistsException(user: Usuario) : Exception(
    "Error: Exist another ${user::class.java.simpleName as String} with ${user.userName}"
)

class NotFoundException(
    classError: String,
    val prop: String,
    val value: String
) : Exception("""Error: Not found $classError with $prop = $value""")