package com.example.mausam_weatherapp.ui.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mausam_weatherapp.ui.models.ForecastItemModel
import com.example.mausam_weatherapp.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

class ForecastAdapter(val mContext: Context, private val mList: List<ForecastItemModel>) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.forecast_recycler_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hourlyViewModel = mList[position]
        val imgUrl = "https:" + hourlyViewModel.icon
        Glide.with(mContext).load(imgUrl).into(holder.iconIV)

        holder.highTV.text = hourlyViewModel.high.toString()+"°"
        holder.lowTV.text = hourlyViewModel.low.toString()+"°"
        holder.precipTV.text = hourlyViewModel.precip.toString()+"%"

        holder.dayTV.text = hourlyViewModel.day?.let { formatDateToDay(it) }
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val dayTV: TextView = itemView.findViewById(R.id.day_tv)
        val precipTV: TextView = itemView.findViewById(R.id.precip_tv)
        val iconIV: ImageView = itemView.findViewById(R.id.condition_iv)
        val highTV: TextView = itemView.findViewById(R.id.high_tv)
        val lowTV: TextView = itemView.findViewById(R.id.low_tv)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateToDay(dateString: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
        val date = LocalDate.parse(dateString, formatter)
        val dayOfWeek = date.dayOfWeek
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    }

}