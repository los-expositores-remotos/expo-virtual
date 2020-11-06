package ar.edu.unq.API.controllers


import ar.edu.unq.API.*
import ar.edu.unq.modelo.Producto
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
        } catch (e: ProveedorExistenteException) {//seria bueno que contemple excepcion por nombre
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
        } catch (e: ProveedorExistenteException) {//seria bueno que contemple excepcion por nombre
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun getSupplierById(ctx: Context) {
        try {
            val supplierId: String = ctx.pathParam("supplierId")
            val supplier: Proveedor = backendProveedorService.recuperarProveedor(supplierId)// aux.searchProveedorById(supplierId)

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

    fun productsBestSellers(ctx: Context) {
        /*traer los productos mas vendidos  EN ESTE CASO ME TRAE EL PRIMERO DE CADA EMPRESA
    * DEBE IMPLEMENTARSE DESDE EL BACKEND*/
        val bestSellersP = backendProveedorService.recuperarATodosLosProveedores().map{ aux.productoClassToProductoView(it.productos.first())}
        ctx.status(200)
        ctx.json(bestSellersP)
    }

    fun productsNewest(ctx: Context) {
        /*traer los productos mas nuevos  EN ESTE CASO ME TRAE EL ULTIMO DE CADA EMPRESA
        * DEBE IMPLEMENTARSE DESDE EL BACKEND*/
        val newestP = backendProveedorService.recuperarATodosLosProveedores().map{ aux.productoClassToProductoView(it.productos.last()) }
        ctx.status(200)
        ctx.json(newestP)
    }

    fun productsWPromoPrice(ctx: Context) {
        /*traer los productos con precio promocional  EN ESTE CASO ME TRAE UNO RANDOM DE CADA EMPRESA
        * DEBE IMPLEMENTARSE DESDE EL BACKEND*/
        val newestP = backendProveedorService.recuperarATodosLosProveedores().map{ aux.productoClassToProductoView(it.productos.random()) }
        ctx.status(200)
        ctx.json(newestP)
    }

    fun searchCompanies(ctx: Context){
        val companieToSearch = ctx.queryParam("text")
        if(companieToSearch!!.isBlank()){
            throw BadRequestResponse("Invalid query - param text is empty")
        }
        val companieResult= backendProveedorService.buscarProveedores(companieToSearch)
        val allC = aux.proveedorClassListToProveedorViewList(companieResult as MutableCollection<Proveedor>)
        ctx.status(200)
        ctx.json(
            mapOf(
                "Companies" to allC
            )
        )
    }
}

//data class BannersRelatedViewMapper(val banners: Collection<BannerRelatedData>)
//    open fun toBannerData(lista: MutableCollection<Content>): List<BannerRelatedData> {
//        return lista.map { BannerRelatedData(it.id, it.description, it.title, evalBool(it.state), it.poster) }
//}
//data class BannerRelatedData (val id: String, val description: String, val title: String, val state: Boolean, val poster: String)
// orderByLowerPrice
//orderByHigherPrice
//orderByOldest
//orderByNewest
//orderByBestSellers
//orderByAlphabeticDesc
//orderByAlphabeticAsc