package ar.edu.unq.cucumber.eliminarunaclaseenvivo

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(features = ["src/test/resources/ar/edu/unq/cucumber/eliminarunaclaseenvivo.feature"])
class EliminarUnaClaseEnVivoTest