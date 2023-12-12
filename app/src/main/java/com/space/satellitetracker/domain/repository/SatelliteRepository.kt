package com.space.satellitetracker.domain.repository

import android.content.Context
import com.space.satellitetracker.util.Resource
import com.space.satellitetracker.domain.model.Satellite

interface SatelliteRepository {
    suspend fun getSatelliteList(context: Context): Resource<List<Satellite>>
}