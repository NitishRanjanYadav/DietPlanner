package com.luxeva.reminder

import com.luxeva.reminder.model.DietItem
import com.luxeva.reminder.model.ScheduleItem
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Helper {
    @Throws(Exception::class)
    fun getDietDataWithScheduleTimestamp(
        d1: String,
        d2: String,
        dietItems: ArrayList<DietItem>
    ): ArrayList<ScheduleItem> { // object
        val date1 = getDate(d1)
        val date2 = getDate(d2)
        val c1 = Calendar.getInstance()
        c1.time = date1
        val c2 = Calendar.getInstance()
        c2.time = date2
        val scheduleList = ArrayList<ScheduleItem>()
        val sundays = 0
        while (c2.after(c1)) {
            when (c1[Calendar.DAY_OF_WEEK]) {
                Calendar.MONDAY -> {
                    var i = 0
                    while (i < dietItems.size) {
                        if (dietItems[i].day.equals("monday", ignoreCase = true)) {
                            scheduleList.add(
                                ScheduleItem(
                                    dietItems[i],
                                    getTime(
                                        c1.time,
                                        dietItems[i].itemData.mealTime
                                    ),
                                    getId(c1, i)
                                )
                            )
                        }
                        i++
                    }
                }
                Calendar.THURSDAY -> {
                    var i = 0
                    while (i < dietItems.size) {
                        if (dietItems[i].day.equals("thursday", ignoreCase = true)) {
                            scheduleList.add(
                                ScheduleItem(
                                    dietItems[i],
                                    getTime(
                                        c1.time,
                                        dietItems[i].itemData.mealTime
                                    ),
                                    getId(c1, i)
                                )
                            )
                        }
                        i++
                    }
                }
                Calendar.WEDNESDAY -> {
                    var i = 0
                    while (i < dietItems.size) {
                        if (dietItems[i].day.equals("wednesday", ignoreCase = true)) {
                            scheduleList.add(
                                ScheduleItem(
                                    dietItems[i],
                                    getTime(
                                        c1.time,
                                        dietItems[i].itemData.mealTime
                                    ),
                                    getId(c1, i)
                                )
                            )
                        }
                        i++
                    }
                }
            }
            c1.add(Calendar.DATE, 1)
        }
        println("number of sundays between 2 dates is $sundays")
        return scheduleList
    }

    private fun getId(c1: Calendar, index: Int): String {
        return "" + c1[Calendar.MONTH] + c1[Calendar.DATE] + index
    }

    private fun getDate(s: String): Date? {
        val format: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        var date: Date? = null
        try {
            date = format.parse(s)
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return date
    }

    private fun getTime(c1Time: Date, mealTime: String?): Date {
        val time = mealTime!!.split(":").toTypedArray()
        c1Time.hours = Integer.valueOf(time[0])
        c1Time.minutes = Integer.valueOf(time[1])
        return c1Time
    }
}