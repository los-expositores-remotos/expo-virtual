package ar.edu.unq.modelo

object BuscadorProductos {

    private fun cantidadPalabrasContenidasEnTexto(textoDeBusqueda: String, texto: String): Int {
        return cantidadDeItemsContenidasEnTexto(palabrasContenidasEnTexto(textoDeBusqueda), texto)
    }

    private fun cantidadDeItemsContenidasEnTexto(palabras: Iterable<String>, texto: String): Int {
        val aMinusculas = {palabra:String -> palabra.toLowerCase()}
        val palabraMin = aMinusculas(texto)
        val containsText = {palabra:String -> palabraMin.contains(palabra)}
        return palabras.map(aMinusculas).toSet().count(containsText)
    }

    private fun palabrasContenidasEnTexto(texto: String): Iterable<String> {
        return texto.split(" ").toMutableList().filter { it != "" }
    }

    fun filtrar(texto: String, productos: Collection<Producto>): MutableList<Producto> {
        return productos.map {
            Pair(
                    cantidadPalabrasContenidasEnTexto(texto, it.itemName) + cantidadDeItemsContenidasEnTexto(it.listTags, texto) + cantidadPalabrasContenidasEnTexto(texto, it.description),
                    it
            )
        }.filter { it.first > 0 }.sortedBy {  it.second.itemName }.sortedByDescending { it.first }.map { it.second }.toMutableList()
    }
}