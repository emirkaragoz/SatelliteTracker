package com.space.satellitetracker.domain.use_case

import com.space.satellitetracker.domain.model.SatelliteDetail
import com.space.satellitetracker.domain.repository.SatelliteRepository
import com.space.satellitetracker.util.Resource

class GetSatelliteDetailFromCache(
    private val repository: SatelliteRepository
) {
    suspend operator fun invoke(id: Int): Resource<SatelliteDetail> {
        return repository.getSatelliteFromCache(id)
    }
}