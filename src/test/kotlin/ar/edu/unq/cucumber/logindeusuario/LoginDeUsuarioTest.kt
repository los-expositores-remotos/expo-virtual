package ar.edu.unq.cucumber.logindeusuario

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(features = ["src/test/resources/ar/edu/unq/cucumber/logindeusuario.feature"])
class LoginDeUsuarioTest
