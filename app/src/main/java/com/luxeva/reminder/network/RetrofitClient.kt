package com.luxeva.reminder.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitClient {

    companion object Factory {

        @Synchronized
        fun getInstance(): APIService {
            val okHttpClient = OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(180, TimeUnit.SECONDS).writeTimeout(15, TimeUnit.MINUTES).build()
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("http://naviasmart.health/").build()
            return retrofit.create(APIService::class.java)
        }

    }
}