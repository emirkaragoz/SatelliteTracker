package com.space.satellitetracker.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.space.satellitetracker.domain.model.SatelliteDetail
import com.space.satellitetracker.domain.use_case.AddSatelliteDetailToCache
import com.space.satellitetracker.domain.use_case.GetSatelliteDetail
import com.space.satellitetracker.domain.use_case.GetSatelliteDetailFromCache
import com.space.satellitetracker.util.GenericUIState
import com.space.satellitetracker.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getSatelliteDetail: GetSatelliteDetail,
    private val getSatelliteDetailFromCache: GetSatelliteDetailFromCache,
    private val addSatelliteDetailToCache: AddSatelliteDetailToCache
) : ViewModel() {

    private val _state = MutableStateFlow(GenericUIState<SatelliteDetail>())
    val state = _state.asStateFlow()

    fun getDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(GenericUIState(isLoading = true))

            when(val cacheValue = getSatelliteDetailFromCache(id)) {
                is Resource.Success -> {
                    _state.emit(GenericUIState(isLoading = false, data = cacheValue.data))
                    Log.d("cache","read from cache")
                }

                is Resource.Error -> {
                    when(val detail = getSatelliteDetail(id)) {
                        is Resource.Success -> {
                            detail.data?.also {
                                _state.emit(GenericUIState(isLoading = false, data = it))
                                addSatelliteDetailToCache(it)
                                Log.d("cache","read from json")
                            }
                        }
                        is Resource.Error -> {
                            _state.emit(GenericUIState(isLoading = false, error = detail.errorType))
                        }
                    }
                }
            }
        }
    }
}