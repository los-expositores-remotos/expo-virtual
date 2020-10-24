package ar.edu.unq.API

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import modelo.Company
import modelo.Expo
import modelo.Product

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
                    it.web,
                    getProducts(it.productos)
                )
            )
        }
    }

    private fun getProducts(products: List<ProductData>): MutableList<Product> {
        return products.map {
            Product(
                    it.nombreDelArticulo,
                    it.description,
                    it.imagenes,
                    it.stock,
                    it.precio,
                    it.precioPromocional
            )
        }.toMutableList()
    }

    fun getExpo(): Expo {
        val expo = Expo()
        addAllCompanies(expo)
        return expo
    }
}