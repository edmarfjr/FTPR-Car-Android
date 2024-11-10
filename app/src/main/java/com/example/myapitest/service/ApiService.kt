package com.example.myapitest.service

import com.example.myapitest.model.Car
import com.example.myapitest.model.CarValue
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("car")
    suspend fun getItens(): List<Car>

    @GET("car/{id}")
    suspend fun getItens(@Path("id") id: String)

    @DELETE("car/{id}")
    suspend fun deleteItens(@Path("id") id: String)

    @POST("car")
    suspend fun addItens(@Body car: CarValue): Car
}