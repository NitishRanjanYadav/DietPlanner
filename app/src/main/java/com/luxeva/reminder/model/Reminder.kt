package com.luxeva.reminder.model

import com.google.gson.annotations.SerializedName

data class Reminder(

	@field:SerializedName("diet_duration")
	val dietDuration: Int? = null,

	@field:SerializedName("week_diet_data")
	val weekDietData: WeekDietData? = null
)