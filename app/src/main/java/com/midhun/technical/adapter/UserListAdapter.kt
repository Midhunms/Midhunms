package com.midhun.technical.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.midhun.technical.R
import com.midhun.technical.network.model.response.UserListResponseModel
import kotlinx.android.synthetic.main.user_list_adapter.view.*
import kotlin.collections.ArrayList

class UserListAdapter(
    private val context: Context,
    private var mItems: ArrayList<UserListResponseModel>,
    private val mListener: Listener?
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {


    init {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.user_list_adapter, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(mItems[position])
    }

    override fun getItemCount(): Int {
        return mItems.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener {

        lateinit var item: UserListResponseModel

        init {
            itemView.setOnLongClickListener(this)
        }

        fun setData(mItem: UserListResponseModel) {
            this.item = mItem

            itemView.nameText.text = mItem.name?:context.getString(R.string.name)
            itemView.emailText.text = mItem.email?:context.getString(R.string.email)
            itemView.genderText.text = mItem.gender?:context.getString(R.string.gender)
        }

        override fun onLongClick(v: View?): Boolean {
            mListener?.onSelectItem(item)
            return true
        }
    }

    interface Listener {
        fun onSelectItem(item: UserListResponseModel)
    }




}