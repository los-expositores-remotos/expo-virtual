Feature: Eliminar un cronograma de actividades

  Scenario: Al presionar sobre el botón de eliminar cronograma, el sistema se encarga de eliminarlo, notificando que la acción ha sido exitosa.
    Given Un cronograma de actividades existente
    When Elimino el cronograma
    Then El cronograma ya no existe