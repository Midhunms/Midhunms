package com.midhun.technical.network.model.response

import android.os.Parcel
import android.os.Parcelable

data class UserListResponseModel(
    val id: Int = -1,
    val name: String? = "",
    val email: String? = "",
    val gender: String? = "",
    val status: String? = "",
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(gender)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserListResponseModel> {
        override fun createFromParcel(parcel: Parcel): UserListResponseModel {
            return UserListResponseModel(parcel)
        }

        override fun newArray(size: Int): Array<UserListResponseModel?> {
            return arrayOfNulls(size)
        }
    }
}
