Feature: Ordenar productos por precios

  Scenario: Como usuario quiero ordenar los productos por precio
    Given Una base de datos con seis productos: "productoA", "productoB", "productoC", "productoD", "productoE", "productoF"
    When Ordeno los productos por precios en forma ascendente
    Then Obtengo los productos ordenados por precio "productoB", "productoA","productoC", "productoD", "productoE", "productoF"