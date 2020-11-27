package com.dc.pokapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dc.pokapp.model.Page
import com.dc.pokapp.model.Pokemon
import com.dc.pokapp.source.network.Api
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    val api: Api by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        api.getPokemonPage(0, 5).enqueue(object : Callback<Page<Pokemon>> {
            override fun onFailure(call: Call<Page<Pokemon>>, t: Throwable) {
                val asd = 1
            }

            override fun onResponse(call: Call<Page<Pokemon>>, response: Response<Page<Pokemon>>) {
                val asd = 1
            }
        })
    }
}