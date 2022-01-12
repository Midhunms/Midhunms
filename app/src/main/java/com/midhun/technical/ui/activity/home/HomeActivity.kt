package com.midhun.technical.ui.activity.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kaopiz.kprogresshud.KProgressHUD
import com.midhun.technical.R
import com.midhun.technical.adapter.UserListAdapter
import com.midhun.technical.network.model.CreateUserRequestModel
import com.midhun.technical.network.model.MSDialogModel
import com.midhun.technical.network.model.base.ResponseBase
import com.midhun.technical.network.model.response.UserListResponseModel
import com.midhun.technical.ui.base.ScopedActivity
import com.midhun.technical.ui.base.UIState
import com.midhun.technical.ui.fragment.dialog.MSDialogFragment
import com.midhun.technical.ui.fragment.dialog.UserCreationDialogFragment
import com.midhun.technical.util.BundleArguments
import com.midhun.technical.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.include_toolbar_basic.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : ScopedActivity(), View.OnClickListener, UserListAdapter.Listener,MSDialogFragment.ItemListener,UserCreationDialogFragment.ItemListener {

    private var userArrayList:ArrayList<UserListResponseModel> = arrayListOf()
    private var mAdapter: UserListAdapter? = null
    private var userCreationRequest: CreateUserRequestModel ? =null
    private val viewModel:HomeViewModel by viewModels()
    private var mProgressHud: KProgressHUD? = null
    private var mSelectedUser: UserListResponseModel ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        intent?.getParcelableArrayListExtra<UserListResponseModel>(BundleArguments.ARG_USER_LIST)?.let {
            userArrayList.addAll(it)
        }
        toolbarTitleWithText?.text = getString(R.string.user_list)

        displayUserList()

        toolbarLeftOne?.setOnClickListener(this)

        mProgressHud = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel(getString(R.string.creating))
            .setAutoDismiss(false)
            .setCancellable(false)

    }

    override fun bindUI() = launch(Dispatchers.Main) {

        viewModel.getCreateUserResponse.state.observe(this@HomeActivity) { state ->
            when (state) {
                is UIState.Loading -> { // Show Progress
                    if(mProgressHud?.isShowing==false){
                        mProgressHud?.show()
                    }
                }
                is UIState.Retrying -> { // Show Retry Progress
                    if(mProgressHud?.isShowing==false){
                        mProgressHud?.show()
                    }
                }
                is UIState.Failure -> { // On Failure

                    Utils.showSnackBar(this@HomeActivity,"Something wrong with data",Snackbar.LENGTH_LONG)
                    if(mProgressHud?.isShowing==true){
                        mProgressHud?.dismiss()
                    }
                }
                is UIState.Success -> { // On Success
                    if(mProgressHud?.isShowing==true){
                        mProgressHud?.dismiss()
                    }
                    Utils.showSnackBar(this@HomeActivity,"User Created Successfully",Snackbar.LENGTH_LONG)
                    val mData = (state.data as ResponseBase<UserListResponseModel>)
                    if(mData!=null){
                        mData.data?.let { userArrayList.add(it) }
                        mAdapter?.notifyDataSetChanged()
                    }

                }
                else -> {
                    Log.e("Critical", "Unexpected UIState received.")
                }
            }

        }
        viewModel.getDeleteUserResponse.state.observe(this@HomeActivity) { state ->
            when (state) {
                is UIState.Loading -> { // Show Progress
                    if(mProgressHud?.isShowing==false){
                        mProgressHud?.show()
                    }
                }
                is UIState.Retrying -> { // Show Retry Progress
                    if(mProgressHud?.isShowing==false){
                        mProgressHud?.show()
                    }
                }
                is UIState.Failure -> { // On Failure

                    Utils.showSnackBar(this@HomeActivity,"Something wrong with data",Snackbar.LENGTH_LONG)
                    if(mProgressHud?.isShowing==true){
                        mProgressHud?.dismiss()
                    }
                }
                is UIState.Success -> { // On Success
                    if(mProgressHud?.isShowing==true){
                        mProgressHud?.dismiss()
                    }
                    Utils.showSnackBar(this@HomeActivity,"User Deleted Successfully",Snackbar.LENGTH_LONG)
                    val mData = (state.data as ResponseBase<UserListResponseModel>)
                    if(mData!=null){
                        mData.data?.let { userArrayList.remove(it) }
                        mAdapter?.notifyDataSetChanged()
                    }

                }
                else -> {
                    Log.e("Critical", "Unexpected UIState received.")
                }
            }

        }
    }

    private fun displayUserList() {

        if(userArrayList.isNullOrEmpty()){
            emptyText?.visibility = View.VISIBLE
            recyclerView?.visibility = View.GONE
        }else{
            emptyText?.visibility = View.GONE
            recyclerView?.visibility = View.VISIBLE
        }
        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = mLayoutManager
        mAdapter = UserListAdapter(this,userArrayList,this)
        recyclerView?.adapter = mAdapter

        toolbarRightOne?.setOnClickListener {

            val mFragment = UserCreationDialogFragment.newInstance()
            mFragment.show(supportFragmentManager, "DialogFragment")

        }
    }

    override fun onClick(v: View?) {
        finish()
    }

    override fun onSelectItem(item: UserListResponseModel) {
        this.mSelectedUser = item
        val mFragment = MSDialogFragment.newInstance(
            MSDialogModel("Alert",
                "Do you want to delete this user",
                "Yes",
                "No",
                dialogId = 1002,
                isNegativeButtonEnabled = true,
                isPositiveButtonEnabled = true,
                isFromFragment = true)
        )
        mFragment.show(supportFragmentManager, "MSDialogFragment")
    }

    override fun onItemClick(dialogId: Int) {

        if(dialogId == 1001){
            userCreationRequest?.let { viewModel.processUserCreationRequest(it) }
        }else{
            mSelectedUser?.id?.let { viewModel.processDeleteUserRequest(it) }
        }

    }

    override fun onCreateClick(userName: String, email: String) {
        userCreationRequest = CreateUserRequestModel().apply {
            this.name = userName
            this.email = email

        }
        when {
            userName.isNullOrEmpty() -> {
                Utils.showSnackBar(this,"Enter your name",Snackbar.LENGTH_LONG)
            }
            email.isNullOrEmpty() -> {
                Utils.showSnackBar(this,"Enter your email",Snackbar.LENGTH_LONG)
            }
            else -> {
                val mFragment = MSDialogFragment.newInstance(
                    MSDialogModel("Alert",
                        "Are you sure want to create this user",
                        "Yes",
                        "No",
                        dialogId = 1001,
                        isNegativeButtonEnabled = true,
                        isPositiveButtonEnabled = true,
                        isFromFragment = true)
                )
                mFragment.show(supportFragmentManager, "MSDialogFragment")
            }
        }

    }
}