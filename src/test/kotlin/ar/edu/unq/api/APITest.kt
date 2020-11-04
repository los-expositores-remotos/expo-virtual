package ar.edu.unq.api

import ar.edu.unq.API.CompanyViewMapper
import ar.edu.unq.API.levantarAPI
import ar.edu.unq.services.runner.DataBaseType
import io.javalin.Javalin
import org.bson.types.ObjectId
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class APITest {
    lateinit var app: Javalin
    lateinit var retrofit: Retrofit
    lateinit var apiService: APIService
    var puerto: Int = 8000


    @BeforeClass
    fun arrancarAPI(){
        this.app = levantarAPI(this.puerto, DataBaseType.TEST)
        this.retrofit = this.crearCliente()
        this.apiService = this.retrofit.create(APIService::class.java)
    }

    @Before
    fun setUp(){
        //this.apiService.createCompany(CompanyViewMapper(ObjectId(),))
    }

    @After
    fun deleteAll(){

    }

    @AfterClass
    fun pararAPI(){
        this.app.stop()
    }

    private fun crearCliente(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://localhost:$puerto/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun <T> consulta(query: String, call: Call<T>, bloque: (T) -> Unit) {
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