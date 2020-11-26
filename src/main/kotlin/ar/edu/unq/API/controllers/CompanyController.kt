package ar.edu.unq.API.controllers


import ar.edu.unq.API.*
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.ProductoService
import io.javalin.http.Context
import ar.edu.unq.services.ProveedorService
import ar.edu.unq.services.impl.exceptions.ProveedorExistenteException
import ar.edu.unq.services.impl.exceptions.ProveedorInexistenteException
import io.javalin.http.BadRequestResponse
import io.javalin.http.NotFoundResponse

class CompanyController(val backendProveedorService: ProveedorService, val backendProductoService: ProductoService) {

    val aux: AuxiliaryFunctions = AuxiliaryFunctions(backendProveedorService, backendProductoService)

    fun createSupplier(ctx: Context) {
        try {
            val newSupplier = aux.companyBodyValidation(ctx)
            val supplier = Proveedor(
                newSupplier.companyName!!, newSupplier.companyImage!!, newSupplier.facebook!!, newSupplier.instagram!!, newSupplier.web!!)
            backendProveedorService.crearProveedor(supplier)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: ProveedorExistenteException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun createMassive(ctx: Context) {
        try {
            val newSuppliers = ctx.bodyValidator<MutableList<CompanyRegisterMapper>>()
                .check(
                    { it -> it.all  { it.companyName != null && it.companyImage != null && it.facebook != null && it.instagram != null && it.web != null }   },
                    "Invalid body : companyName, companyImage, facebook, instagram and web are required"
                )
                .get()
            newSuppliers.forEach {
                val supplier = Proveedor(
                    it.companyName!!, it.companyImage!!, it.facebook!!, it.instagram!!, it.web!!)
                backendProveedorService.crearProveedor(supplier)
            }
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: ProveedorExistenteException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun getSupplierById(ctx: Context) {
        try {
            val supplierId: String = ctx.pathParam("supplierId")
            val supplier: Proveedor = backendProveedorService.recuperarProveedor(supplierId)
            ctx.status(200)
            ctx.json(aux.proveedorClassToProveedorView(supplier))
        } catch (e: ProveedorInexistenteException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun allCompanies(ctx: Context) {
        val suppliers = aux.proveedorClassListToProveedorViewList(backendProveedorService.recuperarATodosLosProveedores() as MutableCollection<Proveedor>)
        ctx.status(200)
        ctx.json(suppliers)
    }

    fun deleteSupplier(ctx: Context) {
        try {
            val id = ctx.pathParam("supplierId")
            backendProveedorService.borrarProveedor(id)
            ctx.status(204)
        } catch (e: ProveedorInexistenteException) {
            throw NotFoundResponse(e.message.toString())
        }
    }

    fun modifySupplier(ctx: Context) {
        try {
            val id = ctx.pathParam("supplierId")
            val newSupplier = aux.companyBodyValidation(ctx)
            val supplier = backendProveedorService.recuperarProveedor(id)//aux.searchProveedorById(id)
            supplier.companyName = newSupplier.companyName!!
            supplier.companyImage = newSupplier.companyImage!!
            supplier.facebook = newSupplier.facebook!!
            supplier.instagram = newSupplier.instagram!!
            supplier.web = newSupplier.web!!
            backendProveedorService.actualizarProveedor(supplier)
            val updated = this.backendProveedorService.recuperarProveedor(id)
            ctx.json(aux.proveedorClassToProveedorView(updated))
        } catch (e: ProveedorInexistenteException) {
            throw NotFoundResponse(e.message.toString())
        }
    }

    fun imagesCompanies(ctx: Context) {
        val imagesC = backendProveedorService.recuperarATodosLosProveedores().map { CompanyImageViewMapper(it.companyImage) }
        ctx.status(200)
        ctx.json(imagesC)
    }

    fun namesCompanies(ctx: Context) {
        val namesC = backendProveedorService.recuperarATodosLosProveedores().map { CompanyNameViewMapper(it.companyName) }
        ctx.status(200)
        ctx.json(namesC)
    }
}