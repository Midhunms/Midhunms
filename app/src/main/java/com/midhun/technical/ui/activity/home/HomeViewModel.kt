package com.midhun.technical.ui.activity.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midhun.technical.network.model.CreateUserRequestModel
import com.midhun.technical.repository.UserRepository
import com.midhun.technical.ui.base.ActionStateLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
        private val repository: UserRepository,
        private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        private const val PARAM_USER_NAME = "PARAM_USER_NAME"
        private const val PARAM_EMAIL = "PARAM_EMAIL"
        private const val PARAM_ID = "PARAM_ID"
    }

    val getCreateUserResponse = ActionStateLiveData(viewModelScope.coroutineContext + IO) {

        val mUserName = savedStateHandle.get<String>(PARAM_USER_NAME) ?: throw  Exception("Parameter <PARAM_USER_NAME> not set")
        val mEmail = savedStateHandle.get<String>(PARAM_EMAIL) ?: throw  Exception("Parameter <PARAM_EMAIL> not set")
        repository.createUserResponse(CreateUserRequestModel().apply {
            this.name = mUserName
            this.email = mEmail
            this.gender = "male"
            this.status = "active"
        })

    }

    val getDeleteUserResponse = ActionStateLiveData(viewModelScope.coroutineContext + IO) {

        val mUserId = savedStateHandle.get<Int>(PARAM_ID) ?: throw  Exception("Parameter <PARAM_ID> not set")
        repository.deleteUserResponse(mUserId)

    }

    fun processUserCreationRequest(userCreationRequest:CreateUserRequestModel) {
        savedStateHandle.set(PARAM_USER_NAME,userCreationRequest.name)
        savedStateHandle.set(PARAM_EMAIL,userCreationRequest.email)
        getCreateUserResponse.load()
    }
    fun processDeleteUserRequest(userId:Int) {
        savedStateHandle.set(PARAM_ID,userId)
        getDeleteUserResponse.load()
    }
}
