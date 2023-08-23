package com.android.apppromotion.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.apppromotion.model.PromoModel
import com.android.apppromotion.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel() : ViewModel() {

    private val BASE_URL = "https://content.digi46.id/"
    private  val AUTH_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiaWF0IjoxNjc1OTE0MTUwLCJleHAiOjE2Nzg1MDYxNTB9.TcIgL5CDZYg9o8CUsSjUbbUdsYSaLutOWni88ZBs9S8"

    /*private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original: Request = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .header("Authorization", AUTH_TOKEN)
            val request: Request = requestBuilder.build()
            val response = chain.proceed(request)

            // Logging
            val responseBody = response.peekBody(1024 * 1024) // Read up to 1MB of response data
            Log.d("Network", "Request URL: ${request.url}")
            Log.d("Network", "Response: ${responseBody.string()}")

            response
        }
        .build()*/


    private val apiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        /*.client(okHttpClient)*/
        .build()
        .create(ApiService::class.java)

    val promos = MutableStateFlow<List<PromoModel>>(emptyList())
    val isLoading = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = apiService.getPromos()
                promos.value = response
            } catch (e: Exception) {
                // Handle error
            } finally {
                isLoading.value = false
            }
        }
    }
}
