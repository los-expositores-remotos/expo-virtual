package ar.edu.unq
import ar.edu.unq.MongoConnection
var conexion: MongoConnection = MongoConnection()

//fun main(args: Array<String>) {

   // pruebaCrearYBorrarCollecciones()
   // pruebaConteoDocumentos()
   // creamosUnProveedor()                      //se puede usar para cualquier documento, no solo proveedores
   // cantidadDeProveedores()                   //se puede usar para cualquier documento, no solo proveedores
   // insertarUnItem()
    // buscarPoveedoresConUnCriterio()          //se puede usar para cualquier documento, no solo proveedores
   //obtenerTodosLosElementoDeUnaColeccion()
    // agregarVariosProveedores()               //se puede usar para cualquier documento, no solo proveedores
//}

fun pruebaCrearYBorrarCollecciones() {

   // conexion.deleteCollection("pruebaV2")
      conexion.createCollection("pruebaVV")
    /*conexion.deleteCollection("pruebaVV")
      conexion.deleteCollection("prueba2")*/
}

fun pruebaConteoDocumentos() {

    println(conexion.countItemsFromCollection("pruebaVV"))
}

fun creamosUnProveedor() {

    val document = org.bson.Document()
    document.put("name","Fabian")
    document.put("lastname","Suarez")
    document.put("years",55)
    conexion.database.getCollection("pruebaVV")!!.insertOne(document)
}

fun cantidadDeProveedores() {
    println(conexion.countItemsFromCollection("pruebaVV"))
}

fun buscarPoveedoresConUnCriterio() {
    var documents = conexion.findEq<String, String>("lastname", "Suarez", "pruebaVV")
    if (documents != null) {
        for (d in documents) {
            println(d)
        }
    }
}

fun insertarUnItem() {
    val document = org.bson.Document()
    document.put("name","Juana")
    document.put("lastname","Azurduy")
    document.put("years",156)
    conexion.insertItem(document, "pruebaVV")
}

fun obtenerTodosLosElementoDeUnaColeccion() {
    var lista = conexion.getAllItems("pruebaVV")
    if (lista != null) {
        for (e in lista) {
            println(e)
            println(" ")
        }
    }
}

fun agregarVariosProveedores() {
    val document1 = org.bson.Document()
    document1.put("name","Ruben")
    document1.put("lastname","Macias")
    document1.put("years",16)

    val document2 = org.bson.Document()
    document2.put("name","Mariana")
    document2.put("lastname","Pagano")
    document2.put("years",46)

    val items: List<org.bson.Document> = listOf(document1, document2)
    conexion.insertManyItems("pruebaVV",items)
}