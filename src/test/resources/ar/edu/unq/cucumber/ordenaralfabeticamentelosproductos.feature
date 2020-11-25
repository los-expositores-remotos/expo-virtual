# language: es

Característica: Ordenar alfabéticamente los productos

  Escenario: Como usuario quiero poder ordenar los productos alfabeticamente
    Dada Una base de datos con cinco productos: "productoE", "productoC", "productoB", "productoA", "productoD"
    Cuando ordeno los productos alfabeticamente de forma ascendente
    Entonces obtengo los productos ordendos "productoA", "productoB", "productoC", "productoD", "productoE"