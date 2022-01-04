package com.hansarang.android.air.ui.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.os.bundleOf
import androidx.core.view.updateLayoutParams
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.FragmentGroupDetailBinding
import com.hansarang.android.air.di.assistedfactory.GroupDetailAssistedFactory
import com.hansarang.android.air.ui.adapter.GroupRankAdapter
import com.hansarang.android.air.ui.base.BaseFragment
import com.hansarang.android.air.ui.dialog.DialogAlertFragment
import com.hansarang.android.air.ui.livedata.EventObserver
import com.hansarang.android.air.ui.rvhelper.SwipeHelperCallback
import com.hansarang.android.air.ui.viewmodel.fragment.GroupDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GroupDetailFragment : BaseFragment<FragmentGroupDetailBinding, GroupDetailViewModel>() {

    companion object {
        const val id = "GroupDetail"
    }

    private val groupRankAdapter = GroupRankAdapter()

    @Inject lateinit var assistedFactory: GroupDetailAssistedFactory

    override val viewModel: GroupDetailViewModel by viewModels {
        val groupCode = requireArguments().getString("groupCode") ?: ""
        val leader = requireArguments().getString("leader") ?: ""
        val leaderEmail = requireArguments().getString("leaderEmail") ?: ""
        GroupDetailViewModel.provideFactory(assistedFactory, groupCode, leader, leaderEmail)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRank()
    }

    override fun observerViewModel() {
        binding.tvGroupCodeDetail.text = viewModel.groupCode
        binding.tvTitleGroupDetail.text = resources.getString(R.string.group_name, viewModel.leader)
        binding.rvRankGroupDetail.adapter = groupRankAdapter

        viewModel.backButtonClick.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        viewModel.groupExitButtonClick.observe(viewLifecycleOwner) {
            val alert = DialogAlertFragment.newInstance("알림", "그룹을 나가시겠습니까?", "취소", "나가기")
            alert.show(parentFragmentManager, "alert")
            alert.setOnNegativeButtonClickListener {
                viewModel.leaveGroup()
            }
        }

        viewModel.deleteGroupButtonClick.observe(viewLifecycleOwner) {
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

        viewModel.copyCodeButtonLongClick.observe(viewLifecycleOwner) {
            val clipBoardManager = context?.getSystemService(ClipboardManager::class.java)
            val clipData = ClipData.newPlainText("그룹 코드", binding.tvGroupCodeDetail.text)
            clipBoardManager?.setPrimaryClip(clipData)
            Toast.makeText(context, "그룹 코드가 복사되었습니다.", Toast.LENGTH_SHORT).show()
        }

        viewModel.groupRankList.observe(viewLifecycleOwner) {
            groupRankAdapter.submitList(it)
        }

        viewModel.isFailure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.sflGroupDetail.startShimmer()
            } else {
                binding.sflGroupDetail.stopShimmer()
                binding.srlGroupDetail.isRefreshing = false
                binding.nestedScrollViewGroupDetail.fullScroll(NestedScrollView.FOCUS_UP)
            }
        }

        viewModel.isGroupLeader.observe(viewLifecycleOwner) {
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

        viewModel.isLeaveSuccess.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
            setFragmentResult(GroupDetailFragment.id, bundleOf("data" to "Reload"))
        }
    }

}