# language: es

Característica: Eliminar un cronograma de actividades

  Escenario: Al presionar sobre el botón de eliminar cronograma, el sistema se encarga de eliminarlo, notificando que la acción ha sido exitosa.
    Dado Un cronograma de actividades existente
    Cuando Elimino el cronograma
    Entonces El cronograma ya no existe
