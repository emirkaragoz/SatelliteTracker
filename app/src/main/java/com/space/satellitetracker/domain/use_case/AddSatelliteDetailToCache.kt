package com.space.satellitetracker.domain.use_case

import com.space.satellitetracker.domain.model.SatelliteDetail
import com.space.satellitetracker.domain.repository.SatelliteRepository

class AddSatelliteDetailToCache(private val repository: SatelliteRepository) {
    suspend operator fun invoke(satellite: SatelliteDetail) {
        return repository.addSatellite(satellite)
    }
}