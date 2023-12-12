package com.space.satellitetracker.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.space.satellitetracker.util.Resource
import com.space.satellitetracker.domain.use_case.GetSatelliteList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListViewModel(private val getSatelliteList: GetSatelliteList): ViewModel() {

    private val _state = MutableStateFlow(SatelliteListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = SatelliteListState(isLoading = true)

            delay(2000) //to simulate long process and help to see loading indicator

            when(val list = getSatelliteList.invoke()) {
                is Resource.Success -> {
                    _state.value = SatelliteListState(isLoading = false, satellites = list.data)
                }
                is Resource.Error -> {
                    _state.value = SatelliteListState(isLoading = false, error = list.errorType)
                }
            }
        }
    }
}