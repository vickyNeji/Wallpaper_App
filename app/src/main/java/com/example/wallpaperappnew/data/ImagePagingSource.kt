package com.example.wallpaperappnew.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wallpaperappnew.api.Api
import com.example.wallpaperappnew.utils.Constants.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class ImagePagingSource(
    private val api: Api,
    private val searchQuery:String
):PagingSource<Int,Hit>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hit> {
        val position=params.key?:STARTING_PAGE_INDEX
       return try {

            val response = api.searchPhotos(query = searchQuery, page = position, perPage = 20)
            val photos = response.hits
           LoadResult.Page(
               data =photos,
                prevKey = if(position== STARTING_PAGE_INDEX) null else position-1,
               nextKey = if(photos.isEmpty()) null else position+1
           )
        }catch (exception:IOException){
            LoadResult.Error(exception)
       }catch (exception:HttpException){
            LoadResult.Error(exception)
       }
    }

    override fun getRefreshKey(state: PagingState<Int, Hit>): Int? {
        return state.anchorPosition
    }


}