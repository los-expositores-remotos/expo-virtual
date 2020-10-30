package modelo

import ar.edu.unq.API.NotFoundException
import com.fasterxml.jackson.databind.util.ArrayBuilders.addToList

class Expo(
    var companyId: Int = 0,
    var productId: Int = 0,
    var bannerId: Int = 0,
    val companies: MutableList<Company> = mutableListOf(),
    var banners: MutableList<Banner> = mutableListOf()
) {
    fun setCompanyId() = ++companyId
    fun setProductId() = ++productId
    fun setBannerId() = ++bannerId
    fun addCompany(company: Company) = addToList(companies, company)
    fun addBanner(banner: Banner) = addToList(banners, banner)
    fun removeBanner(id: String) = banners.removeIf { it.id.toString() == id }
    fun removeSupplier(id: String) = companies.removeIf { it.id.toString() == id }
    fun updateCompanyWithId(id: String, company: Company) {
        companies.removeIf { it.id.toString() == id }
        companies.add(company)
    }
}
