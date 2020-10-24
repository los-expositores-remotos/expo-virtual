package modelo

import com.fasterxml.jackson.databind.util.ArrayBuilders.addToList

class Expo(
    val companies: MutableList<Company> = mutableListOf()
) {
    fun addCompany(company: Company) = addToList(companies, company)
}