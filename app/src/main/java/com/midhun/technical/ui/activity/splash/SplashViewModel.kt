package com.midhun.technical.ui.activity.splash

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midhun.technical.repository.UserRepository
import com.midhun.technical.ui.base.ActionStateLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
        private val repository: UserRepository,
        private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        private const val PARAM_PAGE_NUMBER = "PAGE_NUMBER"
    }

    val getUserListResponse = ActionStateLiveData(viewModelScope.coroutineContext + IO) {

        val mPageNumber = savedStateHandle.get<Int>(PARAM_PAGE_NUMBER) ?:
                throw  Exception("Parameter <PARAM_PAGE_NUMBER> not set")
        repository.getUserListResponse(mPageNumber)

    }

    fun processUserListRequest(pageNumber:Int) {
        savedStateHandle.set(PARAM_PAGE_NUMBER,pageNumber)
        getUserListResponse.load()
    }
}
