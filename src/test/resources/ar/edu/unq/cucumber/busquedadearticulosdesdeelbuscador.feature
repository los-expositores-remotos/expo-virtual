# language: es
Característica: Búsqueda de Artículos desde el buscador

  Escenario: Al ingresar una palabra clave en el buscador del sitio web, se obtienen los productos coincidentes
    Dada Una base de datos con cuatro productos: "productoA", "productoB", "productoC", "productoD"
    Dada Una palabra "producto"
    Cuando Busco productos con ese dato
    Entonces Devuelvo de los productos "productoA", "productoB", "productoC" y "productoD", los que coinciden con la busqueda