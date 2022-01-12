package com.midhun.technical.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.midhun.technical.util.Utils.errorResponse
import com.midhun.technical.network.model.base.ResponseBase
import kotlin.coroutines.CoroutineContext

class ActionStateLiveData<T>(
        private val coroutineContext: CoroutineContext,
        fetchData: (suspend () -> ResponseBase<T>),
) {
    private val action = MutableLiveData<Action>()
    private var data: T? = null // backing data

    val state = action.switchMap {
        liveData(context = coroutineContext) {
            when (action.value) {
                Action.Load -> {
                    emit(UIState.Loading)
                }

                Action.SwipeRefresh -> {
                    emit(UIState.SwipeRefreshing)
                }

                Action.Retry -> {
                    emit(UIState.Retrying)
                }
            }

            try {
                val response = fetchData()
                if(response.data!=null){
                    data = response.data
                    emit(UIState.Success(response))
                }else{
                    emit(UIState.Failure(response))
                }

            } catch (exception: Exception) {
                exception.printStackTrace()
                when (action.value) {
                    Action.SwipeRefresh -> {
                        emit(UIState.SwipeRefreshFailure(Exception()))
                        data?.let {
                            // emit success with existing data
                            emit(UIState.Success(it))
                        }
                    }
                    else -> {
                        emit(UIState.Failure(data = errorResponse<T>(exception)))
                    }
                }
            }
        }
    }

    // Helpers for triggering different actions

    fun retry() {
        action.value = Action.Retry
    }

    fun swipeRefresh() {
        action.value = Action.SwipeRefresh
    }

    fun load() {
        action.value = Action.Load
    }
}