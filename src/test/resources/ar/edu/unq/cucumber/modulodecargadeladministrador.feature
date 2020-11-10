Feature: Modulo de carga del administrador

  Scenario: Carga proveedores en la base de datos
    Given Una base de datos de proveedores vacia
    When Cargo a los proveedores
        | proveedor1 |
        | proveedor2 |
        | proveedor3 |
    Then Los siguientes proveedores estan en la base de datos
        | proveedor1 |
        | proveedor2 |
        | proveedor3 |
    And La cantidad de proveedores en la base de datos es 3