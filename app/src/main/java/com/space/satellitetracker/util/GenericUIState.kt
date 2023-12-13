package com.space.satellitetracker.util

data class GenericUIState<T> (
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: String? = null
)
