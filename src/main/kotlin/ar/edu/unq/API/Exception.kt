package ar.edu.unq.API


class ExistsException( item: String ) : Exception(
    "Error: Exist another $item" /*/${item::class.java.simpleName as String}/ +"with"+ /${item.idKey()} = ${item.idValue()}/" "*/
)

class NotFoundException(
    classError: String,
    val prop: String,
    val value: String
) : Exception("""Error: Not found $classError with $prop = $value""")