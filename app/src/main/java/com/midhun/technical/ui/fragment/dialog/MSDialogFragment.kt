package com.midhun.technical.ui.fragment.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.midhun.technical.R
import com.midhun.technical.network.model.MSDialogModel
import com.midhun.technical.util.BundleArguments
import kotlinx.android.synthetic.main.fragment_ms_dialog.*


class MSDialogFragment : DialogFragment(),View.OnClickListener {

    private var mListener:ItemListener? = null
    private var msDialogModel: MSDialogModel?=null

    companion object {
        fun newInstance(msDialogModel: MSDialogModel): MSDialogFragment {
            val bundle = Bundle()
            val fragment = MSDialogFragment()
            bundle.putParcelable(BundleArguments.ARG_DIALOG_DATA,msDialogModel)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog_Custom)
        dialog?.setCanceledOnTouchOutside(true)

        if(arguments?.getParcelable<MSDialogModel>(BundleArguments.ARG_DIALOG_DATA)!=null){
            msDialogModel = arguments?.getParcelable(BundleArguments.ARG_DIALOG_DATA)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        return inflater.inflate(R.layout.fragment_ms_dialog, container)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleText?.text = msDialogModel?.title
        contentText?.text = msDialogModel?.content
        textNegativeButton?.text = msDialogModel?.negativeButtonText?:""
        textPositiveButton?.text = msDialogModel?.positiveButtonText?:""

        if(msDialogModel?.isNegativeButtonEnabled!=null && msDialogModel?.isNegativeButtonEnabled!!){
            textNegativeButton?.visibility = View.VISIBLE
        }else{
            textNegativeButton?.visibility = View.GONE
        }

        if(msDialogModel?.isPositiveButtonEnabled!=null && msDialogModel?.isPositiveButtonEnabled!!){
            textPositiveButton?.visibility = View.VISIBLE
        }else{
            textPositiveButton?.visibility = View.GONE
        }

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
                msDialogModel?.dialogId?.let { mListener?.onItemClick(it) }

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
        fun onItemClick(dialogId: Int)

    }


}



