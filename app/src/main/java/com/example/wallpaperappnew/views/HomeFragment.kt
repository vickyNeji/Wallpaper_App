package com.example.wallpaperappnew.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.paging.LoadState
import com.example.wallpaperappnew.R
import com.example.wallpaperappnew.adapters.ImageLoadStateAdapter
import com.example.wallpaperappnew.adapters.ImagePhotoAdapter
import com.example.wallpaperappnew.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment_layout.*

@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.home_fragment_layout) {

   private val viewModel by viewModels<HomeViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter=ImagePhotoAdapter()
        rvHome.adapter=adapter.withLoadStateFooter(ImageLoadStateAdapter { adapter.retry() })

        viewModel.photos.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }

        btnRetry.setOnClickListener {
        adapter.retry()
        }

        adapter.addLoadStateListener {loadState->
            progressBar.isVisible =loadState.source.refresh is LoadState.Loading
            rvHome.isVisible = loadState.source.refresh is LoadState.NotLoading
            btnRetry.isVisible =loadState.source.refresh is LoadState.Error
            tvError.isVisible =loadState.source.refresh is LoadState.Error

            if(loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached
                && adapter.itemCount<1){
                rvHome.isVisible=false
                tvNoResults.isVisible=true
            }else{
                tvNoResults.isVisible=false
            }

        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
        val searchItem=menu.findItem(R.id.search)
        val searchView=searchItem.actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object :androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    rvHome.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }


}