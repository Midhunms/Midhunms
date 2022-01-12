package com.midhun.technical.network.model

import android.os.Parcel
import android.os.Parcelable

class MSDialogModel (

    var title: String? = "",
    var content: String? = "",
    var positiveButtonText: String? = "",
    var negativeButtonText: String? = "",
    var dialogId: Int = -1,
    var isNegativeButtonEnabled: Boolean = false,
    var isPositiveButtonEnabled: Boolean = false,
    var isFromFragment: Boolean = false
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(positiveButtonText)
        parcel.writeString(negativeButtonText)
        parcel.writeInt(dialogId)
        parcel.writeByte(if (isNegativeButtonEnabled) 1 else 0)
        parcel.writeByte(if (isPositiveButtonEnabled) 1 else 0)
        parcel.writeByte(if (isFromFragment) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MSDialogModel> {
        override fun createFromParcel(parcel: Parcel): MSDialogModel {
            return MSDialogModel(parcel)
        }

        override fun newArray(size: Int): Array<MSDialogModel?> {
            return arrayOfNulls(size)
        }
    }
}