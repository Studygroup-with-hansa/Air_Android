package com.hansarang.android.air.ui.fragment

import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import com.hansarang.android.air.databinding.FragmentGroupBinding
import com.hansarang.android.air.ui.adapter.GroupAdapter
import com.hansarang.android.air.ui.base.BaseFragment
import com.hansarang.android.air.ui.decorator.ItemDividerDecorator
import com.hansarang.android.air.ui.dialog.DialogAlertFragment
import com.hansarang.android.air.ui.dialog.JoinGroupDialogFragment
import com.hansarang.android.air.ui.extention.dp
import com.hansarang.android.air.ui.livedata.EventObserver
import com.hansarang.android.air.ui.viewmodel.fragment.GroupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupFragment : BaseFragment<FragmentGroupBinding, GroupViewModel>() {
    private var isOpened: Boolean = false
    private val groupAdapter = GroupAdapter()

    override val viewModel: GroupViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()

        if (viewModel.isFirstLoad) {
            viewModel.groupList()
        }
    }

    override fun observerViewModel() {
        binding.rvGroupList.adapter = groupAdapter
        binding.rvGroupList.addItemDecoration(ItemDividerDecorator(5.dp))

        binding.expandableFabAddGroup.setOnClickListener {
            if(!isOpened) openFAB() else closeFAB()
        }
        binding.viewFabBackground.setOnClickListener {
            closeFAB()
        }
        setFragmentResultListener(GroupDetailFragment.id) { _, bundle ->
            if (bundle.getString("data") == "Reload") {
                viewModel.groupList()
            }
        }

        viewModel.createGroupButtonClick.observe(viewLifecycleOwner) {
            val alert = DialogAlertFragment.newInstance(
                "알림",
                "그룹을 추가하시겠습니까?",
                "확인",
                "취소"
            )
            alert.show(parentFragmentManager, "alert")
            alert.setOnPositiveButtonClickListener {
                viewModel.postGroup()
                viewModel.groupList()
                closeFAB()
            }
            alert.setOnNegativeButtonClickListener {
                closeFAB()
            }
        }

        viewModel.joinGroupButtonClick.observe(viewLifecycleOwner) {
            val joinGroupDialogFragment = JoinGroupDialogFragment.newInstance()
            joinGroupDialogFragment.show(parentFragmentManager, "joinGroupDialogFragment")
            joinGroupDialogFragment.setOnDismissDialogListener {
                if (it.isNotEmpty()) {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.groupList()
                }
                closeFAB()
            }
        }

        viewModel.groupList.observe(viewLifecycleOwner) {
            groupAdapter.submitList(it)
        }

        viewModel.isFailure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.sflRvGroupList.startShimmer()
            } else {
                binding.sflRvGroupList.stopShimmer()
                binding.srlGroup.isRefreshing = false
                binding.nestedScrollViewGroup.fullScroll(NestedScrollView.FOCUS_UP)
            }
        }
    }

    private fun openFAB() = with(binding) {
        isOpened = !isOpened
        ViewCompat.animate(expandableFabAddGroup)
            .rotation(45.0f)
            .withLayer()
            .setDuration(300)
            .setListener(object : ViewPropertyAnimatorListener {
                override fun onAnimationStart(view: View?) {
                    tvFabJoinGroupTitleAddGroup.visibility = View.VISIBLE
                    tvFabCreateGroupTitleAddGroup.visibility = View.VISIBLE
                }
                override fun onAnimationEnd(view: View?) {}
                override fun onAnimationCancel(view: View?) {}
            })
            .setInterpolator(OvershootInterpolator(10.0f))
            .start()

        ViewCompat.animate(linearLayoutCreateGroupAddGroup)
            .alpha(1f)
            .translationY(-180f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(5.0f))
            .start()

        ViewCompat.animate(linearLayoutJoinGroupAddGroup)
            .alpha(1f)
            .translationY(-360f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(5.0f))
            .start()

        ViewCompat.animate(viewFabBackground)
            .alpha(0.3f)
            .withLayer()
            .setDuration(300)
            .setListener(object: ViewPropertyAnimatorListener {
                override fun onAnimationStart(view: View?) { viewFabBackground.visibility = View.VISIBLE }
                override fun onAnimationEnd(view: View?) {}
                override fun onAnimationCancel(view: View?) {}
            })
            .start()

    }
    private fun closeFAB() = with(binding) {
        isOpened = !isOpened
        ViewCompat.animate(expandableFabAddGroup)
            .rotation(0f)
            .withLayer()
            .setDuration(300)
            .setListener(object : ViewPropertyAnimatorListener {
                override fun onAnimationStart(view: View?) {}
                override fun onAnimationEnd(view: View?) {}
                override fun onAnimationCancel(view: View?) {
                    tvFabJoinGroupTitleAddGroup.visibility = View.INVISIBLE
                    tvFabCreateGroupTitleAddGroup.visibility = View.INVISIBLE
                }
            })
            .setInterpolator(OvershootInterpolator(10.0f))
            .start()

        ViewCompat.animate(linearLayoutCreateGroupAddGroup)
            .alpha(0f)
            .translationY(0f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(0f))
            .start()

        ViewCompat.animate(linearLayoutJoinGroupAddGroup)
            .alpha(0f)
            .translationY(0f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(0f))
            .start()

        ViewCompat.animate(viewFabBackground)
            .alpha(0f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(0f))
            .setListener(object: ViewPropertyAnimatorListener {
                override fun onAnimationStart(view: View?) {

                }
                override fun onAnimationCancel(view: View?) {
                }
                override fun onAnimationEnd(view: View?) { viewFabBackground.visibility = View.INVISIBLE }
            })
            .start()
    }
}