package com.hansarang.android.domain.repository

import com.hansarang.android.domain.entity.dto.BaseGroup
import com.hansarang.android.domain.entity.dto.BaseGroupDetail
import com.hansarang.android.domain.entity.dto.GroupCode

interface GroupRepository {
    suspend fun getGroupList(): BaseGroup
    suspend fun postCreateUserGroup(): GroupCode
    suspend fun postViewGroupDetail(groupCode: String): BaseGroupDetail
    suspend fun putJoinGroup(groupCode: String): GroupCode
    suspend fun deleteGroupUser(userMail: String, groupCode: String): GroupCode
}