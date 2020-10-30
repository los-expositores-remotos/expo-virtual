package modelo

import ar.edu.unq.API.NotFoundException
import com.fasterxml.jackson.databind.util.ArrayBuilders.addToList
import java.util.ArrayList

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
    fun removeProduct(id: String) {
        var products: List<Product> = this.makeListFromListofList(companies.map{ it.productos})!!
        var product: Product = products.find { it.id.toString() == id  }!!
        var idProveedor = product.idProveedor
        var company = companies.find { it.id.toString() == idProveedor.toString() }
        this.updateCompanyWithId(product.idProveedor.toString(), Company(idProveedor, company!!.nombreDeEmpresa, company!!.imagenDeLaEmpresa, company!!.facebook, company!!.instagram, company!!.web, company!!.productos.filterNot { it.id.toString() == id }.toMutableList()))
    }

    fun addProduct(product: Product){
        var company = companies.find { it.id.toString() == product.idProveedor.toString() }
        company!!.productos.add(product)
        //this.updateCompanyWithId(product.idProveedor.toString(), Company(product.idProveedor, company!!.nombreDeEmpresa, company!!.imagenDeLaEmpresa, company!!.facebook, company!!.instagram, company!!.web, company!!.productos.filterNot { it.id.toString() == id }.toMutableList()) )
    }

    fun getProduct(id: String): Product {
        var products: List<Product> = this.makeListFromListofList(companies.map{ it.productos})!!
        var product: Product = products.find { it.id.toString() == id }!!
        println(product)
        return product
    }

    fun <E> makeListFromListofList(iter: List<List <E>>): List<E>? {
        val list: MutableList<E> = ArrayList()
        for (item in iter) {
            item.forEach { list.add(it) }
        }
        return list
    }
}
