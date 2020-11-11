package ar.edu.unq.modelo

object BuscadorProductos {

    fun filtrar(texto: String, productos: Collection<Producto>): MutableList<Producto> {
        val resultado = emptyList<Pair<Producto, Int>>().toMutableList()
        for (producto in productos) {
            if (contienePalabrasDelNombre(texto.split(" "), producto.itemName) or contieneTags(texto, producto.listTags) or contienePalabrasDeLaDescripcion(texto, producto.description.split(" "))) {
                val tupla = Pair(producto, cantidadPalabrasQueCoinciden(texto, producto.itemName.split(" ") + producto.description.split(" ")))
                resultado.add(tupla)
            }
        }
        return resultado.sortedByDescending() { it.second }.map { it.first }.toMutableList()
    }

    fun contienePalabrasDelNombre(palabrasBuscadas: List<String>, nombre: String): Boolean {
        for (palabraBuscada in palabrasBuscadas) {
            if (nombre.contains(palabraBuscada, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    fun contieneTags(texto: String, listaTags: List<String>): Boolean {
        for (tag in listaTags) {
            if (texto.contains(tag, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    fun contienePalabrasDeLaDescripcion(texto: String, palabrasDeLaDescripcion: List<String>): Boolean {
        for (palabra in palabrasDeLaDescripcion) {
            if (palabra != "" && texto.contains(palabra, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    fun cantidadPalabrasQueCoinciden(texto: String, palabrasDescripcion: List<String>): Int {
        var contador = 0
        for (palabra in palabrasDescripcion) {
            if (texto.contains(palabra)) {
                contador += 1
            }
        }
        return contador
    }
}