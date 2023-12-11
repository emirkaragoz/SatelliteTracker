package com.space.satellitetracker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.space.satellitetracker.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}