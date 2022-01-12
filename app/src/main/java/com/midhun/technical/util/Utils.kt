package com.midhun.technical.util

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.midhun.technical.R
import com.midhun.technical.network.model.base.ResponseBase

object Utils {

    fun <T> errorResponse(e: Exception): ResponseBase<T> {
        return ResponseBase<T>().apply {
            errorMessage =  e.stackTrace.toString()
            data = null
            meta = null
        }
    }
    fun <T> generateSuccessResponse(response: T): ResponseBase<T> {
        return ResponseBase<T>().apply {
            message = response.toString()
            data = null
            meta = null
        }
    }
    fun showSnackBar(
        context: Context,
        message: String?,
        @BaseTransientBottomBar.Duration
        duration: Int,
    ) {
        try {
            val view: View = (context as Activity).findViewById(android.R.id.content)
            val snackBar = Snackbar.make(view, message.toString(), duration)
            val snackBarView = snackBar.view
            val params = snackBarView.layoutParams as (FrameLayout.LayoutParams)
            params.gravity = Gravity.BOTTOM
            params.width = FrameLayout.LayoutParams.MATCH_PARENT
            snackBarView.layoutParams = params
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                context,
                R.color.colorPrimary
            ))
            val textView =
                snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            val actionView =
                snackBarView.findViewById(com.google.android.material.R.id.snackbar_action) as TextView
            textView.maxLines = 2
            textView.setTextColor(Color.WHITE)
            snackBar.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}