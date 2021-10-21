package com.hansarang.android.air.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hansarang.android.domain.entity.dto.GroupRank

class GroupDetailViewModel: ViewModel() {

    private val _groupRankList = MutableLiveData<List<GroupRank>>()
    val groupRankList: LiveData<List<GroupRank>> = _groupRankList

    fun getRank() {
        _groupRankList.value = arrayListOf(
            GroupRank("서승우", "USER_EMAIL", "PROFILE_IMG", 4321, false, 1),
            GroupRank("박상아", "USER_EMAIL", "PROFILE_IMG", 3421, true, 2),
            GroupRank("이동주", "USER_EMAIL", "PROFILE_IMG", 2341, false, 3),
            GroupRank("양윤재", "USER_EMAIL", "PROFILE_IMG", 1324, false, 4),
            GroupRank("김부성", "USER_EMAIL", "PROFILE_IMG", 1234, false, 5),
        )
    }
}
