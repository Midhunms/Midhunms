package com.midhun.technical.repository

import com.midhun.technical.network.model.CreateUserRequestModel
import com.midhun.technical.network.model.base.ResponseBase
import com.midhun.technical.network.model.response.UserListResponseModel

interface UserRepository {
    suspend fun getUserListResponse(pageNumber: Int ): ResponseBase<ArrayList<UserListResponseModel>>
    suspend fun createUserResponse(createUser:CreateUserRequestModel): ResponseBase<UserListResponseModel>
    suspend fun deleteUserResponse(userId:Int): ResponseBase<String>
}