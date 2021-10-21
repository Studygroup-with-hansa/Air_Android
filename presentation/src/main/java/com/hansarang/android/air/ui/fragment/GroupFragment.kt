package com.hansarang.android.air.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.FragmentGroupBinding
import com.hansarang.android.air.ui.adapter.GroupAdapter
import com.hansarang.android.air.ui.decorator.ItemDividerDecorator
import com.hansarang.android.air.ui.extention.dp
import com.hansarang.android.air.ui.viewmodel.fragment.GroupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupFragment : Fragment() {

    private lateinit var binding: FragmentGroupBinding
    private var isOpened: Boolean = false
    private lateinit var groupAdapter: GroupAdapter
    private val viewModel: GroupViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupBinding.inflate(inflater)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.groupList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        listener()
        observe()
    }

    private fun init() = with(binding) {
        groupAdapter = GroupAdapter(viewModel)
        rvGroupList.adapter = groupAdapter
        rvGroupList.addItemDecoration(ItemDividerDecorator(5.dp))
    }

    private fun observe() = with(viewModel) {
        groupList.observe(viewLifecycleOwner) {
            groupAdapter.submitList(it)
        }
        groupCode.observe(viewLifecycleOwner) {
            val bundle = Bundle()
            bundle.putString("code", it)
            navController.navigate(R.id.action_groupFragment_to_groupDetailFragment2)
        }
    }

    private fun listener() = with(binding) {
        expandableFabAddGroup.setOnClickListener {
            if(!isOpened) openFAB() else closeFAB()
            isOpened = !isOpened
        }
        viewFabBackground.setOnClickListener {
            closeFAB()
            isOpened = false
        }
    }

    private fun openFAB() = with(binding) {
        tvFabJoinGroupTitleAddGroup.visibility = View.VISIBLE
        tvFabCreateGroupTitleAddGroup.visibility = View.VISIBLE
        expandableFabAddGroup.animate()
            .rotation(45.0f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(10.0f))
            .start()

        fabCreateGroupAddGroup.animate()
            .translationY(-180f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(5.0f))
            .start()

        ViewCompat.animate(tvFabCreateGroupTitleAddGroup)
            .alpha(1f)
            .withLayer()
            .setDuration(300)
            .setListener(object: ViewPropertyAnimatorListener {
                override fun onAnimationStart(view: View?) { tvFabCreateGroupTitleAddGroup.visibility = View.VISIBLE }
                override fun onAnimationEnd(view: View?) {}
                override fun onAnimationCancel(view: View?) {
                }
            })
            .start()

        fabJoinGroupAddGroup.animate()
            .translationY(-360f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(5.0f))
            .start()

        ViewCompat.animate(tvFabJoinGroupTitleAddGroup)
            .alpha(1f)
            .withLayer()
            .setDuration(300)
            .setListener(object: ViewPropertyAnimatorListener {
                override fun onAnimationStart(view: View?) { tvFabJoinGroupTitleAddGroup.visibility = View.VISIBLE }
                override fun onAnimationEnd(view: View?) {}
                override fun onAnimationCancel(view: View?) {
                }
            })
            .start()

        ViewCompat.animate(viewFabBackground)
            .alpha(0.3f)
            .withLayer()
            .setDuration(300)
            .setListener(object: ViewPropertyAnimatorListener {
                override fun onAnimationStart(view: View?) { viewFabBackground.visibility = View.VISIBLE }
                override fun onAnimationEnd(view: View?) {
                    with(fabCreateGroupAddGroup) {
                        isClickable = true

                    }
                    fabJoinGroupAddGroup.isClickable = true
                }
                override fun onAnimationCancel(view: View?) {
                }
            })
            .start()

    }
    private fun closeFAB() = with(binding) {
        expandableFabAddGroup.animate()
            .rotation(0f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(10.0f))
            .start()

        fabCreateGroupAddGroup.animate()
            .translationY(0f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(0f))
            .start()

        ViewCompat.animate(tvFabCreateGroupTitleAddGroup)
            .alpha(0f)
            .withLayer()
            .setDuration(300)
            .setListener(object: ViewPropertyAnimatorListener {
                override fun onAnimationEnd(view: View?) { tvFabCreateGroupTitleAddGroup.visibility = View.GONE }
                override fun onAnimationStart(view: View?) {

                }
                override fun onAnimationCancel(view: View?) {
                }
            })
            .start()

        fabJoinGroupAddGroup.animate()
            .translationY(0f)
            .withLayer()
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(0f))
            .start()

        ViewCompat.animate(tvFabJoinGroupTitleAddGroup)
            .alpha(0f)
            .withLayer()
            .setDuration(300)
            .setListener(object: ViewPropertyAnimatorListener {
                override fun onAnimationEnd(view: View?) { tvFabJoinGroupTitleAddGroup.visibility = View.GONE }
                override fun onAnimationStart(view: View?) {

                }
                override fun onAnimationCancel(view: View?) {
                }
            })
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
                override fun onAnimationEnd(view: View?) {
                    viewFabBackground.visibility = View.GONE
                    tvFabJoinGroupTitleAddGroup.visibility = View.GONE
                    tvFabCreateGroupTitleAddGroup.visibility = View.GONE
                }
            })
            .start()
    }
}