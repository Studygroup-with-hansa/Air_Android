package com.hansarang.android.air.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hansarang.android.air.databinding.ItemRankBinding
import com.hansarang.android.air.databinding.ItemRankHeaderBinding
import com.hansarang.android.domain.entity.dto.GroupRank

class GroupRankAdapter: ListAdapter<GroupRank, RecyclerView.ViewHolder>(diffUtil) {

    interface OnDeleteUserButtonClickListener {
        fun onClick(groupRank: GroupRank)
    }

    private var onDeleteUserButtonClickListener: OnDeleteUserButtonClickListener =
        object : OnDeleteUserButtonClickListener {
            override fun onClick(groupRank: GroupRank) {}
        }

    fun setOnDeleteUserButtonClickListener(onClick: (GroupRank) -> Unit) {
        onDeleteUserButtonClickListener = object : OnDeleteUserButtonClickListener {
            override fun onClick(groupRank: GroupRank) {
                onClick.invoke(groupRank)
            }
        }
    }

    inner class HeaderViewHolder(
        binding: ItemRankHeaderBinding
    ): RecyclerView.ViewHolder(binding.root)

    inner class ItemViewHolder(
        private val binding: ItemRankBinding
    ): RecyclerView.ViewHolder(binding.root) {
        val rankItem = binding.constraintLayoutRankItem
        fun bind(groupRank: GroupRank) {
            binding.groupRank = groupRank
            binding.btnDeleteUserRank.setOnClickListener {
                onDeleteUserButtonClickListener.onClick(groupRank)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            HEADER
        } else {
            ITEM
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HEADER) {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemRankHeaderBinding.inflate(inflater)
            binding.root.layoutParams = ViewGroup.LayoutParams(
                MATCH_PARENT,
                WRAP_CONTENT
            )
            HeaderViewHolder(binding)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemRankBinding.inflate(inflater)
            binding.root.layoutParams = ViewGroup.LayoutParams(
                MATCH_PARENT,
                WRAP_CONTENT
            )
            ItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bind(getItem(position - 1))
        }
    }

    companion object {
        private const val HEADER = 0
        private const val ITEM = 1

        val diffUtil = object: DiffUtil.ItemCallback<GroupRank>() {
            override fun areItemsTheSame(oldItem: GroupRank, newItem: GroupRank): Boolean {
                return oldItem.email.hashCode() == newItem.email.hashCode()
            }

            override fun areContentsTheSame(oldItem: GroupRank, newItem: GroupRank): Boolean {
                return oldItem == newItem
            }

        }
    }
}