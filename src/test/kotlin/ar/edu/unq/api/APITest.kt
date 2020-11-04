package ar.edu.unq.api

import ar.edu.unq.API.CompanyViewMapper
import ar.edu.unq.API.SupplierRegisterMapper
import ar.edu.unq.API.levantarAPI
import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.ProveedorService
import ar.edu.unq.services.impl.ProveedorServiceImpl
import ar.edu.unq.services.runner.DataBaseType
import io.javalin.Javalin
import org.bson.types.ObjectId
import org.junit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.test.assertEquals



class APITest {
    lateinit var app1: Javalin
//    lateinit var retrofit: Retrofit
//    lateinit var apiService: APIService
    val proveedorService: ProveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.TEST)
//    lateinit var retrofit: Retrofit

    private val apiService: APIService = this.crearCliente().create(APIService::class.java)

//    companion object {
//        init {
//            // things that may need to be setup before companion class member variables are instantiated
//        }
//
//        lateinit var app: Javalin
//        var puerto: Int = 7000
//
//        @BeforeClass
//        @JvmStatic
//        fun arrancarAPI() {
//            //this.app = levantarAPI(7000, DataBaseType.TEST)
//        }
//
//        @AfterClass
//        @JvmStatic
//        fun pararAPI(){
//            //this.app.stop()
//        }
//
//    }


    @Before
    fun setUp() {
        levantarAPI(7000, DataBaseType.TEST)
//        Thread.sleep(5000)
//        this.consulta(this.apiService.createCompany(SupplierRegisterMapper("LaCompany", "www.images.com/lacompany.png", "www.facebook.com/LaCompany", "www.instagram.com/LaCompany", "www.lacompany.com"))){}
//        while(true){
//
//        }
//        println("hola")
    }

    fun sendGet() {
        this.proveedorService.crearProveedor(Proveedor("LaCompany"))
        val url = URL("http://localhost:7000/companies/")

        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"  // optional default is GET

            println("\nSent 'GET' request to URL : $url; Response Code : $responseCode")

            inputStream.bufferedReader().use {
                it.lines().forEach { line ->
                    println(line)
                }
            }
        }
    }


    @Test
    fun testGetAllCompanies(){
        sendGet()
//        this.consulta(this.apiService.getAllCompanies()) { companieViewMappers ->
//            val companies = this.mapAllToCompanies(companieViewMappers).toSet()
//            assertEquals(this.proveedorService.recuperarATodosLosProveedores().toSet(), companies)
        }


    private fun mapAllToCompanies(companieViewMappers: List<CompanyViewMapper>): List<Proveedor> {
        return companieViewMappers.map{ mapToCompany(it) }
    }

    private fun mapToCompany(companyViewMapper: CompanyViewMapper): Proveedor {
        val proveedor = Proveedor(companyViewMapper.companyName,
        companyViewMapper.companyImage,
        companyViewMapper.facebook,
        companyViewMapper.instagram,
        companyViewMapper.web)
        proveedor.id = ObjectId(companyViewMapper.id)
        return proveedor
    }

    @After
    fun deleteAll(){
        //this.proveedorService.deleteAll()
        //this.app1.stop()
    }



    private fun crearCliente(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://localhost:${7000}/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun <T> consulta(call: Call<T>, bloque: (T) -> Unit) {
        //getRetrofit().create(APIService::class.java).getCharacterByName("$query/images")
            call.enqueue(
                    object: Callback<T> {
                        override fun onResponse(call: Call<T>,
                                                response: Response<T>) {
                            val respuesta: T = response.body() ?: throw Exception("")
                            bloque(respuesta)
                        }

                        override fun onFailure(call: Call<T>, t: Throwable) {
                            throw t
                        }
                    }
            )
    }
}
