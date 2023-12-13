package com.space.satellitetracker.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.space.satellitetracker.domain.model.SatelliteDetail

@Database(entities = [SatelliteDetail::class], version = 1)
abstract class SatelliteDatabase: RoomDatabase() {
    abstract fun getSatelliteDao(): SatelliteDao
}