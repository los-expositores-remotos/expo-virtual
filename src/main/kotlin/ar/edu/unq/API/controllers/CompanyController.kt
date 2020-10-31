package ar.edu.unq.API.controllers


import ar.edu.unq.API.*
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import io.javalin.http.Context
import ar.edu.unq.services.ProveedorService
import io.javalin.http.BadRequestResponse
import io.javalin.http.NotFoundResponse
import java.util.*


class CompanyController(val backendProveedorService: ProveedorService) {

    fun createSupplier(ctx: Context) {
        try {
            val newSupplier = ctx.bodyValidator<SupplierRegisterMapper>()
                .check(
                    { it.companyName != null && it.companyImage != null && it.facebook != null && it.instagram != null && it.web != null },
                    "Invalid body : companyName, companyImage, facebook, instagram and web are required"
                )
                .get()
            val supplier = Proveedor(
                newSupplier.companyName!!, newSupplier.companyImage!!, newSupplier.facebook!!, newSupplier.instagram!!, newSupplier.web!!)
            backendProveedorService.crearProveedor(supplier)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: ExistsException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun getSupplierById(ctx: Context) {
        try {
            val supplierId: String = ctx.pathParam("supplierId")
            val supplier: Proveedor = this.searchContentById(supplierId) as Proveedor

            ctx.status(200)
            ctx.json(
                CompanyViewMapper(
                    supplier.id.toString(),
                    supplier.companyName,
                    supplier.companyImage,
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

    fun allCompanies(ctx: Context) {

        val suppliers = backendProveedorService.recuperarATodosLosProveedores().map{ CompanyViewMapper(
            it.id.toString() ,
            it.companyName,
            it.companyImage,
            it.facebook,
            it.instagram,
            it.web,
            toSimpleData(it.productos)
        )
        }
        ctx.status(200)
        ctx.json(suppliers)
    }

    fun deleteSupplier(ctx: Context) {

        try {
            val id = ctx.pathParam("supplierId")
            backendProveedorService.borrarProveedor(id)
            ctx.status(204)
        } catch (e: ExistsException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun modifySupplier(ctx: Context) {

        try {
            val id = ctx.pathParam("supplierId")
            val newSupplier = ctx.bodyValidator<SupplierRegisterMapper>()
                .check(
                    { it.companyName != null && it.companyImage != null && it.facebook != null && it.instagram != null && it.web != null },
                    "Invalid body : companyName, companyImage, facebook, instagram and web are required"
                )
                .get()
            val supplier = this.searchContentById(id)
            println(supplier.companyName)
            supplier.companyName = newSupplier.companyName!!
            supplier.companyImage = newSupplier.companyImage!!
            supplier.facebook = newSupplier.facebook!!
            supplier.instagram = newSupplier.instagram!!
            supplier.web = newSupplier.web!!
            println(supplier.companyImage)
            backendProveedorService.actualizarProveedor(supplier)
            println(supplier.companyName)
            val updated = this.backendProveedorService.recuperarProveedor(id)
            println(updated!!.companyName)
            ctx.json(
                CompanyViewMapper(
                updated.id.toString(),
                updated.companyName,
                updated.companyImage,
                updated.facebook,
                updated.instagram,
                updated.web,
                toSimpleData(updated.productos)
            )
            ) } catch (e: NotFoundException) {
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

    fun producstBestSellers(ctx: Context) {
        /*traer los productos mas vendidos  EN ESTE CASO ME TRAE EL PRIMERO DE CADA EMPRESA
    * DEBE IMPLEMENTARSE DESDE EL BACKEND*/
        val bestSellersP = backendProveedorService.recuperarATodosLosProveedores().map{ ProductsViewMapper(it.productos.first().id.toString(), it.productos.first().idProveedor.toString(), it.productos.first().itemName,
            it.productos.first().description,
            it.productos.first().listImages,
            it.productos.first().stock,
            it.productos.first().itemPrice,
            it.productos.first().promotionalPrice) }
        ctx.status(200)
        ctx.json(bestSellersP)
    }

    fun productsNewest(ctx: Context) {
        /*traer los productos mas nuevos  EN ESTE CASO ME TRAE EL ULTIMO DE CADA EMPRESA
        * DEBE IMPLEMENTARSE DESDE EL BACKEND*/
        val newestP = backendProveedorService.recuperarATodosLosProveedores().map{ ProductsViewMapper(it.productos.last().id.toString(), it.productos.last().idProveedor.toString(), it.productos.last().itemName,
            it.productos.last().description,
            it.productos.last().listImages,
            it.productos.last().stock,
            it.productos.last().itemPrice,
            it.productos.last().promotionalPrice) }
        ctx.status(200)
        ctx.json(newestP)
    }

    fun productsWPromoPrice(ctx: Context) {
        /*traer los productos con precio promocional  EN ESTE CASO ME TRAE UNO RANDOM DE CADA EMPRESA
        * DEBE IMPLEMENTARSE DESDE EL BACKEND*/
        val newestP = backendProveedorService.recuperarATodosLosProveedores().map{ ProductsViewMapper(it.productos.random().id.toString(), it.productos.random().idProveedor.toString(), it.productos.random().itemName,
            it.productos.random().description,
            it.productos.random().listImages,
            it.productos.random().stock,
            it.productos.random().itemPrice,
            it.productos.random().promotionalPrice) }
        ctx.status(200)
        ctx.json(newestP)
    }

    //funciones auxiliares
    fun <E> makeListFromListofList(iter: List<List <E>>): List<E>? {
        val list: MutableList<E> = ArrayList()
        for (item in iter) {
            item.forEach { list.add(it) }
        }
        return list
    }

    fun searchContentById(supplierId: String?): Proveedor {
        return backendProveedorService.recuperarProveedor(supplierId!!) ?: throw NotFoundException("Supplier", "id", supplierId!!)
    }

    fun toSimpleData(lista: MutableCollection<Producto>): List<ProductsViewMapper> {
        return lista.map { ProductsViewMapper(it.id.toString(), it.idProveedor.toString(), it.itemName, it.description, it.listImages, it.stock, it.itemPrice, it.promotionalPrice) }
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