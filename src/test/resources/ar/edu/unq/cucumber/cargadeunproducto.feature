Feature: Carga de un producto

    Scenario:   Al ingresar todo los campos se carga el producto al proveedor,  Al ingresar todo los campos y
    presionar el botón de agregar se carga el producto al proveedor y se recibe una notificación de carga exitosa

        Given un nombre de un producto "producto"
        Given una descripción "descripcion"
        Given un stock 10
        Given un precio normal 100
        Given un precio promocional 90
        Given una longitud 11
        Given un ancho 12
        Given un alto 13
        Given un peso en kilos 1
        When creo un producto con estos datos
        Then el producto cargado "producto" se encuentra en la base de datos
        And Sus datos son "producto", "descripcion", 10, 100, 90, 11, 12, 13, 1


