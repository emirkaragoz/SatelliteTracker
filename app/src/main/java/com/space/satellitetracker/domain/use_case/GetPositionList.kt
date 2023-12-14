package com.space.satellitetracker.domain.use_case

import android.content.Context
import com.space.satellitetracker.domain.model.Position
import com.space.satellitetracker.domain.repository.SatelliteRepository
import com.space.satellitetracker.util.Resource

class GetPositionList(private val repository: SatelliteRepository, private val context: Context) {
    suspend operator fun invoke(): Resource<List<Position>> {
        return repository.getPositionList(context)
    }
}