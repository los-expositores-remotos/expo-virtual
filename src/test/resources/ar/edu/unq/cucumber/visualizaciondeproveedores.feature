# language: es

Característica:  Visualizacion de productos

  Escenario: Al seleccionar un proveedor se muestran los articulos del mismo
    Dado Un proveedor sin productos
    Cuando Agrego un producto "producto1"
    Cuando Agrego un producto "producto2"
    Entonces Los productos del proveedor son "producto1" y "producto2"

  Escenario: Al seleccionar un producto se muestran las caracteristicas y precio del mismo
    Dado Un producto vacio
    Cuando Le asigno la descripcion "descripcion"
    Cuando Le asigno el precio 45
    Entonces La descripción del producto es "descripcion"
    Y El precio del producto es 45
