package com.luxeva.reminder.network

import com.luxeva.reminder.model.Reminder
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("dummy/")
    fun getMealList(): Call<Reminder>
}