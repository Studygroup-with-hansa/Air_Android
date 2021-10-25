package com.hansarang.android.data.datasource

import com.hansarang.android.data.base.BaseDataSource
import com.hansarang.android.data.mapper.toEntity
import com.hansarang.android.data.network.remote.GroupRemote
import com.hansarang.android.domain.entity.dto.BaseGroup
import com.hansarang.android.domain.entity.dto.BaseGroupDetail
import com.hansarang.android.domain.entity.dto.GroupCode
import javax.inject.Inject

class GroupDataSource @Inject constructor(
    override val remote: GroupRemote
): BaseDataSource<GroupRemote>() {
    suspend fun getGroupList(): BaseGroup {
        return remote.getGroupList().toEntity()
    }

    suspend fun postCreateUserGroup(): GroupCode {
        return remote.postCreateUserGroup().toEntity()
    }

    suspend fun postViewGroupDetail(groupCode: String): BaseGroupDetail {
        return remote.postViewGroupDetail(groupCode).toEntity()
    }

    suspend fun putJoinGroup(groupCode: String): GroupCode {
        return remote.putJoinGroup(groupCode).toEntity()
    }

    suspend fun deleteGroupUser(
        userMail: String,
        groupCode: String
    ): GroupCode {
        return remote.deleteGroupUser(userMail, groupCode).toEntity()
    }
}