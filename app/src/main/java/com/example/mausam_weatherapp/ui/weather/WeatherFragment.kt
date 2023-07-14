package com.example.mausam_weatherapp.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mausam_weatherapp.ui.adapters.AirQualityAdapter
import com.example.mausam_weatherapp.ui.adapters.AstroGVAdapter
import com.example.mausam_weatherapp.ui.models.AstroModel
import com.example.mausam_weatherapp.ui.adapters.ForecastAdapter
import com.example.mausam_weatherapp.ui.models.ForecastItemModel
import com.example.mausam_weatherapp.ui.adapters.HourlyAdapter
import com.example.mausam_weatherapp.ui.models.HourlyItemModel
import com.example.mausam_weatherapp.ui.models.PollutantViewModel
import com.example.mausam_weatherapp.R
import com.example.mausam_weatherapp.api.WeatherAPI
import com.example.mausam_weatherapp.ui.viewmodels.WeatherViewModelFactory
import com.example.mausam_weatherapp.data.WeatherRepository
import com.example.mausam_weatherapp.databinding.FragmentWeatherBinding
import com.example.mausam_weatherapp.ui.viewmodels.WeatherViewModel

class WeatherFragment : Fragment(R.layout.fragment_weather)  {
    private lateinit var viewModel: WeatherViewModel
    private val retroService = WeatherAPI.createRetrofitInstance()
    private val hourlyItems = mutableListOf<HourlyItemModel>()
    private val forecastItems = mutableListOf<ForecastItemModel>()
    private val astroList = mutableListOf<AstroModel>()
    private val astroList2 = mutableListOf<AstroModel>()
    private val pollutantList = mutableListOf<PollutantViewModel>()
    private lateinit var hourlyAdapter: HourlyAdapter
    private lateinit var forecastAdapter: ForecastAdapter
    private lateinit var airQualityAdapter: AirQualityAdapter
    private lateinit var binding: FragmentWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        val location = sp.getString("edit_text_preference_1","London")
        val metric = sp.getString("pref_key_units","Metric")
        viewModel = ViewModelProvider(
            requireActivity(),
            WeatherViewModelFactory(WeatherRepository(retroService!!))
        )[WeatherViewModel::class.java]


