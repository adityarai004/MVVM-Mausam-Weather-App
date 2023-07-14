package com.example.mausam_weatherapp.ui.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.mausam_weatherapp.ui.models.AstroModel
import com.example.mausam_weatherapp.R

class AstroGVAdapter(context: Context, astroModelArrayList: List<AstroModel>) :
    ArrayAdapter<AstroModel?>(context, 0, astroModelArrayList as List<AstroModel?>) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var listitemView = convertView
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(context).inflate(R.layout.astro_grid_view, parent, false)
        }

        val astroModel: AstroModel? = getItem(position)
        val astroTimeTV = listitemView!!.findViewById<TextView>(R.id.astro_time_tv)
        val astroIV = listitemView.findViewById<ImageView>(R.id.astro_iv)
        val astroRiseTV = listitemView.findViewById<TextView>(R.id.rise_tv)
        if (astroModel != null) {
            astroTimeTV.text = astroModel.astro_time
            astroIV.setImageResource(astroModel.imgid)
            astroRiseTV.text = astroModel.rise
        }
        return listitemView
    }
}