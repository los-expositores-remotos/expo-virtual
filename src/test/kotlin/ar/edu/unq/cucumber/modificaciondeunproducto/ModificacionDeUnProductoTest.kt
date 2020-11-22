package ar.edu.unq.cucumber.modificaciondeunproducto

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(features = ["src/test/resources/ar/edu/unq/cucumber/modificaciondeunproducto.feature"])
class ModificacionDeUnProductoTest
