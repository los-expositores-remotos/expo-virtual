Feature:  Visualizacion de productos

  Scenario: Al seleccionar un proveedor se muestran los articulos del mismo
    Given Un proveedor vacio
    When Agrego un producto "producto1"
    When Agrego un producto "producto2"
    Then El provedor tiene "producto1"
    And El proveedor tiene "producto2"

  Scenario: Al seleccionar un producto se muestran las caracteristicas y precio del mismo
    Given Un producto "producto1"
    When Asigno descripcion "descripcion"
    When Asigno precio 45
    Then La descripción de "producto1" es "descripción"
    And El "producto1" tiene precio 45
