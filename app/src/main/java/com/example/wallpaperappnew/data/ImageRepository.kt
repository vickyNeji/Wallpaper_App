package com.example.wallpaperappnew.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.liveData
import com.example.wallpaperappnew.api.Api
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepository@Inject constructor(private val api:Api) {

    fun getSearchResults(query:String)=
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {ImagePagingSource(api,query)}
        ).liveData

}