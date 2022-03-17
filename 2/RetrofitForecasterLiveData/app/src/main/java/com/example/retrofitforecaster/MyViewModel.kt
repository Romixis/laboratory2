package com.example.retrofitforecaster

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class MyViewModel: ViewModel() {

    val mutWeatherList: MutableLiveData<List<ListItem>> = MutableLiveData()
    val weatherList: LiveData<List<ListItem>> get() = mutWeatherList
    var mService: RetrofitServices = Common.retrofitService

    init {
        getAllWeatherList("Kemerovo")
    }

    fun getAllWeatherList(city: String){
        mService.getWeatherList(city).enqueue(object : Callback<DataWeather> {
            override fun onResponse(call: Call<DataWeather>, response: Response<DataWeather>)  {
                try {
                    val abc = response.body() as DataWeather
                    mutWeatherList.value = abc.list
                    Log.d("123", abc.cod)

                }catch(e: IOException){
                    Timber.e("Города нет")
                }
            }
            override fun onFailure(call: Call<DataWeather>, t: Throwable) {
                Log.d("123", t.message.toString())
                mutWeatherList.value = emptyList()
            }
        })
    }
}