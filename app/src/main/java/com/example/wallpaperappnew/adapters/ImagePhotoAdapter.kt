package com.example.wallpaperappnew.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.wallpaperappnew.R
import com.example.wallpaperappnew.data.Hit
import com.example.wallpaperappnew.databinding.RecyclerviewSingleItemBinding

class ImagePhotoAdapter:PagingDataAdapter<Hit,ImagePhotoAdapter.DataHolder>(PHoTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val binding=RecyclerviewSingleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DataHolder(binding)

    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        val currentItem=getItem(position)
        currentItem?.let { holder.bind(it) }
    }

    class DataHolder(private val binding: RecyclerviewSingleItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(photo:Hit){
            binding.apply {
                Glide.with(ivSingleImage)
                    .load(photo.webformatURL)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.placeholder)
                    .into(ivSingleImage)

                tvUserName.text=photo.user

            }
        }
    }

    companion object{
        private val PHoTO_COMPARATOR= object :DiffUtil.ItemCallback<Hit>(){
            override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean {
                return oldItem==newItem
            }

        }
    }



}