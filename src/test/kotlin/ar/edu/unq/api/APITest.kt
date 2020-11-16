package ar.edu.unq.api

import ar.edu.unq.API.BannerViewMapper
import ar.edu.unq.API.CompanyRegisterMapper
import ar.edu.unq.API.CompanyViewMapper
import ar.edu.unq.API.client.APIClient
import ar.edu.unq.API.controllers.BannerController
import ar.edu.unq.API.controllers.CompanyController
import ar.edu.unq.API.controllers.ProductController
import ar.edu.unq.API.levantarAPI
import ar.edu.unq.dao.ProveedorDAO
import ar.edu.unq.dao.mongodb.MongoBannerDAOImpl
import ar.edu.unq.dao.mongodb.MongoProductoDAOImpl
import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.BannerService
import ar.edu.unq.services.ProductoService
import ar.edu.unq.services.ProveedorService
import ar.edu.unq.services.impl.BannerServiceImpl
import ar.edu.unq.services.impl.ProductoServiceImpl
import ar.edu.unq.services.impl.ProveedorServiceImpl
import ar.edu.unq.services.runner.DataBaseType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.JsonParser
import io.javalin.Javalin
import org.bson.types.ObjectId
import org.junit.*
import java.io.BufferedReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class APITest {
    private lateinit var proveedor: CompanyRegisterMapper
    private val proveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.TEST)

    companion object {
        private const val url = "http://localhost"
        private const val puerto = 8000
        private val apiClient = APIClient(this.url, this.puerto)
        lateinit var app: Javalin
//        private val backendProveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.TEST)
//        private val backendProductoService =
//            ProductoServiceImpl(MongoProveedorDAOImpl(), MongoProductoDAOImpl(), DataBaseType.TEST)
//        private val backendBannerService = BannerServiceImpl(MongoBannerDAOImpl(), DataBaseType.TEST)
//        private val bannerController = BannerController(backendBannerService, backendProveedorService, backendProductoService)
//        private val productController = ProductController(backendProveedorService, backendProductoService)
//        private val companyController = CompanyController(backendProveedorService, backendProductoService)

        @BeforeClass
        @JvmStatic
        fun arrancarAPI() {
            app = levantarAPI(puerto, DataBaseType.TEST)
        }

        @AfterClass
        @JvmStatic
        fun pararAPI(){
            this.app.stop()
        }
    }


    @Before
    fun setUp() {
        this.proveedor = CompanyRegisterMapper("LaCompany", "www.images.com/lacompany.png", "www.facebook.com/LaCompany", "www.instagram.com/LaCompany", "www.lacompany.com")
        val proveedor1 = CompanyRegisterMapper("LaCompany1", "www.images.com/lacompany.png", "www.facebook.com/LaCompany", "www.instagram.com/LaCompany", "www.lacompany.com")
        val proveedor2 = CompanyRegisterMapper("LaCompany2", "www.images.com/lacompany.png", "www.facebook.com/LaCompany", "www.instagram.com/LaCompany", "www.lacompany.com")
        val result = apiClient.sendPost("companies", mapearAJSON(proveedor))
        apiClient.sendPost("companies", mapearAJSON(proveedor1))
        apiClient.sendPost("companies", mapearAJSON(proveedor2))
    }

    @Test
    fun testGetAllCompanies() {
        val result = apiClient.sendGet("companies")
        val resultParse = this.parsearAT<List<CompanyViewMapper>>("{" + result[0] + "}")
        println(resultParse)
        val proveedorRecuperadoJSON = result[0]
        val proveedorRecuperado = this.companyViewMapperToCompanyRegisterMapper(this.parsearAT<CompanyViewMapper>(proveedorRecuperadoJSON))
        assertEquals(this.proveedor, proveedorRecuperado)
    }

    @After
    fun deleteAll() {
        this.proveedorService.deleteAll()
    }

    private inline fun <reified T> parsearAT(t: String): T {
        val tJSON: String = t.subSequence(1, t.length-1).toString()
        return ObjectMapper().readValue(tJSON)
    }

    private fun <T> mapearAJSON(t: T): String {
        return ObjectMapper().writeValueAsString(t)
    }

    private fun companyViewMapperToCompanyRegisterMapper(companyViewMapper: CompanyViewMapper): CompanyRegisterMapper {
        return CompanyRegisterMapper(
            companyViewMapper.companyName,
            companyViewMapper.companyImage,
            companyViewMapper.facebook,
            companyViewMapper.instagram,
            companyViewMapper.web
        )
    }
}
