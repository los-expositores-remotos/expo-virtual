package ar.edu.unq.API

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import modelo.Banner
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

    private fun getBanners(): MutableList<BannerData> {
        val bannersString = readFile("banners.json")
        val bannerDataType = object : TypeToken<MutableList<BannerData>>() {}.type
        return Gson().fromJson(bannersString, bannerDataType)
    }

    private fun addAllCompanies(expo: Expo) {
        val companies = getCompanies()
        companies.forEach {
            val companyId: Int = expo.setCompanyId()
            expo.addCompany(
                Company(
                    companyId,
                    it.nombreDeEmpresa,
                    it.imagenDeLaEmpresa,
                    it.facebook,
                    it.instagram,
                    it.web,
                    getProducts(it.productos, expo, companyId)
                )
            )
        }
    }

    private fun getProducts(products: List<ProductData>, expo: Expo, companyId: Int): MutableList<Product> {
        return products.map {
            Product(
                    expo.setProductId(),
                    companyId,
                    it.nombreDelArticulo,
                    it.description,
                    it.imagenes,
                    it.stock,
                    it.precio,
                    it.precioPromocional
            )
        }.toMutableList()
    }

    private fun addAllBanners(expo: Expo) {
        val banners = getBanners()
        banners.forEach {
            expo.addBanner(
                Banner(
                    expo.setBannerId(),
                    it.image
                )
            )
        }
    }

    fun getExpo(): Expo {
        val expo = Expo()
        addAllCompanies(expo)
        addAllBanners(expo)
        return expo
    }
}