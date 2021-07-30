package com.example.android_3.ui.listmovie

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_3.R
import com.example.android_3.databinding.ItemLoadBinding
import com.example.android_3.databinding.ItemmovielistBinding
import com.example.android_3.model.viewitem.MovieItem
import kotlinx.android.extensions.LayoutContainer

class ListPageApdater :
    PagedListAdapter<MovieItem, ListPageApdater.CustomHolder<*>>(MovieDiffCallBack) {


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemCount > 1 && itemCount == position +1) {ItemType.TYPE_LOAD.viewType()}
        else return ItemType.TYPE_MOVIE.viewType()
    }

    override fun getItemCount(): Int {
        return if (super.getItemCount() != 0) super.getItemCount() + 1 else 0
    }

    companion object {
        val MovieDiffCallBack = object : DiffUtil.ItemCallback<MovieItem>() {
            override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
                return oldItem == newItem
            }

        }

    }


    abstract class CustomHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer {
        abstract fun bind(data: T)
        protected val context: Context = itemView.context
        protected val resources: Resources = itemView.resources
        override val containerView: View?
            get() = itemView

    }

    class MovieHolder(
        val binding: ItemmovielistBinding,
        val itemclick: ((MovieItem) -> Unit)?
    ) :
        CustomHolder<MovieItem?>(binding.root) {
        override fun bind(data: MovieItem?) {
            if (data == null) return

            binding.movie = data
            binding.root.setOnClickListener { itemclick?.invoke(data) }
            binding.executePendingBindings()
        }
    }

    class LoadHolder(val binding: ItemLoadBinding) : CustomHolder<Unit>(binding.root) {
        override fun bind(data: Unit) {
        }
    }

    override fun onBindViewHolder(holder: CustomHolder<*>, position: Int) {
        when(holder){
            is MovieHolder ->holder.bind(getItem(position))
        }
    }

    enum class ItemType {
        TYPE_MOVIE {
            override fun OnCreateViewHolder(
                viewGroup: ViewGroup,
                layoutInflater: LayoutInflater,
                onMovieItemClick: ((MovieItem) -> Unit)?
            ): CustomHolder<*> {
                val binding = DataBindingUtil.inflate<ItemmovielistBinding>(
                    layoutInflater,
                    R.layout.itemmovielist,
                    viewGroup,
                    false
                )
                return MovieHolder(binding, onMovieItemClick)
            }
        },
        TYPE_LOAD{
            override fun OnCreateViewHolder(
                viewGroup: ViewGroup,
                layoutInflater: LayoutInflater,
                onMovieItemClick: ((MovieItem) -> Unit)?
            ): CustomHolder<*> {
                val binding = DataBindingUtil.inflate<ItemLoadBinding>(
                    layoutInflater,
                    R.layout.item_load,
                    viewGroup,
                    false
                )
                return LoadHolder(binding)
            }
        };
        abstract fun OnCreateViewHolder(
            viewGroup: ViewGroup,
            layoutInflater: LayoutInflater,
            onMovieItemClick: ((MovieItem) -> Unit)? = null
        ): CustomHolder<*>

        fun viewType(): Int = ordinal
    }
    var onMovieItemClick: ((MovieItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder<*> {
        val layoutInflater = LayoutInflater.from(parent.context)
       return ItemType.values()[viewType].OnCreateViewHolder(parent,layoutInflater,onMovieItemClick)
    }


    val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (getItemViewType(position) == ItemType.TYPE_LOAD.viewType()) {
                4
            } else {
                1
            }
        }
    }




}