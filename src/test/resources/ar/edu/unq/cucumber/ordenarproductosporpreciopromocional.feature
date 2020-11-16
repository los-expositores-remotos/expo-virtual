Feature: Ordenar productos por precio promocional

  Scenario: Como usuario quiero ordenar los productos por precio promocional
    Given una base de datos con seis productos cuyos precios promocionales son: 5, 2, 10, 50, 30, 1
    When ordeno los productos por precios promocional
    Then obtengo los productos ordenados por precio promocional