package com.midhun.technical.ui.fragment.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.midhun.technical.R
import kotlinx.android.synthetic.main.fragment_create_user_dialog.*


class UserCreationDialogFragment : DialogFragment(),View.OnClickListener {

    private var mListener:ItemListener? = null

    companion object {
        fun newInstance(): UserCreationDialogFragment {
            val bundle = Bundle()
            val fragment = UserCreationDialogFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog_Custom)
        dialog?.setCanceledOnTouchOutside(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        return inflater.inflate(R.layout.fragment_create_user_dialog, container)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textNegativeButton?.setOnClickListener(this)
        textPositiveButton?.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.textNegativeButton -> {
                dismiss()
            }
            R.id.textPositiveButton -> {
                dismiss()
                mListener?.onCreateClick(userName?.text.toString(),userEmail?.text.toString())

            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ItemListener) {
            mListener = context
        } else {
            throw RuntimeException("$context must implement Listener")
        }
    }

    interface ItemListener {
        fun onCreateClick(userName:String,email:String)

    }

}



