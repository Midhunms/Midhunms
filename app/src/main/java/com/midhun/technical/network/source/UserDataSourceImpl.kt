package com.midhun.technical.network.source

import com.midhun.technical.util.Utils.errorResponse
import com.midhun.technical.network.ApiService
import com.midhun.technical.network.model.CreateUserRequestModel
import com.midhun.technical.network.model.base.ResponseBase
import com.midhun.technical.network.model.response.UserListResponseModel
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    UserDataSource {

    override suspend fun requestGetUserList(pageNumber: Int): ResponseBase<ArrayList<UserListResponseModel>> {
        return try {
            apiService
                .requestGetAllUsersAsync(pageNumber = pageNumber)
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
            errorResponse(e)
        } catch (e: Exception) {
            errorResponse(e)
        }
    }

    override suspend fun requestCreateNewUser(createUser: CreateUserRequestModel): ResponseBase<UserListResponseModel> {
        return try {
            apiService
                .requestCreateNewUserAsync(createUser = createUser)
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
            errorResponse(e)
        } catch (e: Exception) {
            errorResponse(e)
        }
    }

    override suspend fun requestDeleteUser(userId: Int): ResponseBase<String> {
        return try {
            apiService
                .requestDeleteUserAsync(userId)
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
            errorResponse(e)
        } catch (e: Exception) {
            errorResponse(e)
        }
    }

}