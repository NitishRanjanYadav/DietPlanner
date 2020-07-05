package com.luxeva.reminder.model

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp
import java.util.*

data class ItemData(

    @field:SerializedName("meal_time")
    val mealTime: String? = null,

    @field:SerializedName("food")
    val food: String? = null,
    var mealTimestamp: Date
)