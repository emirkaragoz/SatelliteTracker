package com.space.satellitetracker.domain.repository

import android.content.Context
import com.space.satellitetracker.domain.model.Position
import com.space.satellitetracker.util.Resource
import com.space.satellitetracker.domain.model.Satellite
import com.space.satellitetracker.domain.model.SatelliteDetail

interface SatelliteRepository {
    suspend fun getSatelliteList(context: Context): Resource<List<Satellite>>
    suspend fun getSatelliteDetail(context: Context, id: Int): Resource<SatelliteDetail>
    suspend fun addSatellite(satellite: SatelliteDetail)
    suspend fun getSatelliteFromCache(id: Int): Resource<SatelliteDetail>
    suspend fun getPositionList(context: Context): Resource<List<Position>>
}