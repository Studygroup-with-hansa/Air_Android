package com.hansarang.android.air.ui.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.os.bundleOf
import androidx.core.view.updateLayoutParams
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.hansarang.android.air.databinding.FragmentGroupDetailBinding
import com.hansarang.android.air.ui.adapter.GroupRankAdapter
import com.hansarang.android.air.ui.dialog.DialogAlertFragment
import com.hansarang.android.air.ui.livedata.EventObserver
import com.hansarang.android.air.ui.rvhelper.SwipeHelperCallback
import com.hansarang.android.air.ui.viewmodel.fragment.GroupDetailViewModel
import com.hansarang.android.air.ui.viewmodel.fragment.GroupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupDetailFragment : Fragment() {

    companion object {
        const val id = "GroupDetail"
    }

    private lateinit var binding: FragmentGroupDetailBinding
    private val viewModel: GroupDetailViewModel by viewModels()
    private lateinit var groupRankAdapter: GroupRankAdapter
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupDetailBinding.inflate(inflater)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRank()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observe()
        listener()
        with(viewModel) {
            getIsGroupLeader()
            groupCode.value = requireArguments().getString("groupCode") ?: ""
            leader.value = requireArguments().getString("leader") ?: ""
            leaderEmail.value = requireArguments().getString("leaderEmail") ?: ""
        }
    }

    private fun listener() = with(binding) {
        linearLayoutGroupCodeDetail.setOnLongClickListener {
            val clipBoardManager = context?.getSystemService(ClipboardManager::class.java)
            val clipData = ClipData.newPlainText("그룹 코드", tvGroupCodeDetail.text)
            clipBoardManager?.setPrimaryClip(clipData)
            Toast.makeText(context, "그룹 코드가 복사되었습니다.", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }

        btnExitGroup.setOnClickListener {
            val alert = DialogAlertFragment.newInstance("알림", "그룹을 나가시겠습니까?", "취소", "나가기")
            alert.show(parentFragmentManager, "alert")
            alert.setOnNegativeButtonClickListener {
                viewModel.leaveGroup()
            }
        }

        btnGroupDeleteDetail.setOnClickListener {
            val alert = DialogAlertFragment.newInstance("알림", "그룹을 삭제하시겠습니까?", "취소", "확인")
            alert.show(parentFragmentManager, "alert")
            alert.setOnNegativeButtonClickListener {
                viewModel.deleteGroup()
            }
        }

        groupRankAdapter.setOnDeleteUserButtonClickListener {
            val alert = DialogAlertFragment.newInstance("알림", "그룹원을 내보내시겠습니까?", "취소", "확인")
            alert.show(parentFragmentManager, "alert")
            alert.setOnNegativeButtonClickListener {
                viewModel.leaveUserGroup(it)
            }
        }
    }

    private fun observe() = with(viewModel) {
        groupRankList.observe(viewLifecycleOwner) {
            groupRankAdapter.submitList(it)
        }
        isFailure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
        isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.sflGroupDetail.startShimmer()
            } else {
                binding.sflGroupDetail.stopShimmer()
                binding.srlGroupDetail.isRefreshing = false
                binding.nestedScrollViewGroupDetail.fullScroll(NestedScrollView.FOCUS_UP)
            }
        }
        isGroupLeader.observe(viewLifecycleOwner) {
            with(binding.linearLayoutGroupCodeDetail) {
                updateLayoutParams<ConstraintLayout.LayoutParams> {
                    topToTop = ConstraintSet.PARENT_ID
                    bottomToBottom = ConstraintSet.PARENT_ID
                    startToStart = ConstraintSet.PARENT_ID
                    if (!it) {
                        endToEnd = ConstraintSet.PARENT_ID
                    }
                    else {
                        endToEnd = ConstraintSet.UNSET
                        with(binding) {
                            val swipeHelperCallback =
                                SwipeHelperCallback()
                                    .apply {
                                        setClamp(200f)
                                    }
                            val itemTouchHelper =
                                androidx.recyclerview.widget.ItemTouchHelper(swipeHelperCallback)
                            itemTouchHelper.attachToRecyclerView(rvRankGroupDetail)
                        }
                    }
                }
            }
        }
        isLeaveSuccess.observe(viewLifecycleOwner) {
            navController.navigateUp()
            setFragmentResult(GroupDetailFragment.id, bundleOf("data" to "Reload"))
        }
    }

    private fun init() = with(binding) {
        groupRankAdapter = GroupRankAdapter()
        rvRankGroupDetail.adapter = groupRankAdapter
        toolbarGroupDetail.setNavigationOnClickListener {
            navController.navigateUp()
        }
        srlGroupDetail.setOnRefreshListener {
            viewModel.getRank()
        }
    }

}