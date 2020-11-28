package com.dc.pokapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dc.pokapp.R
import com.dc.pokapp.viewModel.AppViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val appViewModel: AppViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appViewModel.getCurrentState()
    }
}