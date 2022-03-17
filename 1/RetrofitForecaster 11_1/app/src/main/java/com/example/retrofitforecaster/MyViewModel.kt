package com.example.retrofitforecaster

import android.app.Activity
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class   MyViewModel(state: SavedStateHandle): ViewModel() {
    var WEATHER_KEY = "weatherList"
    var weatherList: List<ListItem> = mutableListOf()
    var savedStateHandle = state
    var recAdapter = Adapter()

    var mService: RetrofitServices = Common.retrofitService


    fun getAllWeatherList(city: String, rec: RecyclerView){
        mService.getWeatherList(city).enqueue(object : Callback<DataWeather> {

            override fun onResponse(call: Call<DataWeather>, response: Response<DataWeather>)  {
                try {
                    val abc = response.body() as DataWeather
                    weatherList = abc.list
                    Log.d("123", abc.cod)
                    saveData(weatherList as ArrayList<ListItem>)

                    recAdapter.submitList(weatherList)
                    rec.adapter = recAdapter
                    recAdapter.notifyDataSetChanged()
                }catch(e: IOException){
                    Timber.e("Города нет")
                }
            }
            override fun onFailure(call: Call<DataWeather>, t: Throwable) {
                Log.d("123", t.message.toString())
            }
        })


    }


    fun saveData(savedList: ArrayList<ListItem>){
        weatherList = savedList
        savedStateHandle[WEATHER_KEY] = savedList
    }

    fun getData(): ArrayList<ListItem>{
        return savedStateHandle[WEATHER_KEY] ?: arrayListOf()
    }

    fun restoreData(): List<ListItem>{
        weatherList = getData()

        return weatherList
    }

}