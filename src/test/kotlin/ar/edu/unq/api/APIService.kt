package ar.edu.unq.api

import ar.edu.unq.API.CompanyViewMapper
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface APIService {
    @GET
    fun getAllCompanies(@Url url: String): Call<List<CompanyViewMapper>>

    @POST("companies")
    fun createCompany(@Body body: CompanyViewMapper): Call<CompanyViewMapper>
}