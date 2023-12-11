package com.space.satellitetracker.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.space.satellitetracker.databinding.FragmentDetailBinding

class DetailFragment: Fragment() {

    private lateinit var viewBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentDetailBinding.inflate(inflater,container,false)
        return viewBinding.root
    }
}