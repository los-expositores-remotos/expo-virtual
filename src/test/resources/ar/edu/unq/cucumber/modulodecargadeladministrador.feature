# language: es

Característica: Modulo de carga del administrador

  Escenario: Carga proveedores en la base de datos
    Dada Una base de datos de proveedores vacia
    Cuando Cargo a los proveedores
        | proveedor1 |
        | proveedor2 |
        | proveedor3 |
    Entonces Los siguientes proveedores estan en la base de datos
        | proveedor1 |
        | proveedor2 |
        | proveedor3 |
    Y La cantidad de proveedores en la base de datos es 3