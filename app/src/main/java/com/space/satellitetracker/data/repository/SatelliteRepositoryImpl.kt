package com.space.satellitetracker.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.space.satellitetracker.util.Constants.Companion.SATELLITE_JSON_NAME
import com.space.satellitetracker.util.Resource
import com.space.satellitetracker.domain.model.Satellite
import com.space.satellitetracker.domain.repository.SatelliteRepository
import java.io.IOException
import java.lang.Exception

class SatelliteRepositoryImpl: SatelliteRepository {
    override suspend fun getSatelliteList(context: Context): Resource<List<Satellite>> {
        return try {
            val jsonString = context.assets.open(SATELLITE_JSON_NAME).bufferedReader().use {
                it.readText()
            }
            val result:List<Satellite> = Gson().fromJson(jsonString, object: TypeToken<List<Satellite>>() {}.type)
            Resource.Success(result)
        } catch (io: IOException) {
            Resource.Error(io.message)
        } catch (json: JsonSyntaxException) {
            Resource.Error(json.message)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }
}