package com.midhun.technical.ui.activity.splash

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.midhun.technical.R
import com.midhun.technical.network.model.base.ResponseBase
import com.midhun.technical.network.model.response.UserListResponseModel
import com.midhun.technical.ui.activity.home.HomeActivity
import com.midhun.technical.ui.base.ScopedActivity
import com.midhun.technical.ui.base.UIState
import com.midhun.technical.util.ActivityUtils
import com.midhun.technical.util.BundleArguments
import com.midhun.technical.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreenActivity : ScopedActivity() {

    private val viewModel: SplashViewModel by viewModels()

    private var userArrayList:ArrayList<UserListResponseModel> = arrayListOf()
    private var isLastPage:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        requestApiCall(1)

    }

    private fun requestApiCall(pageNumber: Int) {
        viewModel.processUserListRequest(pageNumber)
    }

    override fun bindUI() = launch(Dispatchers.Main) {

        viewModel.getUserListResponse.state.observe(this@SplashScreenActivity) { state ->
            when (state) {
                is UIState.Loading -> { // Show Progress

                }
                is UIState.Retrying -> { // Show Retry Progress

                }
                is UIState.Failure -> { // On Failure
                    Utils.showSnackBar(this@SplashScreenActivity,"Something wrong happened", Snackbar.LENGTH_LONG)
                }
                is UIState.Success -> { // On Success

                    val mData = (state.data as ResponseBase<ArrayList<UserListResponseModel>>)
                    if(!isLastPage){
                        viewModel.processUserListRequest(mData.meta?.pagination?.pages?:1)
                        isLastPage = true
                    }else if(mData.data?.isNotEmpty() == true){

                        userArrayList.clear()
                        mData.data?.let {
                            userArrayList.addAll(it)
                        }
                        pageNavigation()

                    }else{
                        viewModel.processUserListRequest(mData.meta?.pagination?.pages?.minus(1) ?:1)
                    }
                    Log.e("","")
                }
                else -> {
                    Log.e("Critical", "Unexpected UIState received.")
                }
            }

        }
    }

    private fun pageNavigation() {
        val bundle = Bundle()
        bundle.putParcelableArrayList(BundleArguments.ARG_USER_LIST,userArrayList)
        ActivityUtils.startActivity(this@SplashScreenActivity,HomeActivity::class.java,bundle,true)

    }
}