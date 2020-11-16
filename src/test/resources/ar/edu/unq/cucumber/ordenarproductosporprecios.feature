Feature: Ordenar productos por precios

  Scenario: Como usuario quiero ordenar los productos por precio en forma ascendente
    Given una base de datos con seis productos cuyos precios son: 5, 2, 10, 50, 30, 1
    When ordeno los productos por precios en ascendentemente
    Then obtengo los productos ordenados por precio