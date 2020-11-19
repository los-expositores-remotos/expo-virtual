package ar.edu.unq.cucumber.visualizaciondeproveedores

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(features = ["src/test/resources/ar/edu/unq/cucumber/visualizaciondeproveedores.feature"])
class VisualizacionDeProveedoresTest
