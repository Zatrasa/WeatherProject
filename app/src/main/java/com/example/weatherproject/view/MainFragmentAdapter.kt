package com.example.weatherproject.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherproject.R
import com.example.weatherproject.model.Weather

class MainFragmentAdapter(private var onItemViewClickListener: MainFragment.OnItemViewClickListener?)
    : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var weatherData : List<Weather> = listOf()

    fun setWeather(data : List<Weather>){
        this.weatherData = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item, parent, false) as View
        )

    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int = weatherData.size

    fun removeListener() {
        onItemViewClickListener = null
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(weather: Weather) {
            itemView.findViewById<TextView>(R.id.cityNameText_item).setText(weather.city.city)
            itemView.findViewById<TextView>(R.id.cityTemp_item).setText("Температура ${weather.temperature}")
            itemView.findViewById<TextView>(R.id.cityImgTemp_item).setText("Ощущается как ${weather.feelsLike}")
            itemView.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(weather)
            }
        }
    }

}