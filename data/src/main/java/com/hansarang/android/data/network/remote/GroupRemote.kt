package com.hansarang.android.data.network.remote

import com.hansarang.android.data.base.BaseRemote
import com.hansarang.android.data.entity.BaseGroupData
import com.hansarang.android.data.entity.BaseGroupDetailData
import com.hansarang.android.data.entity.GroupCodeData
import com.hansarang.android.data.network.service.GroupService
import javax.inject.Inject

class GroupRemote @Inject constructor(
    override val service: GroupService
): BaseRemote<GroupService>() {
    suspend fun getGroupList(): BaseGroupData {
        return getResponse(service.getGroupList())
    }

    suspend fun postCreateUserGroup(): GroupCodeData {
        return getResponse(service.postCreateUserGroup())
    }

    suspend fun postViewGroupDetail(groupCode: String): BaseGroupDetailData {
        return getResponse(service.postViewGroupDetail(groupCode))
    }

    suspend fun putJoinGroup(groupCode: String): GroupCodeData {
        return getResponse(service.putJoinGroup(groupCode))
    }

    suspend fun deleteGroup(): String {
        return getDetail(service.deleteGroup())
    }

    suspend fun deleteLeaveGroup(groupCode: String): String {
        return getDetail(service.deleteLeaveGroup(groupCode))
    }

    suspend fun deleteGroupUser(
        userMail: String,
        groupCode: String
    ): GroupCodeData {
        return getResponse(service.deleteGroupUser(userMail, groupCode))
    }
}