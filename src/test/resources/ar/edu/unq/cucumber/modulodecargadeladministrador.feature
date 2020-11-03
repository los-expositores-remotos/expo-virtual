Feature: Modulo de carga del administrador

  Scenario: Carga proveedores en la base de datos
    Given Una base de datos de proveedores vacia
    When Cargo un proveedor "proveedor1"
    When Cargo un proveedor "proveedor2"
    When Cargo un proveedor "proveedor3"
    Then Los proveedores "proveedor1", "proveedor2" y "proveedor3" estan en la base de datos
    And La cantidad de proveedores en la base de datos es 3