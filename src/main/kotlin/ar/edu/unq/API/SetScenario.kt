package ar.edu.unq.API

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import modelo.Company
import modelo.Expo

class SetScenario {

    private fun readFile(name: String): String {
        return object {}::class.java.classLoader.getResource(name).readText()
    }

    private fun getCompanies(): MutableList<CompanyData> {
        val companiesString = readFile("empresas-productos.json")
        val companyDataType = object : TypeToken<MutableList<CompanyData>>() {}.type
        return Gson().fromJson(companiesString, companyDataType)
    }

    private fun addAllCompanies(expo: Expo) {
        val companies = getCompanies()
        companies.forEach {
            expo.addCompany(
                Company(
                    it.nombreDeEmpresa,
                    it.imagenDeLaEmpresa,
                    it.facebook,
                    it.instagram,
                    it.web
                )
            )
        }
    }

    fun getExpo(): Expo {
        val expo = Expo()
        addAllCompanies(expo)
        return expo
    }
}