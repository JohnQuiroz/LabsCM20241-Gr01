package co.edu.udea.compumovil.gr01_20241.lab2.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://demo6267984.mockable.io/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface InfoApiService {
    @GET("detail")
    suspend fun getDetail(): String
}

object InfoApi{
    val retrofitService: InfoApiService by lazy{
        retrofit.create(InfoApiService::class.java)
    }
}