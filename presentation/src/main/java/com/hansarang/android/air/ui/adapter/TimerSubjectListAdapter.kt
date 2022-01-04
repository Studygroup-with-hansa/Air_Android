package com.hansarang.android.air.ui.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.PopupMenu
import androidx.activity.result.ActivityResultLauncher
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.ItemTimerSubjectBinding
import com.hansarang.android.air.ui.activity.AddSubjectActivity
import com.hansarang.android.air.ui.activity.TimerActivity
import com.hansarang.android.air.ui.viewmodel.fragment.HomeViewModel
import com.hansarang.android.domain.entity.dto.Subject

class TimerSubjectListAdapter(
    private val viewModel: HomeViewModel,
    private val activityResultLauncher: ActivityResultLauncher<Intent>
) : ListAdapter<Subject, TimerSubjectListAdapter.TimeSubjectListViewHolder>(diffUtil) {

    inner class TimeSubjectListViewHolder(
        private val binding: ItemTimerSubjectBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(subject: Subject) {

            subject.color = when (subject.color) {
                "#ffffff" -> "#8a8a8a"
                "#000000" -> "#404040"
                else -> subject.color
            }
            binding.subject = subject

            binding.tvTimePercentageSubject.text = if (viewModel.goal.value!! > 0) {
                "${((subject.time.toFloat() / (viewModel.goal.value!!.toFloat())) * 100).toInt()}% 달성"
            } else {
                "0% 달성"
            }

            binding.ibtOptionTimerSubject.setOnClickListener {
                val popupMenu = PopupMenu(binding.root.context, it).apply {
                    inflate(R.menu.menu_timer_subject_list)
                    setOnMenuItemClickListener { menuItem ->
                        when(menuItem.itemId) {
                            R.id.delete_subject_list -> {
                                viewModel.deleteSubject(subject)
                            }
                            R.id.modify_subject_list -> {
                                with(subject) {
                                    val intent = Intent(binding.root.context, AddSubjectActivity::class.java)
                                    intent.putExtra("btnText", "수정")
                                    intent.putExtra("oldTitle", title)
                                    intent.putExtra("oldColor", color)
                                    activityResultLauncher.launch(intent)
                                }
                            }
                        }
                        true
                    }
                }
                popupMenu.show()
            }
            binding.fabPlayTimerSubject.setOnClickListener {
                val intent = Intent(binding.root.context, TimerActivity::class.java)
                intent.putExtra("title", subject.title)
                intent.putExtra("time", subject.time)
                intent.putExtra("goal", viewModel.goal.value!!)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: TimeSubjectListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSubjectListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTimerSubjectBinding.inflate(inflater)
        binding.root.layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        return TimeSubjectListViewHolder(binding)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Subject>() {
            override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean {
                return oldItem == newItem
            }

        }
    }
}