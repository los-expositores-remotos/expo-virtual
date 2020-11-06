Feature: Búsqueda de Artículos desde el buscador

  Scenario: Al ingresar una palabra clave en el buscador del sitio web, se obtienen los productos coincidentes
    Given Una base de datos con cuatro productos: "productoA", "productoB", "productoC", "productoD"
    Given Una palabra "producto"
    When Busco productos con ese dato
    Then Devuelvo de los productos "productoA", "productoB", "productoC" y "productoD", los que coinciden con la busqueda