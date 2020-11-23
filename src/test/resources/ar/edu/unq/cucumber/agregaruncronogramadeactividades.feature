# language: es
Característica: Agrega Un Cronograma De Actividades

  Escenario: Al presionar sobre el botón de agregar cronograma de actividades y cargar la imagen, el sistema se encarga de agregarlo, notificando que la acción ha sido exitosa
    Dado un link a una imagen "www.images.com/schedule.jpg"
    Dada una categoria "SCHEDULE"
    Cuando creo al schedule con esos datos
    Entonces el schedule esta en la base de datos
    Y sus datos son "www.images.com/schedule.jpg" y "SCHEDULE"

  Escenario: En el caso de presionar sobre el botón de agregar cronograma de actividades y no haber cargado una imagen, debe notificarse que fue una carga fallida
    Dado un link a una imagen ""
    Dado una categoria "SCHEDULE"
    Cuando trato de crear el schedule
    Entonces lanza una excepcion
    Y no existe el schedule