        if (location != null) {
            viewModel.getWeatherData(location)
        }
        viewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        })
        viewModel.result.observe(requireActivity(), Observer {
            val activity: FragmentActivity? = activity
            if(isAdded && activity !=null) {
                if(it == null){
                    Toast.makeText(requireContext(),"kuc toh bhi hoyega ",Toast.LENGTH_SHORT).show()
                }
                else{
                    if(metric == "Metric"){
                        setUpCurrentData()
                        setUpAstroGridView()
                        setUpForecastRecyclerView()
                        setUpHourlyRecyclerView()
                        setUpPollutantRecyclerView()
                    }
                    else{
                        setUpCurrentDataF()
                        setUpAstroGridView()
                        setUpForecastRecyclerViewF()
                        setUpHourlyRecyclerViewF()
                        setUpPollutantRecyclerView()
                    }
                }
            }
        })


    }
    private fun setUpCurrentData(){
        viewModel.result.observe(requireActivity(), Observer {
            val activity: FragmentActivity? = activity
            if (isAdded && activity != null) {
                binding.currentConditionTv.text = it.current.condition.text
                binding.currentLocationTv.text = "${it.location.name}, ${it.location.country}"
                binding.currentTempTv.text = it.current.temp_c.toString() + "℃"
                binding.currentFeelsLikeTv.text =
                    "${it.forecast.forecastday[0].day.maxtemp_c}℃ / ${it.forecast.forecastday[0].day.mintemp_c}℃ Feels like ${it.current.feelslike_c}℃"
            }
        })
    }
    private fun setUpHourlyRecyclerView(){
        viewModel.result.observe(requireActivity(), Observer {
            val activity: FragmentActivity? = activity
            if(isAdded && activity !=null) {
                hourlyItems.clear()
                for(i in 23 downTo 0){
                    hourlyItems.add(HourlyItemModel(it.forecast.forecastday[0].hour[i].time,it.forecast.forecastday[0].hour[i].temp_c,it.forecast.forecastday[0].hour[i].condition.icon))
                }
                binding.hourlyTempRv.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,true)
                hourlyAdapter = HourlyAdapter(requireContext(),hourlyItems)
                binding.hourlyTempRv.adapter = hourlyAdapter
            }
        })
    }
    private fun setUpForecastRecyclerView(){
        viewModel.result.observe(requireActivity(), Observer {
            val activity: FragmentActivity? = activity
            if(isAdded && activity !=null) {
                forecastItems.clear()
                for(i in 2 downTo 0){
                    forecastItems.add(
                        ForecastItemModel(it.forecast.forecastday[i].date,it.forecast.forecastday[i].day.daily_chance_of_rain.toDouble(),
                        it.forecast.forecastday[i].day.condition.icon,it.forecast.forecastday[i].day.maxtemp_c,it.forecast.forecastday[i].day.mintemp_c)
                    )
                }

                binding.forecastRv.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,true)
                forecastAdapter = ForecastAdapter(requireContext(),forecastItems)
                binding.forecastRv.adapter = forecastAdapter

            }
        })
    }
    private fun setUpAstroGridView(){
        viewModel.result.observe(requireActivity(), Observer {
            val activity: FragmentActivity? = activity
            if(isAdded && activity !=null) {
                astroList.clear()
                astroList2.clear()
                astroList.add(AstroModel(it.forecast.forecastday[0].astro.sunrise,R.drawable.sunrise,"Sunrise"))
                astroList.add(AstroModel(it.forecast.forecastday[0].astro.sunset,R.drawable.sunset,"Sunset"))
                astroList2.add(AstroModel(it.forecast.forecastday[0].astro.moonrise,R.drawable.moonrise,"Moonrise"))
                astroList2.add(AstroModel(it.forecast.forecastday[0].astro.moonset,R.drawable.moonset,"Moonset"))

                val astroAdapter = AstroGVAdapter(requireContext(),astroList)
                val astroAdapter2 = AstroGVAdapter(requireContext(),astroList2)
                binding.astroGv.adapter = astroAdapter
                binding.astroGv2.adapter = astroAdapter2
            }
        })
    }
    private fun setUpPollutantRecyclerView() {
        viewModel.result.observe(requireActivity(), Observer {
            val activity: FragmentActivity? = activity
            if(isAdded && activity !=null) {
                pollutantList.clear()
                pollutantList.add(PollutantViewModel("CO", it.current.air_quality.co))
                pollutantList.add(PollutantViewModel("NO2", it.current.air_quality.no2))
                pollutantList.add(PollutantViewModel("O3", it.current.air_quality.o3))
                pollutantList.add(PollutantViewModel("SO2", it.current.air_quality.so2))
                pollutantList.add(PollutantViewModel("PM2.5", it.current.air_quality.pm2_5))
                pollutantList.add(PollutantViewModel("PM10", it.current.air_quality.pm10))
                pollutantList.add(
                    PollutantViewModel(
                        "Quality Index",
                        it.current.air_quality.us_epa_index.toDouble()
                    )
                )

                binding.airQualityRv.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
                airQualityAdapter = AirQualityAdapter(requireContext(), pollutantList)
                binding.airQualityRv.adapter = airQualityAdapter
            }
        })
    }

    private fun setUpHourlyRecyclerViewF(){
        viewModel.result.observe(requireActivity(), Observer {
            val activity: FragmentActivity? = activity
            if(isAdded && activity !=null) {
                hourlyItems.clear()
                for(i in 23 downTo 0){
                    hourlyItems.add(HourlyItemModel(it.forecast.forecastday[0].hour[i].time,it.forecast.forecastday[0].hour[i].temp_f,it.forecast.forecastday[0].hour[i].condition.icon))
                }
                binding.hourlyTempRv.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,true)
                hourlyAdapter = HourlyAdapter(requireContext(),hourlyItems)
                binding.hourlyTempRv.adapter = hourlyAdapter
            }
        })
    }
    private fun setUpForecastRecyclerViewF(){
        viewModel.result.observe(requireActivity(), Observer {
            val activity: FragmentActivity? = activity
            if(isAdded && activity !=null) {
                forecastItems.clear()
                for(i in 2 downTo 0){
                    forecastItems.add(
                        ForecastItemModel(it.forecast.forecastday[i].date,it.forecast.forecastday[i].day.daily_chance_of_rain.toDouble(),
                        it.forecast.forecastday[i].day.condition.icon,it.forecast.forecastday[i].day.maxtemp_f,it.forecast.forecastday[i].day.mintemp_f)
                    )
                }

                binding.forecastRv.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,true)
                forecastAdapter = ForecastAdapter(requireContext(),forecastItems)
                binding.forecastRv.adapter = forecastAdapter

            }
        })
    }
    private fun setUpCurrentDataF(){
        viewModel.result.observe(requireActivity(), Observer {
            val activity: FragmentActivity? = activity
            if (isAdded && activity != null) {
                binding.currentLocationTv.text = it.location.name
                binding.currentTempTv.text = it.current.temp_f.toString() + "℉"
                binding.currentConditionTv.text = it.current.condition.text
                binding.currentFeelsLikeTv.text =
                    "${it.forecast.forecastday[0].day.maxtemp_f}℉ / ${it.forecast.forecastday[0].day.mintemp_f}℉ Feels like ${it.current.feelslike_f}℉"
            }
        })
    }
}