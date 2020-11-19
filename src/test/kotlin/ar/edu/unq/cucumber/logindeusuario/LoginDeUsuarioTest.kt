package ar.edu.unq.cucumber.logindeusuario

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(features = ["src/test/resources/ar/edu/unq/cucumber/logindeusuario.feature"])
class LoginDeUsuarioTest
