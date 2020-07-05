package com.luxeva.reminder.model

import com.google.gson.annotations.SerializedName

data class WeekDietData(

	@field:SerializedName("wednesday")
	val wednesday: List<ItemData>? = null,

	@field:SerializedName("thursday")
	val thursday: List<ItemData>? = null,

	@field:SerializedName("monday")
	val monday: List<ItemData>? = null
)