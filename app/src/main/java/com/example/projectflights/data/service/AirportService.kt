package com.example.projectflights.data.service
import com.example.projectflights.BuildConfig
import com.example.projectflights.data.service.dto.airports.AirportResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface AirportService {
    @Headers("X-RapidAPI-Key: 33fef736d0mshe0685f76ea6d2eep1eecc2jsn07389fb6cb2c", "X-RapidAPI-Host: sky-scrapper.p.rapidapi.com")
    @GET("searchAirport")
    suspend fun searchAirport(@Query("query") query: String
    ): AirportResponse

    companion object{
        fun create(): AirportService{

            val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY
            )

            val client = OkHttpClient
                    .Builder()
                .addInterceptor(httpLoggingInterceptor)
                    .build()
            return Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(AirportService::class.java)
        }
    }
}