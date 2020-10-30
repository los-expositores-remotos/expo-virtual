package ar.edu.unq.API

import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ar.edu.unq.modelo.Banner
import ar.edu.unq.modelo.Expo

class SetScenario {

    private fun readFile(name: String): String {
        return object {}::class.java.classLoader.getResource(name).readText()
    }

    private fun getCompanies(): MutableList<Proveedor> {
        val companiesString = readFile("empresas-productosING.json")
        val companyDataType = object : TypeToken<MutableList<Proveedor>>() {}.type
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
                Proveedor(
                    companyId,
                    it.companyName,
                    it.companyImage,
                    it.facebook,
                    it.instagram,
                    it.web,
                    getProducts(it.productos, expo, companyId)
                )
            )
        }
    }

    private fun getProducts(products: List<Producto>, expo: Expo, companyId: Int): MutableList<Producto> {
        return products.map {
            Producto(
                    expo.setProductId().toInt(),
                    companyId,
                    it.itemName,
                    it.description,
                    it.images,
                    it.stock,
                    it.itemPrice,
                    it.promotionalPrice
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