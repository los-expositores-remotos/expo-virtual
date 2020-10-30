package modelo

import com.fasterxml.jackson.databind.util.ArrayBuilders.addToList

class Expo(
    var companyId: Int = 0,
    var productId: Int = 0,
    val companies: MutableList<Company> = mutableListOf(),
    var banners: MutableList<Banner> = mutableListOf()
) {
    fun setCompanyId() = ++companyId
    fun setProductId() = ++productId
    fun addCompany(company: Company) = addToList(companies, company)
    fun addBanner(banner: Banner) = addToList(banners, banner)
}
