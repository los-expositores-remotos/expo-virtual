package ar.edu.unq.modelo

object BuscadorProveedores {
    fun filtrar(texto: String, proveedores: Collection<Proveedor>): MutableList<Proveedor> {
        val listaResultado = emptyList<Proveedor>().toMutableList()
        for(proveedor in proveedores) {
            if(proveedor.companyName.contains(texto, ignoreCase = true)) {
                listaResultado.add(proveedor)
            }
        }
        return listaResultado
    }
}