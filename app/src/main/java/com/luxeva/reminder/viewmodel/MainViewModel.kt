package com.luxeva.reminder.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luxeva.reminder.model.DietItem
import com.luxeva.reminder.model.Reminder
import com.luxeva.reminder.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel() : ViewModel() {
    var isShowDialog: MutableLiveData<Boolean> = MutableLiveData()
    var data = MutableLiveData<List<DietItem>>()

    fun getData() {
        isShowDialog.value = true
        RetrofitClient.getInstance().getMealList()
            .enqueue(object : Callback<Reminder> {
                override fun onFailure(call: Call<Reminder>, t: Throwable) {
                    Log.d("error", "" + t.message)
                    isShowDialog.value = false
                }

                override fun onResponse(call: Call<Reminder>, response: Response<Reminder>) {
                    Log.d("response", response.body().toString())
                    isShowDialog.value = false
                    if (response.isSuccessful && response.body() != null) {
                        val dataList = ArrayList<DietItem>()
                        response.body().let {
                            if (response.body()!!.weekDietData != null && !response.body()!!.weekDietData!!.monday.isNullOrEmpty()) {
                                for(i in 0 until response.body()!!.weekDietData!!.monday!!.size){
                                    dataList.add(DietItem(response.body()!!.weekDietData!!.monday!!.get(i),"Monday"))
                                }
                            }
                            if (response.body()!!.weekDietData != null && !response.body()!!.weekDietData!!.wednesday.isNullOrEmpty()) {
                                for(i in 0 until response.body()!!.weekDietData!!.wednesday!!.size){
                                    dataList.add(DietItem(response.body()!!.weekDietData!!.wednesday!!.get(i),"Wednesday"))
                                }
                            }
                            if (response.body()!!.weekDietData != null && !response.body()!!.weekDietData!!.thursday.isNullOrEmpty()) {
                                for(i in 0 until response.body()!!.weekDietData!!.thursday!!.size){
                                    dataList.add(DietItem(response.body()!!.weekDietData!!.thursday!!.get(i),"Thursday"))
                                }
                            }
                            data.value =dataList
                        }
                    }
                }

            })
    }

}