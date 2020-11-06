package ar.edu.unq.modelo

enum class Orden {
    PRECIO, ALFABETICAMENTE, ANTIGUEDAD, VENDIDOS, PROMOCION
}

object Buscador {

    fun ordenar(texto: String, productos: Collection<Producto>, filtros: List<Orden>): MutableList<Producto> {
        var resultado = emptyList<Pair<Producto, Int>>().toMutableList()
        for (producto in productos) {
            if (contienePalabrasDelNombre(texto, producto.itemName.split(" ")) or contieneTags(texto, producto.listTags) or contienePalabrasDeLaDescripcion(texto, producto.description.split(" "))) {
                val tupla = Pair(producto, cantidadPalabrasQueCoinciden(texto, producto.description.split(" ")))
                resultado.add(tupla)
            }
        }
        loop@ for (filtro in filtros) {
            when (filtro) {
                Orden.PRECIO -> resultado = resultado.sortedWith(compareBy() { it.first.itemPrice }).toMutableList()
                Orden.ALFABETICAMENTE -> resultado = resultado.sortedWith(
                        compareBy(String.CASE_INSENSITIVE_ORDER) { it.first.itemName }
                ).toMutableList()
                Orden.ANTIGUEDAD -> resultado.sortedWith(compareBy { it.first.itemPrice })
                Orden.VENDIDOS -> resultado.sortedWith(compareBy { it.first.itemPrice })
                Orden.PROMOCION -> resultado = resultado.sortedWith(
                        compareBy() { it.first.promotionalPrice }
                ).toMutableList()
            }
        }
        return resultado.map { it.first }.toMutableList()
    }

    fun contienePalabrasDelNombre(texto: String, palabrasDelNombre: List<String>): Boolean {
        for (palabra in palabrasDelNombre) {
            if (texto.contains(palabra)) {
                return true
            }
        }
        return false
    }

    fun contieneTags(texto: String, listaTags: List<String>): Boolean {
        for (tag in listaTags) {
            if (texto.contains(tag)) {
                return true
            }
        }
        return false
    }

    fun contienePalabrasDeLaDescripcion(texto: String, palabrasDeLaDescripcion: List<String>): Boolean {
        for (palabra in palabrasDeLaDescripcion) {
            if (texto.contains(palabra)) {
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