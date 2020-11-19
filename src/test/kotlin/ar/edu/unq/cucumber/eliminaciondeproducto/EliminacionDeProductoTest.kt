package ar.edu.unq.cucumber.eliminaciondeproducto

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(features = ["src/test/resources/ar/edu/unq/cucumber/eliminaciondeproducto.feature"])
class EliminacionDeProductoTest
