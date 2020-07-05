package com.luxeva.reminder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.luxeva.reminder.databinding.ItemMealBinding
import com.luxeva.reminder.model.DietItem
import java.util.*

class MealAdapter(val dataList: ArrayList<DietItem>) : RecyclerView.Adapter<MealAdapter.ViewHolder>() {
    class ViewHolder(view: ItemMealBinding) : RecyclerView.ViewHolder(view.root) {
         var dataBinding: ItemMealBinding

        init {
           dataBinding =view
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate<ItemMealBinding>(LayoutInflater.from(parent.context),R.layout.item_meal,parent,false))
    }

    override fun getItemCount(): Int  = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBinding.item =dataList.get(position)
    }

}
