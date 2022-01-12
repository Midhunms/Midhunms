package com.midhun.technical.ui.base

sealed class Action {
    object Load : Action()
    object SwipeRefresh : Action()
    object Retry : Action()
}