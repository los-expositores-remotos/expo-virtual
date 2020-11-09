Feature: Eliminar clase en vivo

  Scenario: Poder eliminar una clase en vivo
    Given Una clase y una categoria "www.images.com/claseaovivo1.png" y "CLASS"
    When Creo la clase
    Then La clase "www.images.com/claseaovivo1.png" con categoria "CLASS" existe
    And Lo deleteo
    And La clase ya no existe