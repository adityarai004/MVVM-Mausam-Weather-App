package com.example.mausam_weatherapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mausam_weatherapp.ui.models.PollutantViewModel
import com.example.mausam_weatherapp.R
import java.math.BigDecimal
import java.math.RoundingMode

class AirQualityAdapter(val context : Context,val pollutantArrayList: List<PollutantViewModel>):RecyclerView.Adapter<AirQualityAdapter.ViewHolder>(){

    class ViewHolder(ItemView : View): RecyclerView.ViewHolder(ItemView) {
        val pollutantTV: TextView = itemView.findViewById(R.id.pollutant_tv)
        val quantityTV: TextView = itemView.findViewById(R.id.quantity_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.air_quality_recycler_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pollutantArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pollutantTV.text = pollutantArrayList[position].pollutant
        val quality = BigDecimal(pollutantArrayList[position].quantity).setScale(2,RoundingMode.HALF_EVEN)
        if(holder.pollutantTV.text == "Quality Index"){
            if(quality.toDouble() == 1.00){
                holder.quantityTV.text = "${(quality).toString()} (Good)"
            }
            else if(quality.toDouble() == 2.00) {
                holder.quantityTV.text = "${(quality).toString()} (Moderate)"
            }

            else if(quality.toDouble() == 3.00) {
                holder.quantityTV.text = "${(quality).toString()} (Unhealthy for sensitive group)"
            }

            else if(quality.toDouble() == 4.00) {
                holder.quantityTV.text = "${(quality).toString()} (Unhealthy) "
            }

            else if(quality.toDouble() == 5.00) {
                holder.quantityTV.text = "${(quality).toString()} (Very Unhealthy)"
            }
            else{
                holder.quantityTV.text = "${(quality).toString()} (Hazardous)"
            }
        }
        else{
            holder.quantityTV.text = (quality).toString() +"µ/m3"
        }
    }
}