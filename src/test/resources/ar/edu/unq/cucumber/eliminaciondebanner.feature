Feature: Eliminacion de banner

  Scenario: Al presionar el boton de eliminacion de Banner se despliegan todos los Banners
    Given Un banner y una categoria "www.images.com/banner1.png" y "HOME"
    Given Un banner y una categoria "www.images.com/banner2.png" y "CLASS"
    When Creo los banners
    Then El banner "www.images.com/banner1.png" con categoria "HOME" existe
    And El banner "www.images.com/banner2.png" con categoria "CLASS" existe

  Scenario: Si se presiona el icono de eliminacion de Banner se muestra una notificacion de eliminacion exitosa
    Given Un banner existente
    When Lo elimino
    Then El banner ya no existe