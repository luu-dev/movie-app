package com.example.basemodule.base.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.basemodule.base.models.BaseModel

open class BaseBindingAdapter<T : BaseModel?>(
    private val inflater: LayoutInflater,
    private val resId: Int,
    private val brItem: Int
) : RecyclerView.Adapter<BaseBindingAdapter.BaseBindingHolder>() {
    private var data: List<T>? = null
    private var listener: BaseBindingItemListener? = null
    private var brListener = 0
    fun setData(data: List<T>?) {
        this.data = data
        notifyDataSetChanged()
    }

    fun getData(): List<T>? {
        return data
    }

    fun setListener(listener: BaseBindingItemListener?, brListener: Int) {
        this.listener = listener
        this.brListener = brListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            inflater,
            resId,
            parent,
            false
        )
        return BaseBindingHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseBindingHolder, position: Int) {
        val item = data!![position]
        holder.binding.setVariable(brItem, item)
        if (listener != null) {
            holder.binding.setVariable(brListener, listener)
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (data == null) 0 else data!!.size
    }

    open class BaseBindingHolder(binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        val binding: ViewDataBinding

        init {
            this.binding = binding
        }
    }

    interface BaseBindingItemListener


}