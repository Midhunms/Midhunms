package com.midhun.technical.repository

import com.midhun.technical.network.model.CreateUserRequestModel
import com.midhun.technical.network.model.base.ResponseBase
import com.midhun.technical.network.model.response.UserListResponseModel
import com.midhun.technical.network.source.UserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
        private val dataSource: UserDataSource,
) : UserRepository {
    override suspend fun getUserListResponse(pageNumber: Int): ResponseBase<ArrayList<UserListResponseModel>> {
        return withContext(Dispatchers.IO) {
            return@withContext dataSource.requestGetUserList(pageNumber)
        }
    }

    override suspend fun createUserResponse(createUser: CreateUserRequestModel): ResponseBase<UserListResponseModel> {
        return withContext(Dispatchers.IO) {
            return@withContext dataSource.requestCreateNewUser(createUser)
        }
    }

    override suspend fun deleteUserResponse(userId: Int): ResponseBase<UserListResponseModel> {
        return withContext(Dispatchers.IO) {
            return@withContext dataSource.requestDeleteUser(userId)
        }
    }

}