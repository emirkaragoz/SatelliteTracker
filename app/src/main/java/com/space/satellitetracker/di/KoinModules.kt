package com.space.satellitetracker.di

import android.content.Context
import androidx.room.Room
import com.space.satellitetracker.data.data_source.SatelliteDatabase
import com.space.satellitetracker.data.repository.SatelliteRepositoryImpl
import com.space.satellitetracker.domain.repository.SatelliteRepository
import com.space.satellitetracker.domain.use_case.AddSatelliteDetailToCache
import com.space.satellitetracker.domain.use_case.GetPositionList
import com.space.satellitetracker.domain.use_case.GetSatelliteDetail
import com.space.satellitetracker.domain.use_case.GetSatelliteDetailFromCache
import com.space.satellitetracker.domain.use_case.GetSatelliteList
import com.space.satellitetracker.presentation.detail.DetailViewModel
import com.space.satellitetracker.presentation.list.ListViewModel
import com.space.satellitetracker.presentation.list.SatelliteListAdapter
import com.space.satellitetracker.util.Constants.Companion.SATELLITE_DB_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // region Singletons
    single<SatelliteRepository> { SatelliteRepositoryImpl(get()) }
    single { GetSatelliteList(get(), androidContext()) }
    single { SatelliteListAdapter() }
    single { GetSatelliteDetail(get(), androidContext()) }
    single { GetSatelliteDetailFromCache(get()) }
    single { AddSatelliteDetailToCache(get()) }
    single { GetPositionList(get(),androidContext()) }
    single { provideDatabase(androidContext()) }
    single { provideDao(get()) }

    // region Factories

    // region ViewModels
    viewModel { ListViewModel(get()) }
    viewModel { DetailViewModel(get(),get(),get(),get()) }
}


fun provideDatabase(context: Context) =
    Room.databaseBuilder(context, SatelliteDatabase::class.java, SATELLITE_DB_NAME)
        .build()

fun provideDao(db: SatelliteDatabase) = db.getSatelliteDao()