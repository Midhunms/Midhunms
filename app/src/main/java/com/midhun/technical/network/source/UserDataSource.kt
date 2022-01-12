package com.midhun.technical.network.source

import com.midhun.technical.network.model.CreateUserRequestModel
import com.midhun.technical.network.model.base.ResponseBase
import com.midhun.technical.network.model.response.UserListResponseModel

interface UserDataSource {

    suspend fun requestGetUserList(
        pageNumber: Int,
    ): ResponseBase<ArrayList<UserListResponseModel>>

    suspend fun requestCreateNewUser(
        createUser: CreateUserRequestModel,
    ): ResponseBase<UserListResponseModel>

    suspend fun requestDeleteUser(
        userId: Int,
    ): ResponseBase<UserListResponseModel>

}