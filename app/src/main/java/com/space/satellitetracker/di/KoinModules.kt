package com.space.satellitetracker.di

import com.space.satellitetracker.data.repository.SatelliteRepositoryImpl
import com.space.satellitetracker.domain.repository.SatelliteRepository
import com.space.satellitetracker.domain.use_case.GetSatelliteList
import com.space.satellitetracker.presentation.list.ListViewModel
import com.space.satellitetracker.presentation.list.SatelliteListAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // region Singletons
    single<SatelliteRepository> { SatelliteRepositoryImpl() }
    single { GetSatelliteList(get(), androidContext()) }
    single { SatelliteListAdapter() }

    // region Factories

    // region ViewModels
    viewModel { ListViewModel(get()) }
}