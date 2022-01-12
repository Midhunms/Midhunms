package com.midhun.technical.ui.activity.home

import com.midhun.technical.network.ApiService
import com.midhun.technical.network.model.CreateUserRequestModel
import com.midhun.technical.network.model.base.ResponseBase
import com.midhun.technical.network.model.response.UserListResponseModel
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @Mock
    lateinit var apiService: ApiService

    @Test
    fun processUserCreationRequest() {
        apiService = Mockito.mock(ApiService::class.java)
        runBlocking {
            Mockito.`when`(
                apiService.requestCreateNewUserAsync(
                    CreateUserRequestModel().apply {
                        this.status = "active"
                        this.name = "Your Name"
                        this.email = "Email"
                        this.gender = "male"

                    }
                )

            ).thenReturn(
                async {
                    ResponseBase<UserListResponseModel>().apply {
                        this.data   = UserListResponseModel(1000,"Your Name","Email","male","active")
                    }
                }

            )
        }
    }

    @Test
    fun processDeleteUserRequest() {
    }
}