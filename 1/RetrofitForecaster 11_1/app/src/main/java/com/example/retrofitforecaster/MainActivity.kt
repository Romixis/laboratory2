package com.example.retrofitforecaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.IOException


class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    val recAdapter = Adapter()
    val viewModel: MyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.rView)
        with(recyclerView){
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )

            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recAdapter
        }

        viewModel.restoreData()
        if(viewModel.weatherList.isNullOrEmpty()){
            viewModel.getAllWeatherList("Kemerovo", recyclerView)
        }
        recAdapter.submitList(viewModel.weatherList)
    }



}
