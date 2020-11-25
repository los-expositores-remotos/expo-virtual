# language: es

Característica: Mostrar todos los proveedores desde el menu

  Escenario: Al presionar sobre el boton empresas accedo a todas las existentes
    Dada Una database de proveedores vacia
    Cuando Agrego un proveedor "proveedor1"
    Cuando Agrego un proveedor "proveedor2"
    Entonces Los proveedores "proveedor1" y "proveedor2" estan cargados en la base de datos