Feature: Ordenar alfabéticamente los productos

  Scenario: Como usuario quiero poder ordenar los productos alfabeticamente
    Given Una base de datos con cinco productos: "productoE", "productoC", "productoB", "productoA", "productoD"
    When ordeno los productos alfabeticamente de forma ascendente
    Then obtengo los productos ordendos "productoA", "productoB", "productoC", "productoD", "productoE"