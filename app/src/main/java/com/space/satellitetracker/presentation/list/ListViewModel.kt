package com.space.satellitetracker.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.space.satellitetracker.domain.model.Satellite
import com.space.satellitetracker.util.Resource
import com.space.satellitetracker.domain.use_case.GetSatelliteList
import com.space.satellitetracker.util.GenericUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class ListViewModel(private val getSatelliteList: GetSatelliteList): ViewModel() {

    private val _searchText = MutableStateFlow("")

    private val _state = MutableStateFlow(GenericUIState<List<Satellite>>())
    val state = _searchText
        .debounce(1000)
        .combine(_state) { text, state ->
            if (text.isBlank()) {
                state
            } else {
                GenericUIState(data = state.data?.filter {
                    it.name.contains(text, ignoreCase = true)
                })
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            GenericUIState(isLoading = true)
        )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(GenericUIState(isLoading = true))

            //delay(2000) //to simulate long process and help to see loading indicator

            when(val list = getSatelliteList()) {
                is Resource.Success -> {
                    _state.emit(GenericUIState(isLoading = false, data = list.data))
                }
                is Resource.Error -> {
                    _state.emit(GenericUIState(isLoading = false, error = list.errorType))
                }
            }
        }
    }

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }
}