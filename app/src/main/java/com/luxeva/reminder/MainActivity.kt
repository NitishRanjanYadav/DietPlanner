package com.luxeva.reminder

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Reminders
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.luxeva.reminder.databinding.ActivityMainBinding
import com.luxeva.reminder.model.DietItem
import com.luxeva.reminder.model.ScheduleItem
import com.luxeva.reminder.viewmodel.MainViewModel
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBinding = setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mViewModel.getData()
        mViewModel.isShowDialog.observe(this, Observer {
            dataBinding.swipe.isRefreshing = it
        })
        val dataList = ArrayList<DietItem>()
        val mAdapter = MealAdapter(dataList)
        dataBinding.mealAdapter = mAdapter
        mViewModel.data.observe(this, Observer {
            dataList.addAll(it)
            mAdapter.notifyDataSetChanged()
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR), 2)
            } else {
                //  addReminderInCalendar()
                val list = Helper.getDietDataWithScheduleTimestamp("2020-07-01", "2020-07-31", dataList)
                for(i in 0 until list.size){
                    addReminderInCalendar(list.get(i))
                }
            }
        })


    }

    /** Adds Events and Reminders in Calendar.  */
    private fun addReminderInCalendar(scheduleItem : ScheduleItem) {
        Log.d("data",scheduleItem.dietItem.day +" "+scheduleItem.date.toString())
        val cal = Calendar.getInstance()
        cal.time= scheduleItem.date
        val cr = contentResolver
        val timeZone = TimeZone.getDefault()
        var values = ContentValues()
        values.put(CalendarContract.Events.CALENDAR_ID, 1)
        values.put(CalendarContract.Events.TITLE,"Diet Plan :"+ scheduleItem.dietItem.itemData.food)
        values.put(CalendarContract.Events.DESCRIPTION,scheduleItem.dietItem.itemData.food)
        values.put(CalendarContract.Events.ALL_DAY, 0)
        values.put(CalendarContract.Events.DTSTART, cal.timeInMillis)
        values.put(CalendarContract.Events.DTEND, cal.timeInMillis + 20 * 60 * 1000)
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.id)
        values.put(CalendarContract.Events.HAS_ALARM, 1)
        val event: Uri = cr.insert(CalendarContract.Events.CONTENT_URI, values)!!
        values = ContentValues()
        values.put(Reminders.EVENT_ID, event.getLastPathSegment()?.toLong())
        values.put(Reminders.METHOD, Reminders.METHOD_ALERT)
        values.put(Reminders.MINUTES, 5)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        cr.insert(Reminders.CONTENT_URI, values)
    }
}
