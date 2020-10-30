package ar.edu.unq.API.controllers

import ar.edu.unq.API.*
import modelo.Expo
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import modelo.Company
import modelo.Product

class SupplierController(val backend: Expo) {

    /*  this.companyName = companyName
        this.companyImage = companyImage
        this.facebook = facebook
        this.instagram = instagram
        this.web = web
*/
    fun createSupplier(ctx: Context) {
        try {
            val newSupplier = ctx.bodyValidator<SupplierRegisterMapper>()
                .check(
                    { it.companyName != null && it.companyImage != null && it.facebook != null && it.instagram != null && it.web != null },
                    "Invalid body : companyName, companyImage, facebook, instagram and web are required"
                )
                .get()
            val supplier = Company(
                backend.setCompanyId(), newSupplier.companyName!!, newSupplier.companyImage!!, newSupplier.facebook!!, newSupplier.instagram!!, newSupplier.web!!, mutableListOf())
            println(supplier)
            backend.addCompany(supplier)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: ExistsException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun getSupplierById(ctx: Context) {
        try {
            val supplierId: String = ctx.pathParam("supplierId")
            val supplier: Company = this.searchContentById(supplierId) as Company

            ctx.status(200)
            ctx.json(
                CompanyViewMapper(
                    supplier.id.toString(),
                    supplier.nombreDeEmpresa,
                    supplier.imagenDeLaEmpresa,
                    supplier.facebook,
                    supplier.instagram,
                    supplier.web,
                    toSimpleData(supplier.productos)
                )
            )
        } catch (e: NotFoundException) {
            throw NotFoundResponse(e.message.toString())
        }
    }
    fun getProductsBySuppId(ctx: Context) {
        try {
            val supplierId: String = ctx.pathParam("supplierId")
            val supplier: Company = this.searchContentById(supplierId) as Company
            val products = supplier.productos.map{ ProductsViewMapper(it.id.toString(),
                it.idProveedor.toString(),
                it.nombreDelArticulo,
                it.description,
                it.imagenes,
                it.stock,
                it.precio,
                it.precioPromocional) }
            ctx.status(200)
            ctx.json(products)
        } catch (e: NotFoundException) {
            throw NotFoundResponse(e.message.toString())
        }
    }

    fun searchContentById(supplierId: String?): Company {
        return backend.companies.find { it.id.toString() == supplierId } ?: throw NotFoundException("Supplier", "id", supplierId!!)
    }

    fun toSimpleData(lista: MutableCollection<Product>): List<ProductsViewMapper> {
        return lista.map { ProductsViewMapper(it.id.toString(), it.idProveedor.toString(), it.nombreDelArticulo, it.description, it.imagenes, it.stock, it.precio, it.precioPromocional) }
    }
}

