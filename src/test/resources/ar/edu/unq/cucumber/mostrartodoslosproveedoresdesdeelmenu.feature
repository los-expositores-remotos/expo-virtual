Feature: Mostrar todos los proveedores desde el menu

  Scenario: Al presionar sobre el boton empresas accedo a todas las existentes
    Given Una database de proveedores vacia
    When Agrego un proveedor "proveedor1"
    When Agrego un proveedor "proveedor2"
    Then Los proveedores "proveedor1" y "proveedor2" estan cargados en la base de datos