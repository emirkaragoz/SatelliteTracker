package com.space.satellitetracker.domain.use_case

import android.content.Context
import com.space.satellitetracker.util.Resource
import com.space.satellitetracker.domain.model.Satellite
import com.space.satellitetracker.domain.repository.SatelliteRepository

class GetSatelliteList(private val repository: SatelliteRepository, private val context: Context) {
    suspend operator fun invoke(): Resource<List<Satellite>> {
        return repository.getSatelliteList(context)
    }
}