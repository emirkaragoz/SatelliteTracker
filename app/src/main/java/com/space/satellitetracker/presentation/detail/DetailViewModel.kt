package com.space.satellitetracker.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.space.satellitetracker.domain.model.Coordinate
import com.space.satellitetracker.domain.model.SatelliteDetail
import com.space.satellitetracker.domain.use_case.AddSatelliteDetailToCache
import com.space.satellitetracker.domain.use_case.GetPositionList
import com.space.satellitetracker.domain.use_case.GetSatelliteDetail
import com.space.satellitetracker.domain.use_case.GetSatelliteDetailFromCache
import com.space.satellitetracker.util.GenericUIState
import com.space.satellitetracker.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getSatelliteDetail: GetSatelliteDetail,
    private val getSatelliteDetailFromCache: GetSatelliteDetailFromCache,
    private val addSatelliteDetailToCache: AddSatelliteDetailToCache,
    private val getPositionList: GetPositionList
) : ViewModel() {

    private val _state = MutableStateFlow(GenericUIState<SatelliteDetail>())
    val state = _state.asStateFlow()

    private val _position = MutableStateFlow(Coordinate(0.0,0.0))
    val position = _position.asStateFlow()

    fun getDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(GenericUIState(isLoading = true))

            when(val cacheValue = getSatelliteDetailFromCache(id)) {
                is Resource.Success -> {
                    _state.emit(GenericUIState(isLoading = false, data = cacheValue.data))
                    handlePositions(cacheValue.data?.id.toString())
                    Log.d("cache","read from cache")
                }

                is Resource.Error -> {
                    when(val detail = getSatelliteDetail(id)) {
                        is Resource.Success -> {
                            detail.data?.also {
                                _state.emit(GenericUIState(isLoading = false, data = it))
                                addSatelliteDetailToCache(it)
                                handlePositions(detail.data.id.toString())
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

    private suspend fun handlePositions(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val positions = getPositionList()) {
                is Resource.Success -> {
                    val positionWithID = positions.data?.find {
                        it.id == id
                    }
                    while (true) {
                        positionWithID?.positions?.forEach {
                            _position.emit(it)
                            delay(3000)
                            Log.d("position update","emitted")
                        }
                    }
                }
                is Resource.Error -> {
                    //nothing to do
                    Log.d("position update","error")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}