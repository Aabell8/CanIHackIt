package com.austinabell8.canihackit.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by austi on 2018-02-03.
 */
class ResultItem(val name: String, val location: String, val description: String, val tags: MutableList<String>) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            arrayListOf<String>().apply {
                parcel.readList(this, String::class.java.classLoader)
            })

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(location)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultItem> {
        override fun createFromParcel(parcel: Parcel): ResultItem {
            return ResultItem(parcel)
        }

        override fun newArray(size: Int): Array<ResultItem?> {
            return arrayOfNulls(size)
        }
    }
}