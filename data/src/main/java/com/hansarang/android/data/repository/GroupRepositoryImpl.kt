package com.hansarang.android.data.repository

import com.hansarang.android.data.datasource.GroupDataSource
import com.hansarang.android.domain.entity.dto.BaseGroup
import com.hansarang.android.domain.entity.dto.BaseGroupDetail
import com.hansarang.android.domain.entity.dto.GroupCode
import com.hansarang.android.domain.repository.GroupRepository
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val groupDataSource: GroupDataSource
): GroupRepository {
    override suspend fun getGroupList(): BaseGroup {
        return groupDataSource.getGroupList()
    }

    override suspend fun postCreateUserGroup(): GroupCode {
        return groupDataSource.postCreateUserGroup()
    }

    override suspend fun postViewGroupDetail(groupCode: String): BaseGroupDetail {
        return groupDataSource.postViewGroupDetail(groupCode)
    }

    override suspend fun putJoinGroup(groupCode: String): GroupCode {
        return groupDataSource.putJoinGroup(groupCode)
    }

    override suspend fun deleteGroupUser(userMail: String, groupCode: String): GroupCode {
        return groupDataSource.deleteGroupUser(userMail, groupCode)
    }
}
