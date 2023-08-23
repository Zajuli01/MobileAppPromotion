package com.android.apppromotion.network

import com.android.apppromotion.model.PromoModel
import retrofit2.http.GET

interface ApiService {
    @GET("promos")
    suspend fun getPromos(): List<PromoModel>

    /*
    @Headers("Accept: application/json")
    @GET("promos")
    suspend fun getPromos(
        @Header("Authorization") token: String
    ): List<PromoModel>
     */
}
