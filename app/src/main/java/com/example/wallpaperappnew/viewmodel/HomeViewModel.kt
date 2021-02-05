package com.example.wallpaperappnew.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.wallpaperappnew.data.ImageRepository
import com.example.wallpaperappnew.utils.Constants.DEFAULT_QUERY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ImageRepository) :ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    //val photos=repository.getSearchResults("cats")
    val photos=currentQuery.switchMap {
        repository.getSearchResults(it).cachedIn(viewModelScope)
    }

    fun searchPhotos(query:String){
        currentQuery.value=query
    }

}