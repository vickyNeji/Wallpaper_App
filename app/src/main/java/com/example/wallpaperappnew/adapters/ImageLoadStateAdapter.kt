package com.example.wallpaperappnew.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperappnew.databinding.ImageLoadsstateFooterBinding

class ImageLoadStateAdapter(private val retry:()->Unit) :LoadStateAdapter<ImageLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
    val binding=ImageLoadsstateFooterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }



    inner class LoadStateViewHolder(private val binding: ImageLoadsstateFooterBinding):RecyclerView.ViewHolder(binding.root){

        init {
            binding.btnRetryFooter.setOnClickListener {
            retry.invoke()
            }

        }

        fun bind(loadState: LoadState){
        binding.apply {
            progressFooter.isVisible =loadState is LoadState.Loading
            btnRetryFooter.isVisible =loadState !is LoadState.Loading
            textViewError.isVisible = loadState !is LoadState.Loading
        }
        }
    }
}