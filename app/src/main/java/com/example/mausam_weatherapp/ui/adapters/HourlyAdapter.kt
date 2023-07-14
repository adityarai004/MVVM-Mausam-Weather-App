package com.example.mausam_weatherapp.ui.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mausam_weatherapp.ui.models.HourlyItemModel
import com.example.mausam_weatherapp.R

class HourlyAdapter(val mContext: Context, private val mList: List<HourlyItemModel>) : RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hourly_temp_recycler_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hourlyViewModel = mList[position]
        val imgUrl = "https:" + hourlyViewModel.icon
        Glide.with(mContext).load(imgUrl).into(holder.iconIV)
        holder.tempTV.text = hourlyViewModel.tempC.toString()
        holder.timeTV.text = hourlyViewModel.time?.substring(11, 16)
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val iconIV: ImageView = itemView.findViewById(R.id.weather_icon_iv)
        val tempTV: TextView = itemView.findViewById(R.id.temp_tv)
        val timeTV: TextView = itemView.findViewById(R.id.time_tv)
    }
}