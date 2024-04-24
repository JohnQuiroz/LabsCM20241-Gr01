package co.edu.udea.compumovil.gr01_20241.lab2.network

import javax.net.ssl.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

private const val BASE_URL = "https://demo6267984.mockable.io/"

val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
})

fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
    val sslContext = SSLContext.getInstance("SSL")//.init(null, trustAllCerts, java.security.SecureRandom())
    sslContext.init(null, trustAllCerts, java.security.SecureRandom())

    val sslSocketFactory = sslContext.getSocketFactory()
    return OkHttpClient.Builder()
        .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        .hostnameVerifier { _, _ -> true }
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .client(
        getUnsafeOkHttpClient().build()
        )
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