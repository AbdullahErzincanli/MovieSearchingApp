package com.example.sinemalar.service

import com.weatherapp.models.OmdbApiData
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Query

interface OmdbService {
    @GET("?")
    fun getFilm(
        @Query("t") t: String,
        @Query("apikey") apikey: String
    ): Call<OmdbApiData>
}