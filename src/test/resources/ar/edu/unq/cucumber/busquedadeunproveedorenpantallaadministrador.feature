Feature: Búsqueda de un proveedor en pantalla administrador

  Scenario: Buscar un proveedor por una palabra y el sistema me muestra todos los proveedores que contienen esa palabra en su nombre
    Given Dos proveedores de nombre "proveedorA" y "proveedorB"
    Given Un texto "prov"
    When Busco proveedores con ese dato
    Then Devuelvo de los proveedores "proveedorA" y "proveedorB", los que coinciden con la busqueda