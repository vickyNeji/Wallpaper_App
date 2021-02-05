package com.example.wallpaperappnew.api

import com.example.wallpaperappnew.data.ImageResponse
import com.example.wallpaperappnew.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET(".")
    suspend fun searchPhotos(@Query("key")key:String=API_KEY,
                             @Query("q")query:String,
                             @Query("page") page:Int,
                            @Query("per_page")perPage:Int):ImageResponse

}