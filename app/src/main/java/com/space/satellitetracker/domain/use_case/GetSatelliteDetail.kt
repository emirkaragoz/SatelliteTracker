package com.space.satellitetracker.domain.use_case

import android.content.Context
import com.space.satellitetracker.domain.model.SatelliteDetail
import com.space.satellitetracker.domain.repository.SatelliteRepository
import com.space.satellitetracker.util.Resource

class GetSatelliteDetail(
    private val repository: SatelliteRepository,
    private val context: Context
) {
    suspend operator fun invoke(id: Int): Resource<SatelliteDetail> {
        return repository.getSatelliteDetail(context,id)
    }
}