package modelo

import com.fasterxml.jackson.databind.util.ArrayBuilders.addToList

class Expo(
    val companies: MutableList<Company> = mutableListOf(),
    var banners: MutableList<Banner> = mutableListOf()
) {
    fun addCompany(company: Company) = addToList(companies, company)
    fun addBanner(banner: Banner) = addToList(banners, banner)
}
