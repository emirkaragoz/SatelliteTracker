package com.space.satellitetracker.domain.model

data class Position(
    val id: String,
    val positions: List<Coordinate>
)