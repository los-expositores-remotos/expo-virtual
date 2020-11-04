package ar.edu.unq.api

import ar.edu.unq.API.CompanyViewMapper
import ar.edu.unq.API.SupplierRegisterMapper
import org.bson.Document
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface APIService {
    @GET("companies")
    fun getAllCompanies(): Call<List<CompanyViewMapper>>

    @POST("companies")
    fun createCompany(@Body body: SupplierRegisterMapper): Call<Document>
}