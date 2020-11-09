Feature: Modificación de un Producto
  Scenario: Como administrador quiero modificar un producto para que el usuario lo  vea correctamente

    Given Un nombre de un producto "producto"
    Given Una descripción "descripcion"
    Given Un stock 10
    Given Un precio normal 100
    Given Un precio promocional 90
    When Creo un producto con estos datos
    When Cuando modifico su nombre por "sarasa"
    Then sus datos son "sarasa", "descripcion", 10, 100, 90
