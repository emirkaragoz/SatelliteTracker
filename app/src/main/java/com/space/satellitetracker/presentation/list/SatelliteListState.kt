package com.space.satellitetracker.presentation.list

import com.space.satellitetracker.domain.model.Satellite

data class SatelliteListState (
    val isLoading: Boolean = false,
    val satellites: List<Satellite>? = null,
    val error: String? = null
)
