Feature:  Visualizacion de productos

  Scenario: Al seleccionar un proveedor se muestran los articulos del mismo
    Given Un proveedor sin productos
    When Agrego un producto "producto1"
    When Agrego un producto "producto2"
    Then Los productos del proveedor son "producto1" y "producto2"

  Scenario: Al seleccionar un producto se muestran las caracteristicas y precio del mismo
    Given Un producto vacío
    When Le asigno la descripcion "descripcion"
    When ALe asigno el precio 45
    Then La descripción del producto es "descripción"
    And El precio del producto es 45
