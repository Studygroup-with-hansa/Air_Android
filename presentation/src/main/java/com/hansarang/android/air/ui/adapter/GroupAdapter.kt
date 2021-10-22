package com.hansarang.android.air.ui.adapter

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.ItemGroupBinding
import com.hansarang.android.air.ui.viewmodel.fragment.GroupViewModel
import com.hansarang.android.domain.entity.dto.Group

class GroupAdapter(
    private val viewModel: GroupViewModel
): ListAdapter<Group, GroupAdapter.ViewHolder>(diffUtil) {

    interface OnClickGroup {
        fun onClick(code: String)
    }

    inner class ViewHolder(private val binding: ItemGroupBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(group: Group) = with(binding) {
            binding.group = group
            onClickGroup = object: OnClickGroup {
                override fun onClick(code: String) {
                    val bundle = Bundle()
                    bundle.putString("code", code)
                    itemView.findNavController().navigate(R.id.action_groupFragment_to_groupDetailFragment)
                }
            }
            with(itemView) {
                for (i in 1..group.userCount) {
                    if (i > 5) {
                        val textView = TextView(context)
                        textView.text = "+${group.userCount - 5}"
                        textView.setTextColor(
                            ResourcesCompat.getColor(
                                resources,
                                R.color.main,
                                resources.newTheme()
                            )
                        )
                        flbNumberDrawingItemGroup.addView(textView)
                        break
                    }
                    val imgView = ImageView(context)
                    imgView.setImageResource(R.drawable.ic_union)
                    flbNumberDrawingItemGroup.addView(imgView)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGroupBinding.inflate(inflater)
        binding.root.layoutParams = LinearLayout.LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Group>() {
            override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean {
                return oldItem == newItem
            }
        }
    }
}